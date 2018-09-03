package com.wms.newwmsapp.model;

/**
 * Created by cheng on 2018/6/19.
 */

public class SeedingOtherModel {

    /**
     * IsSuccess : true
     * ErrorMessage :
     * Code : BAJP180619000001
     * StockCode : BAJ
     * SortUserName : 管理员
     * SortUserCode : admin1
     * TotalNum : 4
     * TotalGoodsCount : 1
     * TotalOrderCount : 4
     */

    private boolean IsSuccess;
    private String ErrorMessage;
    private String Code;
    private String StockCode;
    private String SortUserName;
    private String SortUserCode;
    private String TotalNum;
    private String TotalGoodsCount;
    private String TotalOrderCount;

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getStockCode() {
        return StockCode;
    }

    public void setStockCode(String StockCode) {
        this.StockCode = StockCode;
    }

    public String getSortUserName() {
        return SortUserName;
    }

    public void setSortUserName(String SortUserName) {
        this.SortUserName = SortUserName;
    }

    public String getSortUserCode() {
        return SortUserCode;
    }

    public void setSortUserCode(String SortUserCode) {
        this.SortUserCode = SortUserCode;
    }

    public String getTotalNum() {
        return TotalNum;
    }

    public void setTotalNum(String TotalNum) {
        this.TotalNum = TotalNum;
    }

    public String getTotalGoodsCount() {
        return TotalGoodsCount;
    }

    public void setTotalGoodsCount(String TotalGoodsCount) {
        this.TotalGoodsCount = TotalGoodsCount;
    }

    public String getTotalOrderCount() {
        return TotalOrderCount;
    }

    public void setTotalOrderCount(String TotalOrderCount) {
        this.TotalOrderCount = TotalOrderCount;
    }
}
