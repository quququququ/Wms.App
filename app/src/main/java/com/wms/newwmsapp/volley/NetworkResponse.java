package com.wms.newwmsapp.volley;

import org.apache.http.HttpStatus;

import java.util.Collections;
import java.util.Map;

public class NetworkResponse {
	public NetworkResponse(int statusCode, byte[] data,
			Map<String, String> headers, boolean notModified, long networkTimeMs) {
		this.statusCode = statusCode;
		this.data = data;
		this.headers = headers;
		this.notModified = notModified;
		this.networkTimeMs = networkTimeMs;
	}

	public NetworkResponse(int statusCode, byte[] data,
			Map<String, String> headers, boolean notModified) {
		this(statusCode, data, headers, notModified, 0);
	}

	public NetworkResponse(byte[] data) {
		this(HttpStatus.SC_OK, data, Collections.<String, String> emptyMap(),
				false, 0);
	}

	public NetworkResponse(byte[] data, Map<String, String> headers) {
		this(HttpStatus.SC_OK, data, headers, false, 0);
	}

	/** The HTTP status code. */
	public final int statusCode;

	/** Raw data from this response. */
	public final byte[] data;

	/** Response headers. */
	public final Map<String, String> headers;

	/** True if the server returned a 304 (Not Modified). */
	public final boolean notModified;

	/** Network roundtrip time in milliseconds. */
	public final long networkTimeMs;
}
