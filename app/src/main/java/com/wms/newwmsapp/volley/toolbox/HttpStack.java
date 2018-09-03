package com.wms.newwmsapp.volley.toolbox;

import org.apache.http.HttpResponse;

import com.wms.newwmsapp.volley.AuthFailureError;
import com.wms.newwmsapp.volley.Request;

import java.io.IOException;
import java.util.Map;

public interface HttpStack {
	
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
        throws IOException, AuthFailureError;

}
