package com.wms.newwmsapp.model;

import java.io.Serializable;

public class InstockModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6570380099647161452L;
	public String Code; //入库单号
	public String PurchaseNO;//采购单号
	public String CustGoodsName;//货主
	public String InformDate;//入库时间
	
	public String InStockTypeCode;
	public String LighterTypeCode;
	public int InStockStatusCode;
	public String InStockStatusName;
	public String LighterTypeName;
	public String InStockTypeName;
	public String StockName;
	public String InStockStatusDate;
	public String FirstInDate;
	public String LastInDate;
	public String InStockDate;
	public String ReturnGoodsOutformCode;
	public String ReturnGoodsReceipt;
	public String ReturnGoodsResponsible;
	public String Sender;
	public String Transer;
	public String ShippingType;
	public String ShippingTypeName;
	public String Remark;
	public String LimitStockArea;
	public String LimitGoodsArea;
	public String InStockInformCode;
	public String StockCode;
	public String CustGoodsCode;
	public String CreateDate;
	public String CreateBy;
	public String UpdateDate;
	public String UpdateBy;
	
	public String getPurchaseNO() {
		return PurchaseNO;
	}
	public void setPurchaseNO(String purchaseNO) {
		PurchaseNO = purchaseNO;
	}
	public String getInStockTypeCode() {
		return InStockTypeCode;
	}
	public void setInStockTypeCode(String inStockTypeCode) {
		InStockTypeCode = inStockTypeCode;
	}
	public String getLighterTypeCode() {
		return LighterTypeCode;
	}
	public void setLighterTypeCode(String lighterTypeCode) {
		LighterTypeCode = lighterTypeCode;
	}
	public int getInStockStatusCode() {
		return InStockStatusCode;
	}
	public void setInStockStatusCode(int inStockStatusCode) {
		InStockStatusCode = inStockStatusCode;
	}
	public String getInStockStatusName() {
		return InStockStatusName;
	}
	public void setInStockStatusName(String inStockStatusName) {
		InStockStatusName = inStockStatusName;
	}
	public String getLighterTypeName() {
		return LighterTypeName;
	}
	public void setLighterTypeName(String lighterTypeName) {
		LighterTypeName = lighterTypeName;
	}
	public String getInStockTypeName() {
		return InStockTypeName;
	}
	public void setInStockTypeName(String inStockTypeName) {
		InStockTypeName = inStockTypeName;
	}
	public String getCustGoodsName() {
		return CustGoodsName;
	}
	public void setCustGoodsName(String custGoodsName) {
		CustGoodsName = custGoodsName;
	}
	public String getStockName() {
		return StockName;
	}
	public void setStockName(String stockName) {
		StockName = stockName;
	}
	public String getInStockStatusDate() {
		return InStockStatusDate;
	}
	public void setInStockStatusDate(String inStockStatusDate) {
		InStockStatusDate = inStockStatusDate;
	}
	public String getInformDate() {
		return InformDate;
	}
	public void setInformDate(String informDate) {
		InformDate = informDate;
	}
	public String getFirstInDate() {
		return FirstInDate;
	}
	public void setFirstInDate(String firstInDate) {
		FirstInDate = firstInDate;
	}
	public String getLastInDate() {
		return LastInDate;
	}
	public void setLastInDate(String lastInDate) {
		LastInDate = lastInDate;
	}
	public String getInStockDate() {
		return InStockDate;
	}
	public void setInStockDate(String inStockDate) {
		InStockDate = inStockDate;
	}
	public String getReturnGoodsOutformCode() {
		return ReturnGoodsOutformCode;
	}
	public void setReturnGoodsOutformCode(String returnGoodsOutformCode) {
		ReturnGoodsOutformCode = returnGoodsOutformCode;
	}
	public String getReturnGoodsReceipt() {
		return ReturnGoodsReceipt;
	}
	public void setReturnGoodsReceipt(String returnGoodsReceipt) {
		ReturnGoodsReceipt = returnGoodsReceipt;
	}
	public String getReturnGoodsResponsible() {
		return ReturnGoodsResponsible;
	}
	public void setReturnGoodsResponsible(String returnGoodsResponsible) {
		ReturnGoodsResponsible = returnGoodsResponsible;
	}
	public String getSender() {
		return Sender;
	}
	public void setSender(String sender) {
		Sender = sender;
	}
	public String getTranser() {
		return Transer;
	}
	public void setTranser(String transer) {
		Transer = transer;
	}
	public String getShippingType() {
		return ShippingType;
	}
	public void setShippingType(String shippingType) {
		ShippingType = shippingType;
	}
	public String getShippingTypeName() {
		return ShippingTypeName;
	}
	public void setShippingTypeName(String shippingTypeName) {
		ShippingTypeName = shippingTypeName;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getLimitStockArea() {
		return LimitStockArea;
	}
	public void setLimitStockArea(String limitStockArea) {
		LimitStockArea = limitStockArea;
	}
	public String getLimitGoodsArea() {
		return LimitGoodsArea;
	}
	public void setLimitGoodsArea(String limitGoodsArea) {
		LimitGoodsArea = limitGoodsArea;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getInStockInformCode() {
		return InStockInformCode;
	}
	public void setInStockInformCode(String inStockInformCode) {
		InStockInformCode = inStockInformCode;
	}
	public String getStockCode() {
		return StockCode;
	}
	public void setStockCode(String stockCode) {
		StockCode = stockCode;
	}
	public String getCustGoodsCode() {
		return CustGoodsCode;
	}
	public void setCustGoodsCode(String custGoodsCode) {
		CustGoodsCode = custGoodsCode;
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
