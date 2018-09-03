package com.wms.newwmsapp.model;

import java.io.Serializable;

public class CustGoodModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4190053952622927977L;
	private String CustGoodsCode;
	private String CustGoodsName;
	private String IfBoxId;
	private String IfProductId;
	public String getCustGoodsCode() {
		return CustGoodsCode;
	}
	public void setCustGoodsCode(String custGoodsCode) {
		CustGoodsCode = custGoodsCode;
	}
	public String getCustGoodsName() {
		return CustGoodsName;
	}
	public void setCustGoodsName(String custGoodsName) {
		CustGoodsName = custGoodsName;
	}
	public String getIfBoxId() {
		return IfBoxId;
	}
	public void setIfBoxId(String ifBoxId) {
		IfBoxId = ifBoxId;
	}
	public String getIfProductId() {
		return IfProductId;
	}
	public void setIfProductId(String ifProductId) {
		IfProductId = ifProductId;
	}
	
}
