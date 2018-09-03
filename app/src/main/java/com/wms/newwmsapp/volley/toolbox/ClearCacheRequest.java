package com.wms.newwmsapp.volley.toolbox;

import com.wms.newwmsapp.volley.Cache;
import com.wms.newwmsapp.volley.NetworkResponse;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.Response;

import android.os.Handler;
import android.os.Looper;

public class ClearCacheRequest extends Request<Object> {
	private final Cache mCache;
	private final Runnable mCallback;

	public ClearCacheRequest(Cache cache, Runnable callback) {
		super(Method.GET, null, null);
		mCache = cache;
		mCallback = callback;
	}

	@Override
	public boolean isCanceled() {
		// This is a little bit of a hack, but hey, why not.
		mCache.clear();
		if (mCallback != null) {
			Handler handler = new Handler(Looper.getMainLooper());
			handler.postAtFrontOfQueue(mCallback);
		}
		return true;
	}

	@Override
	public Priority getPriority() {
		return Priority.IMMEDIATE;
	}

	@Override
	protected Response<Object> parseNetworkResponse(NetworkResponse response) {
		return null;
	}

	@Override
	protected void deliverResponse(Object response) {
	}
}
