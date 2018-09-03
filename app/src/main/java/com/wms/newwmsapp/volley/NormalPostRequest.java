package com.wms.newwmsapp.volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.wms.newwmsapp.volley.Response.ErrorListener;
import com.wms.newwmsapp.volley.Response.Listener;
import com.wms.newwmsapp.volley.toolbox.HttpHeaderParser;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class NormalPostRequest extends Request<JSONObject> {
	private Handler handler;
	private Map<String, String> mMap;
	private Listener<JSONObject> mListener;

	@SuppressWarnings("rawtypes")
	public NormalPostRequest(Context context, String url,
			Listener<JSONObject> listener, ErrorListener errorListener,
			Map map, Handler handler) {
		super(Request.Method.POST, url, errorListener);
		this.mListener = listener;
		this.mMap = new HashMap<String, String>();
		this.handler = handler;

		if (map != null) {
			for (Object key : map.keySet()) {
				if (map.get(key) != null) {
					mMap.put(key.toString(), map.get(key).toString());
				} else {
					mMap.put(key.toString(), "");
				}
			}
		}

	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mMap;
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse netWorkResponse) {
		try {
			String jsonString = new String(netWorkResponse.data,HttpHeaderParser.parseCharset(netWorkResponse.headers));
			
			Boolean IsSuccess = new JSONObject(jsonString).getBoolean("IsSuccess");
			//常规错误
			if(!IsSuccess){
				String ErrorMessage = new JSONObject(jsonString).getString("ErrorMessage");
				Message msg = new Message();
				msg.what = 1;
				msg.obj = ErrorMessage;
				handler.sendMessage(msg);
				return null;
			}

			return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(netWorkResponse));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		mListener.onResponse(response);
	}

	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {

		if (volleyError instanceof NoConnectionError) {
			Message msg = new Message();
			msg.what = 1;
			msg.obj = "访问服务器异常\n请联系信息中心";
			handler.sendMessage(msg);
		} else if (volleyError instanceof TimeoutError) {
			Message msg = new Message();
			msg.what = 1;
			msg.obj = "请求超时，请稍后再试";
			handler.sendMessage(msg);
		}
		return super.parseNetworkError(volleyError);
	}
}