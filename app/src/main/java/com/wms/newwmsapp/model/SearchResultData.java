package com.wms.newwmsapp.model;

/**
 * Created by cheng on 2018/6/19.
 */

public class SearchResultData {

    /**
     * ErrorMessage : 波次中没有此条码商品！
     * Details : {"GoodsCustomerCode":null,"Num":0,"GoodsModel":null,"OutStockDeliveryCode":null,"SortNo":0,"GoodsBarcode":null,"GoodsColor":null,"GoodsName":null}
     * IsSuccess : false
     */

    private String ErrorMessage;
    private DetailsBean Details;
    private boolean IsSuccess;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public DetailsBean getDetails() {
        return Details;
    }

    public void setDetails(DetailsBean Details) {
        this.Details = Details;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public static class DetailsBean {
        /**
         * GoodsCustomerCode : null
         * Num : 0
         * GoodsModel : null
         * OutStockDeliveryCode : null
         * SortNo : 0
         * GoodsBarcode : null
         * GoodsColor : null
         * GoodsName : null
         */

        private String GoodsCustomerCode;
        private String Num;
        private String GoodsModel;
        private String OutStockDeliveryCode;
        private String SortNo;
        private String GoodsBarcode;
        private String GoodsColor;
        private String GoodsName;

        public String getGoodsCustomerCode() {
            return GoodsCustomerCode;
        }

        public void setGoodsCustomerCode(String GoodsCustomerCode) {
            this.GoodsCustomerCode = GoodsCustomerCode;
        }

        public String getNum() {
            return Num;
        }

        public void setNum(String Num) {
            this.Num = Num;
        }

        public String getGoodsModel() {
            return GoodsModel;
        }

        public void setGoodsModel(String GoodsModel) {
            this.GoodsModel = GoodsModel;
        }

        public String getOutStockDeliveryCode() {
            return OutStockDeliveryCode;
        }

        public void setOutStockDeliveryCode(String OutStockDeliveryCode) {
            this.OutStockDeliveryCode = OutStockDeliveryCode;
        }

        public String getSortNo() {
            return SortNo;
        }

        public void setSortNo(String SortNo) {
            this.SortNo = SortNo;
        }

        public String getGoodsBarcode() {
            return GoodsBarcode;
        }

        public void setGoodsBarcode(String GoodsBarcode) {
            this.GoodsBarcode = GoodsBarcode;
        }

        public String getGoodsColor() {
            return GoodsColor;
        }

        public void setGoodsColor(String GoodsColor) {
            this.GoodsColor = GoodsColor;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }
    }
}
