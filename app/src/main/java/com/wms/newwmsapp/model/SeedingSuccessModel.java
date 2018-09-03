package com.wms.newwmsapp.model;

/**
 * Created by cheng on 2018/6/25.
 */

public class SeedingSuccessModel {

    /**
     * IsSuccess : true
     * ErrorMessage : 已经播种完成！
     * Details : {"OutStockDeliveryCode":"CBAJ171017001113","Num":1,"SortNo":1,"GoodsName":"G119A强化地板 1215*140*12","GoodsCustomerCode":"AAA","GoodsBarcode":"4154886","GoodsColor":"黑","GoodsModel":"1215*140*12"}
     */

    private boolean IsSuccess;
    private String ErrorMessage;
    private DetailsBean Details;

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

    public DetailsBean getDetails() {
        return Details;
    }

    public void setDetails(DetailsBean Details) {
        this.Details = Details;
    }

    public static class DetailsBean {
        /**
         * OutStockDeliveryCode : CBAJ171017001113
         * Num : 1
         * SortNo : 1
         * GoodsName : G119A强化地板 1215*140*12
         * GoodsCustomerCode : AAA
         * GoodsBarcode : 4154886
         * GoodsColor : 黑
         * GoodsModel : 1215*140*12
         */

        private String OutStockDeliveryCode;
        private int Num;
        private int SortNo;
        private String GoodsName;
        private String GoodsCustomerCode;
        private String GoodsBarcode;
        private String GoodsColor;
        private String GoodsModel;

        public String getOutStockDeliveryCode() {
            return OutStockDeliveryCode;
        }

        public void setOutStockDeliveryCode(String OutStockDeliveryCode) {
            this.OutStockDeliveryCode = OutStockDeliveryCode;
        }

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
        }

        public int getSortNo() {
            return SortNo;
        }

        public void setSortNo(int SortNo) {
            this.SortNo = SortNo;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public String getGoodsCustomerCode() {
            return GoodsCustomerCode;
        }

        public void setGoodsCustomerCode(String GoodsCustomerCode) {
            this.GoodsCustomerCode = GoodsCustomerCode;
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

        public String getGoodsModel() {
            return GoodsModel;
        }

        public void setGoodsModel(String GoodsModel) {
            this.GoodsModel = GoodsModel;
        }
    }
}
