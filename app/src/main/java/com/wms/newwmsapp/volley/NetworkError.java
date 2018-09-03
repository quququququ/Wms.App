package com.wms.newwmsapp.volley;

@SuppressWarnings("serial")
public class NetworkError extends VolleyError {
	public NetworkError() {
		super();
	}

	public NetworkError(Throwable cause) {
		super(cause);
	}

	public NetworkError(NetworkResponse networkResponse) {
		super(networkResponse);
	}
}