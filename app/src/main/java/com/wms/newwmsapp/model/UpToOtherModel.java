package com.wms.newwmsapp.model;

/**
 * Created by cheng on 2018/7/18.
 */

public class UpToOtherModel  {

    /**
     * IsSuccess : true
     * ErrorMessage : null
     * GoodsPosCode : 货位编码，供后续提交数据使用
     * GoodsPosName : 货位名称
     */

    private boolean IsSuccess;
    private String ErrorMessage;
    private String GoodsPosCode;
    private String GoodsPosName;

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

    public String getGoodsPosCode() {
        return GoodsPosCode;
    }

    public void setGoodsPosCode(String GoodsPosCode) {
        this.GoodsPosCode = GoodsPosCode;
    }

    public String getGoodsPosName() {
        return GoodsPosName;
    }

    public void setGoodsPosName(String GoodsPosName) {
        this.GoodsPosName = GoodsPosName;
    }
}
