package com.wms.newwmsapp.volley;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;

import java.util.concurrent.BlockingQueue;

public class NetworkDispatcher extends Thread {
	/** The queue of requests to service. */
	private final BlockingQueue<Request<?>> mQueue;
	/** The network interface for processing requests. */
	private final Network mNetwork;
	/** The cache to write to. */
	private final Cache mCache;
	/** For posting responses and errors. */
	private final ResponseDelivery mDelivery;
	/** Used for telling us to die. */
	private volatile boolean mQuit = false;

	public NetworkDispatcher(BlockingQueue<Request<?>> queue, Network network,
			Cache cache, ResponseDelivery delivery) {
		mQueue = queue;
		mNetwork = network;
		mCache = cache;
		mDelivery = delivery;
	}

	public void quit() {
		mQuit = true;
		interrupt();
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void addTrafficStatsTag(Request<?> request) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			TrafficStats.setThreadStatsTag(request.getTrafficStatsTag());
		}
	}

	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		while (true) {
			long startTimeMs = SystemClock.elapsedRealtime();
			Request<?> request;
			try {
				request = mQueue.take();
			} catch (InterruptedException e) {
				if (mQuit) {
					return;
				}
				continue;
			}

			try {
				request.addMarker("network-queue-take");

				if (request.isCanceled()) {
					request.finish("network-discard-cancelled");
					continue;
				}

				addTrafficStatsTag(request);

				NetworkResponse networkResponse = mNetwork
						.performRequest(request);
				request.addMarker("network-http-complete");

				if (networkResponse.notModified
						&& request.hasHadResponseDelivered()) {
					request.finish("not-modified");
					continue;
				}

				Response<?> response = request
						.parseNetworkResponse(networkResponse);
				request.addMarker("network-parse-complete");

				if (request != null && request.shouldCache()
						&& response.cacheEntry != null) {
					mCache.put(request.getCacheKey(), response.cacheEntry);
					request.addMarker("network-cache-written");
				}

				request.markDelivered();
				mDelivery.postResponse(request, response);
			} catch (VolleyError volleyError) {
				volleyError.setNetworkTimeMs(SystemClock.elapsedRealtime()
						- startTimeMs);
				parseAndDeliverNetworkError(request, volleyError);
			} catch (Exception e) {
				VolleyLog.e(e, "Unhandled exception %s", e.toString());
				VolleyError volleyError = new VolleyError(e);
				volleyError.setNetworkTimeMs(SystemClock.elapsedRealtime()
						- startTimeMs);
				mDelivery.postError(request, volleyError);
			}
		}
	}

	private void parseAndDeliverNetworkError(Request<?> request,
			VolleyError error) {
		error = request.parseNetworkError(error);
		mDelivery.postError(request, error);
	}
}
