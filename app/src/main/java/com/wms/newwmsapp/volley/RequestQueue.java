package com.wms.newwmsapp.volley;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestQueue {

	public static interface RequestFinishedListener<T> {
		public void onRequestFinished(Request<T> request);
	}

	private AtomicInteger mSequenceGenerator = new AtomicInteger();
	private final Map<String, Queue<Request<?>>> mWaitingRequests = new HashMap<String, Queue<Request<?>>>();

	private final Set<Request<?>> mCurrentRequests = new HashSet<Request<?>>();

	/** The cache triage queue. */
	private final PriorityBlockingQueue<Request<?>> mCacheQueue = new PriorityBlockingQueue<Request<?>>();

	/** The queue of requests that are actually going out to the network. */
	private final PriorityBlockingQueue<Request<?>> mNetworkQueue = new PriorityBlockingQueue<Request<?>>();

	/** Number of network request dispatcher threads to start. */
	private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;

	/** Cache interface for retrieving and storing responses. */
	private final Cache mCache;

	/** Network interface for performing requests. */
	private final Network mNetwork;

	/** Response delivery mechanism. */
	private final ResponseDelivery mDelivery;

	/** The network dispatchers. */
	private NetworkDispatcher[] mDispatchers;

	/** The cache dispatcher. */
	private CacheDispatcher mCacheDispatcher;

	@SuppressWarnings("rawtypes")
	private List<RequestFinishedListener> mFinishedListeners = new ArrayList<RequestFinishedListener>();

	public RequestQueue(Cache cache, Network network, int threadPoolSize,
			ResponseDelivery delivery) {
		mCache = cache;
		mNetwork = network;
		mDispatchers = new NetworkDispatcher[threadPoolSize];
		mDelivery = delivery;
	}

	public RequestQueue(Cache cache, Network network, int threadPoolSize) {
		this(cache, network, threadPoolSize, new ExecutorDelivery(new Handler(
				Looper.getMainLooper())));
	}

	public RequestQueue(Cache cache, Network network) {
		this(cache, network, DEFAULT_NETWORK_THREAD_POOL_SIZE);
	}

	public void start() {
		stop();
		mCacheDispatcher = new CacheDispatcher(mCacheQueue, mNetworkQueue,
				mCache, mDelivery);
		mCacheDispatcher.start();
		for (int i = 0; i < mDispatchers.length; i++) {
			NetworkDispatcher networkDispatcher = new NetworkDispatcher(
					mNetworkQueue, mNetwork, mCache, mDelivery);
			mDispatchers[i] = networkDispatcher;
			networkDispatcher.start();
		}
	}

	public void stop() {
		if (mCacheDispatcher != null) {
			mCacheDispatcher.quit();
		}
		for (int i = 0; i < mDispatchers.length; i++) {
			if (mDispatchers[i] != null) {
				mDispatchers[i].quit();
			}
		}
	}

	public int getSequenceNumber() {
		return mSequenceGenerator.incrementAndGet();
	}

	public Cache getCache() {
		return mCache;
	}

	public interface RequestFilter {
		public boolean apply(Request<?> request);
	}

	public void cancelAll(RequestFilter filter) {
		synchronized (mCurrentRequests) {
			for (Request<?> request : mCurrentRequests) {
				if (filter.apply(request)) {
					request.cancel();
				}
			}
		}
	}

	public void cancelAll(final Object tag) {
		if (tag == null) {
			throw new IllegalArgumentException(
					"Cannot cancelAll with a null tag");
		}
		cancelAll(new RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return request.getTag() == tag;
			}
		});
	}

	public <T> Request<T> add(Request<T> request) {
		request.setRequestQueue(this);
		synchronized (mCurrentRequests) {
			mCurrentRequests.add(request);
		}

		request.setSequence(getSequenceNumber());
		request.addMarker("add-to-queue");

		if (!request.shouldCache()) {
			mNetworkQueue.add(request);
			return request;
		}

		synchronized (mWaitingRequests) {
			String cacheKey = request.getCacheKey();
			if (mWaitingRequests.containsKey(cacheKey)) {
				Queue<Request<?>> stagedRequests = mWaitingRequests
						.get(cacheKey);
				if (stagedRequests == null) {
					stagedRequests = new LinkedList<Request<?>>();
				}
				stagedRequests.add(request);
				mWaitingRequests.put(cacheKey, stagedRequests);
				if (VolleyLog.DEBUG) {
					VolleyLog
							.v("Request for cacheKey=%s is in flight, putting on hold.",
									cacheKey);
				}
			} else {
				mWaitingRequests.put(cacheKey, null);
				mCacheQueue.add(request);
			}
			return request;
		}
	}

	<T> void finish(Request<T> request) {
		synchronized (mCurrentRequests) {
			mCurrentRequests.remove(request);
		}
		synchronized (mFinishedListeners) {
			for (RequestFinishedListener<T> listener : mFinishedListeners) {
				listener.onRequestFinished(request);
			}
		}

		if (request.shouldCache()) {
			synchronized (mWaitingRequests) {
				String cacheKey = request.getCacheKey();
				Queue<Request<?>> waitingRequests = mWaitingRequests
						.remove(cacheKey);
				if (waitingRequests != null) {
					if (VolleyLog.DEBUG) {
						VolleyLog
								.v("Releasing %d waiting requests for cacheKey=%s.",
										waitingRequests.size(), cacheKey);
					}
					mCacheQueue.addAll(waitingRequests);
				}
			}
		}
	}

	public <T> void addRequestFinishedListener(
			RequestFinishedListener<T> listener) {
		synchronized (mFinishedListeners) {
			mFinishedListeners.add(listener);
		}
	}

	public <T> void removeRequestFinishedListener(
			RequestFinishedListener<T> listener) {
		synchronized (mFinishedListeners) {
			mFinishedListeners.remove(listener);
		}
	}
}
