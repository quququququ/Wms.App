package com.wms.newwmsapp.model;

import java.io.Serializable;

public class UpModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422635437732453571L;
	private String Code;//入库上架单号
	private String InStockPutawayStatusCode;//入库上架状态Code
	private String InStockPutawayStatusName;//入库上架状态Name
	private String InStockPutawayStatusDate;//入库上架单生成时间
	private String PutawayDate;//入库上架时间
	private String Remark;//备注
	private String CreateDate;//创建时间
	private String CreateBy;//创建人
	private String UpdateDate;//修改时间
	private String UpdateBy;//修改人
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getInStockPutawayStatusCode() {
		return InStockPutawayStatusCode;
	}
	public void setInStockPutawayStatusCode(String inStockPutawayStatusCode) {
		InStockPutawayStatusCode = inStockPutawayStatusCode;
	}
	public String getInStockPutawayStatusName() {
		return InStockPutawayStatusName;
	}
	public void setInStockPutawayStatusName(String inStockPutawayStatusName) {
		InStockPutawayStatusName = inStockPutawayStatusName;
	}
	public String getInStockPutawayStatusDate() {
		return InStockPutawayStatusDate;
	}
	public void setInStockPutawayStatusDate(String inStockPutawayStatusDate) {
		InStockPutawayStatusDate = inStockPutawayStatusDate;
	}
	public String getPutawayDate() {
		return PutawayDate;
	}
	public void setPutawayDate(String putawayDate) {
		PutawayDate = putawayDate;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public String getUpdateDate() {
		return UpdateDate;
	}
	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}
	public String getUpdateBy() {
		return UpdateBy;
	}
	public void setUpdateBy(String updateBy) {
		UpdateBy = updateBy;
	}
}
