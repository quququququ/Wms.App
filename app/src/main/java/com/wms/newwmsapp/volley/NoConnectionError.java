package com.wms.newwmsapp.volley;

@SuppressWarnings("serial")
public class NoConnectionError extends NetworkError {
	public NoConnectionError() {
		super();
	}

	public NoConnectionError(Throwable reason) {
		super(reason);
	}
}
