package com.wms.newwmsapp.volley.toolbox;

import java.io.UnsupportedEncodingException;

import com.wms.newwmsapp.volley.NetworkResponse;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyLog;
import com.wms.newwmsapp.volley.Response.ErrorListener;
import com.wms.newwmsapp.volley.Response.Listener;

public abstract class JsonRequest<T> extends Request<T> {
	/** Default charset for JSON request. */
	protected static final String PROTOCOL_CHARSET = "utf-8";

	/** Content type for request. */
	private static final String PROTOCOL_CONTENT_TYPE = String.format(
			"application/json; charset=%s", PROTOCOL_CHARSET);

	private final Listener<T> mListener;
	private final String mRequestBody;

	public JsonRequest(String url, String requestBody, Listener<T> listener,
			ErrorListener errorListener) {
		this(Method.DEPRECATED_GET_OR_POST, url, requestBody, listener,
				errorListener);
	}

	public JsonRequest(int method, String url, String requestBody,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
		mRequestBody = requestBody;
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

	@Override
	public String getPostBodyContentType() {
		return getBodyContentType();
	}

	@Override
	public byte[] getPostBody() {
		return getBody();
	}

	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}

	@Override
	public byte[] getBody() {
		try {
			return mRequestBody == null ? null : mRequestBody
					.getBytes(PROTOCOL_CHARSET);
		} catch (UnsupportedEncodingException uee) {
			VolleyLog
					.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
							mRequestBody, PROTOCOL_CHARSET);
			return null;
		}
	}
}
