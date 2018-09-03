package com.wms.newwmsapp.model;

import java.io.Serializable;

public class GoodsAreaModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7116937439508050112L;
	public String GoodsAreaCode;//货区Code
	public String GoodsAreaName;//货区Name
	public String Code;//货位Name
	public String Name;//货位Name
	
	public String getGoodsAreaCode() {
		return GoodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		GoodsAreaCode = goodsAreaCode;
	}
	public String getGoodsAreaName() {
		return GoodsAreaName;
	}
	public void setGoodsAreaName(String goodsAreaName) {
		GoodsAreaName = goodsAreaName;
	}
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
