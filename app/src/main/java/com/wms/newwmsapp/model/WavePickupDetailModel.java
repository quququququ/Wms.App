package com.wms.newwmsapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class WavePickupDetailModel implements Serializable {
	public String getIsSuccess() {
		return IsSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		IsSuccess = isSuccess;
	}
	public String getErrorMessage() {
		return ErrorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getStockCode() {
		return StockCode;
	}
	public void setStockCode(String stockCode) {
		StockCode = stockCode;
	}
	public String getSortUserName() {
		return SortUserName;
	}
	public void setSortUserName(String sortUserName) {
		SortUserName = sortUserName;
	}
	public String getSortUserCode() {
		return SortUserCode;
	}
	public void setSortUserCode(String sortUserCode) {
		SortUserCode = sortUserCode;
	}
	public String getTotalNum() {
		return TotalNum;
	}
	public void setTotalNum(String totalNum) {
		TotalNum = totalNum;
	}
	public String getTotalGoodsCount() {
		return TotalGoodsCount;
	}
	public void setTotalGoodsCount(String totalGoodsCount) {
		TotalGoodsCount = totalGoodsCount;
	}

	public String IsSuccess;
	public String ErrorMessage;
	public String Code;
	public String StockCode;
	public String SortUserName;
	public String SortUserCode;
	public String TotalNum;
	public String TotalGoodsCount;
	public String getTotalOrderCount() {
		return TotalOrderCount;
	}
	public void setTotalOrderCount(String totalOrderCount) {
		TotalOrderCount = totalOrderCount;
	}

	public String TotalOrderCount;
	public List<PickDetailModel> Details=new ArrayList<PickDetailModel>();
	
}

