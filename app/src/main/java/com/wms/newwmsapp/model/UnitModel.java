package com.wms.newwmsapp.model;

import java.io.Serializable;

public class UnitModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1944138120662941776L;
	private String Code;
	private String UnitName;
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getUnitName() {
		return UnitName;
	}
	public void setUnitName(String unitName) {
		UnitName = unitName;
	}
}
