package com.wms.newwmsapp.model;

/**
 * Created by cheng on 2018/6/19.
 */

public class BaseModel {

    /**
     * ErrorMessage : null
     * HasWarning : false
     * IsSuccess : true
     * SuccessMessage :
     * WarningMessage : null
     */

    private String ErrorMessage;
    private boolean HasWarning;
    private boolean IsSuccess;
    private String SuccessMessage;
    private String WarningMessage;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public boolean isHasWarning() {
        return HasWarning;
    }

    public void setHasWarning(boolean HasWarning) {
        this.HasWarning = HasWarning;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public void setSuccessMessage(String SuccessMessage) {
        this.SuccessMessage = SuccessMessage;
    }

    public String getWarningMessage() {
        return WarningMessage;
    }

    public void setWarningMessage(String WarningMessage) {
        this.WarningMessage = WarningMessage;
    }
}
