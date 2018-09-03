package com.wms.newwmsapp.volley.toolbox;

import org.json.JSONArray;
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

public class JsonArrayRequest extends JsonRequest<JSONArray> {
	
	private Handler handler;
    public JsonArrayRequest(int method, String url, String requestBody,
                            Listener<JSONArray> listener, ErrorListener errorListener) {
        super(method, url, requestBody, listener,
                errorListener);
    }
	public JsonArrayRequest(int method, String url, String requestBody,
			Listener<JSONArray> listener, ErrorListener errorListener, Handler handler) {
		super(method, url, requestBody, listener, errorListener);
		this.handler = handler;
	}
    public JsonArrayRequest(String url, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
    }

    public JsonArrayRequest(int method, String url, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }

    public JsonArrayRequest(int method, String url, JSONArray jsonRequest, 
            Listener<JSONArray> listener, ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
    }

    public JsonArrayRequest(int method, String url, JSONObject jsonRequest,
                            Listener<JSONArray> listener, ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
    }

    public JsonArrayRequest(String url, JSONArray jsonRequest, Listener<JSONArray> listener,
                            ErrorListener errorListener) {
        this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest,
                listener, errorListener);
    }

    public JsonArrayRequest(String url, JSONObject jsonRequest, Listener<JSONArray> listener,
                             ErrorListener errorListener) {
        this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest,
                listener, errorListener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

}
