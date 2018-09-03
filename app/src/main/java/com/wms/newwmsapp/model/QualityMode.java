package com.wms.newwmsapp.model;

import java.io.Serializable;

public class QualityMode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -951449019846688016L;
	private String Code;
	private String Name;
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
}
