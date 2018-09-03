package com.wms.newwmsapp.volley;

public interface Network {
	
	public NetworkResponse performRequest(Request<?> request)throws VolleyError;
}