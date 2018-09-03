package com.wms.newwmsapp.volley.toolbox;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.wms.newwmsapp.volley.NetworkResponse;
import com.wms.newwmsapp.volley.NoConnectionError;
import com.wms.newwmsapp.volley.ParseError;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.TimeoutError;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.Response.ErrorListener;
import com.wms.newwmsapp.volley.Response.Listener;

import java.io.UnsupportedEncodingException;

public class JsonObjectRequest extends JsonRequest<JSONObject> {
	private Handler handler;
	private boolean IsFlag;
	
	public JsonObjectRequest(int method, String url, String requestBody,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, requestBody, listener, errorListener);
	}

	public JsonObjectRequest(int method, String url, String requestBody,
			Listener<JSONObject> listener, ErrorListener errorListener, Handler handler) {
		super(method, url, requestBody, listener, errorListener);
		this.handler = handler;
	}
	public JsonObjectRequest(int method, String url, String requestBody,
			Listener<JSONObject> listener, ErrorListener errorListener, Handler handler,boolean isFlag) {
		super(method, url, requestBody, listener, errorListener);
		this.handler = handler;
		IsFlag=isFlag;
	}
	public JsonObjectRequest(String url, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		super(Method.GET, url, null, listener, errorListener);
	}

	public JsonObjectRequest(int method, String url,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, null, listener, errorListener);
	}

	public JsonObjectRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, (jsonRequest == null) ? null : jsonRequest
				.toString(), listener, errorListener);
	}

	public JsonObjectRequest(String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest,
				listener, errorListener);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,HttpHeaderParser.parseCharset(response.headers,PROTOCOL_CHARSET));
			
			Boolean IsSuccess =true;
			if(IsFlag)
			{
				
				IsSuccess=true;
			}else
			{
				 new JSONObject(jsonString).getBoolean("IsSuccess");
			}
			//常规错误
			if(!IsSuccess){
				String ErrorMessage = new JSONObject(jsonString).getString("ErrorMessage");
				Message msg = new Message();
				msg.what = 1;
				msg.obj = ErrorMessage;
				handler.sendMessage(msg);
				return null;
			}
			
			
			return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {

		if (volleyError instanceof NoConnectionError) {
			Message msg = new Message();
			msg.what = 1;
			msg.obj = "请联系管理员";
			handler.sendMessage(msg);
		} else if (volleyError instanceof TimeoutError) {
			Message msg = new Message();
			msg.what = 1;
			msg.obj = "请求超时，请稍后再试";
			handler.sendMessage(msg);
		} else {
			Message msg = new Message();
			msg.what = 1;
			msg.obj = "请联系管理员";
			handler.sendMessage(msg);
		}

		return super.parseNetworkError(volleyError);
	}
}
