package com.wms.newwmsapp.volley.toolbox;

import com.wms.newwmsapp.volley.AuthFailureError;

public interface Authenticator {

	public String getAuthToken() throws AuthFailureError;

	public void invalidateAuthToken(String authToken);
}
