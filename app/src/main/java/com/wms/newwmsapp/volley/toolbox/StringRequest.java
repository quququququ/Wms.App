package com.wms.newwmsapp.volley.toolbox;

import java.io.UnsupportedEncodingException;

import com.wms.newwmsapp.volley.NetworkResponse;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.Response.ErrorListener;
import com.wms.newwmsapp.volley.Response.Listener;

public class StringRequest extends Request<String> {
	private final Listener<String> mListener;

	public StringRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	public StringRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		this(Method.GET, url, listener, errorListener);
	}

	@Override
	protected void deliverResponse(String response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String parsed;
		try {
			parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed,
				HttpHeaderParser.parseCacheHeaders(response));
	}
}
