package com.wms.newwmsapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qupengcheng on 2018/1/10.
 */

public class TaskDetailModel implements Serializable{


    /**
     * UserCode : dev
     * IsSuccess : true
     * StockCheckTaskCode : BAJPT17121500003
     * ErrorMessage :
     * Details : [{"UnitName":"托","BarCode":"4154886","OldSum":99,"Num":99,"QualityName":"良品","CustomerCode":"4154886","CustGoodsCode":"BAJ001","QualityCode":"lp","ProductId":null,"GoodsName":"百得适 G119A强化地板 1215*140*12","ProductionDate":null,"GoodsUnitCode":"6d821942-f60a-43ff-8195-1bf8b4e582d0","GoodsCode":"00B8861CE79B46FC81B29FAF07DEA972","StockCheckTaskCode":"BAJPT17121500003"},{"UnitName":"个","BarCode":"4154886","OldSum":9995,"Num":9995,"QualityName":"良品","CustomerCode":"4154886","CustGoodsCode":"BAJ001","QualityCode":"lp","ProductId":null,"GoodsName":"百得适 G119A强化地板 1215*140*12","ProductionDate":null,"GoodsUnitCode":"35D71E665C84980BE050180A00C9269A","GoodsCode":"00B8861CE79B46FC81B29FAF07DEA972","StockCheckTaskCode":"BAJPT17121500003"}]
     * PosCode : 32F3AF01AB0F7364E0502D0AC89412A2
     */

    private String UserCode;
    private boolean IsSuccess;
    private String StockCheckTaskCode;
    private String ErrorMessage;
    private String PosCode;
    private List<DetailsBean> Details;

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getStockCheckTaskCode() {
        return StockCheckTaskCode;
    }

    public void setStockCheckTaskCode(String StockCheckTaskCode) {
        this.StockCheckTaskCode = StockCheckTaskCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getPosCode() {
        return PosCode;
    }

    public void setPosCode(String PosCode) {
        this.PosCode = PosCode;
    }

    public List<DetailsBean> getDetails() {
        return Details;
    }

    public void setDetails(List<DetailsBean> Details) {
        this.Details = Details;
    }

    public static class DetailsBean {
        /**
         * UnitName : 托
         * BarCode : 4154886
         * OldSum : 99
         * Num : 99
         * QualityName : 良品
         * CustomerCode : 4154886
         * CustGoodsCode : BAJ001
         * QualityCode : lp
         * ProductId : null
         * GoodsName : 百得适 G119A强化地板 1215*140*12
         * ProductionDate : null
         * GoodsUnitCode : 6d821942-f60a-43ff-8195-1bf8b4e582d0
         * GoodsCode : 00B8861CE79B46FC81B29FAF07DEA972
         * StockCheckTaskCode : BAJPT17121500003
         */

        private String UnitName;
        private String BarCode;
        private String OldSum;
        private String Num;
        private String QualityName;
        private String CustomerCode;
        private String CustGoodsCode;
        private String QualityCode;
        private String ProductId;
        private String GoodsName;
        private String ProductionDate;
        private String GoodsUnitCode;
        private String GoodsCode;
        private String StockCheckTaskCode;
        private boolean isAdd;

        public boolean isAdd() {
            return isAdd;
        }

        public void setAdd(boolean add) {
            isAdd = add;
        }

        public String getUnitName() {
            return UnitName;
        }

        public void setUnitName(String UnitName) {
            this.UnitName = UnitName;
        }

        public String getBarCode() {
            return BarCode;
        }

        public void setBarCode(String BarCode) {
            this.BarCode = BarCode;
        }

        public String getOldSum() {
            return OldSum;
        }

        public void setOldSum(String OldSum) {
            this.OldSum = OldSum;
        }

        public String getNum() {
            return Num;
        }

        public void setNum(String Num) {
            this.Num = Num;
        }

        public String getQualityName() {
            return QualityName;
        }

        public void setQualityName(String QualityName) {
            this.QualityName = QualityName;
        }

        public String getCustomerCode() {
            return CustomerCode;
        }

        public void setCustomerCode(String CustomerCode) {
            this.CustomerCode = CustomerCode;
        }

        public String getCustGoodsCode() {
            return CustGoodsCode;
        }

        public void setCustGoodsCode(String CustGoodsCode) {
            this.CustGoodsCode = CustGoodsCode;
        }

        public String getQualityCode() {
            return QualityCode;
        }

        public void setQualityCode(String QualityCode) {
            this.QualityCode = QualityCode;
        }

        public Object getProductId() {
            return ProductId;
        }

        public void setProductId(String ProductId) {
            this.ProductId = ProductId;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public String getProductionDate() {
            return ProductionDate;
        }

        public void setProductionDate(String ProductionDate) {
            this.ProductionDate = ProductionDate;
        }

        public String getGoodsUnitCode() {
            return GoodsUnitCode;
        }

        public void setGoodsUnitCode(String GoodsUnitCode) {
            this.GoodsUnitCode = GoodsUnitCode;
        }

        public String getGoodsCode() {
            return GoodsCode;
        }

        public void setGoodsCode(String GoodsCode) {
            this.GoodsCode = GoodsCode;
        }

        public String getStockCheckTaskCode() {
            return StockCheckTaskCode;
        }

        public void setStockCheckTaskCode(String StockCheckTaskCode) {
            this.StockCheckTaskCode = StockCheckTaskCode;
        }
    }
}
