package com.wms.newwmsapp.volley;

@SuppressWarnings("serial")
public class ServerError extends VolleyError {
	public ServerError(NetworkResponse networkResponse) {
		super(networkResponse);
	}

	public ServerError() {
		super();
	}
}
