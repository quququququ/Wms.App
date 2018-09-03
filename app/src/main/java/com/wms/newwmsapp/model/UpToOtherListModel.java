package com.wms.newwmsapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cheng on 2018/7/19.
 */

public class UpToOtherListModel implements Serializable{

    /**
     * IsSuccess : true
     * ErrorMessage : null
     * RecordCount : 1
     * Data : [{"Code":null,"StockCode":"BAJ","StockName":"BAJ","GoodsAreaCode":"3210E84CAA750381E0502D0AC894024C","GoodsAreaName":"默认","GoodsPosCode":"f2ffb88b-1b54-49a1-a8d3-2a25f445d70e","GoodsPosName":"88-88-88-88","CustomerCode":"AAA","CustGoodsCode":"BAJ001","CustGoodsName":"BAJ","GoodsCode":"8C2F79034B4142DF9617FF013B48B6E0","GoodsName":"G119A强化地板 1215*140*12","QualityCode":"lp","GoodsUnitCode":"fe4e70cf-8972-4a92-b71b-66ccd663b631","GoodsUnitName":"箱","Num":"1574","OutNum":"0","OffNum":"0","BarCode":"4154886","ProductId":null,"IsNoOut":0,"ProductionDate":"0001-01-01T00:00:00","BoxId":null}]
     * GoodsTypeSum : 1
     * GoodsSum : 1574
     */

    private boolean IsSuccess;
    private String ErrorMessage;
    private int RecordCount;
    private int GoodsTypeSum;
    private int GoodsSum;
    private List<DataBean> Data;

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

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int RecordCount) {
        this.RecordCount = RecordCount;
    }

    public int getGoodsTypeSum() {
        return GoodsTypeSum;
    }

    public void setGoodsTypeSum(int GoodsTypeSum) {
        this.GoodsTypeSum = GoodsTypeSum;
    }

    public int getGoodsSum() {
        return GoodsSum;
    }

    public void setGoodsSum(int GoodsSum) {
        this.GoodsSum = GoodsSum;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable {
        /**
         * Code : null
         * StockCode : BAJ
         * StockName : BAJ
         * GoodsAreaCode : 3210E84CAA750381E0502D0AC894024C
         * GoodsAreaName : 默认
         * GoodsPosCode : f2ffb88b-1b54-49a1-a8d3-2a25f445d70e
         * GoodsPosName : 88-88-88-88
         * CustomerCode : AAA
         * CustGoodsCode : BAJ001
         * CustGoodsName : BAJ
         * GoodsCode : 8C2F79034B4142DF9617FF013B48B6E0
         * GoodsName : G119A强化地板 1215*140*12
         * QualityCode : lp
         * GoodsUnitCode : fe4e70cf-8972-4a92-b71b-66ccd663b631
         * GoodsUnitName : 箱
         * Num : 1574
         * OutNum : 0
         * OffNum : 0
         * BarCode : 4154886
         * ProductId : null
         * IsNoOut : 0
         * ProductionDate : 0001-01-01T00:00:00
         * BoxId : null
         */

        private String Code;
        private String StockCode;
        private String StockName;
        private String GoodsAreaCode;
        private String GoodsAreaName;
        private String GoodsPosCode;
        private String GoodsPosName;
        private String CustomerCode;
        private String CustGoodsCode;
        private String CustGoodsName;
        private String GoodsCode;
        private String GoodsName;
        private String QualityCode;
        private String GoodsUnitCode;
        private String GoodsUnitName;
        private String Num;
        private String OutNum;
        private String OffNum;
        private String BarCode;
        private String ProductId;
        private int IsNoOut;
        private String ProductionDate;
        private String BoxId;
        private String upNo; //减的数量
        private int originNo;  //原始数量

        public int getOriginNo() {
            return originNo;
        }

        public void setOriginNo(int originNo) {
            this.originNo = originNo;
        }

        public String getUpNo() {
            return upNo;
        }

        public void setUpNo(String upNo) {
            this.upNo = upNo;
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

        public String getStockName() {
            return StockName;
        }

        public void setStockName(String StockName) {
            this.StockName = StockName;
        }

        public String getGoodsAreaCode() {
            return GoodsAreaCode;
        }

        public void setGoodsAreaCode(String GoodsAreaCode) {
            this.GoodsAreaCode = GoodsAreaCode;
        }

        public String getGoodsAreaName() {
            return GoodsAreaName;
        }

        public void setGoodsAreaName(String GoodsAreaName) {
            this.GoodsAreaName = GoodsAreaName;
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

        public String getCustGoodsName() {
            return CustGoodsName;
        }

        public void setCustGoodsName(String CustGoodsName) {
            this.CustGoodsName = CustGoodsName;
        }

        public String getGoodsCode() {
            return GoodsCode;
        }

        public void setGoodsCode(String GoodsCode) {
            this.GoodsCode = GoodsCode;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public String getQualityCode() {
            return QualityCode;
        }

        public void setQualityCode(String QualityCode) {
            this.QualityCode = QualityCode;
        }

        public String getGoodsUnitCode() {
            return GoodsUnitCode;
        }

        public void setGoodsUnitCode(String GoodsUnitCode) {
            this.GoodsUnitCode = GoodsUnitCode;
        }

        public String getGoodsUnitName() {
            return GoodsUnitName;
        }

        public void setGoodsUnitName(String GoodsUnitName) {
            this.GoodsUnitName = GoodsUnitName;
        }

        public String getNum() {
            return Num;
        }

        public void setNum(String Num) {
            this.Num = Num;
        }

        public String getOutNum() {
            return OutNum;
        }

        public void setOutNum(String OutNum) {
            this.OutNum = OutNum;
        }

        public String getOffNum() {
            return OffNum;
        }

        public void setOffNum(String OffNum) {
            this.OffNum = OffNum;
        }

        public String getBarCode() {
            return BarCode;
        }

        public void setBarCode(String BarCode) {
            this.BarCode = BarCode;
        }

        public String getProductId() {
            return ProductId;
        }

        public void setProductId(String ProductId) {
            this.ProductId = ProductId;
        }

        public int getIsNoOut() {
            return IsNoOut;
        }

        public void setIsNoOut(int IsNoOut) {
            this.IsNoOut = IsNoOut;
        }

        public String getProductionDate() {
            return ProductionDate;
        }

        public void setProductionDate(String ProductionDate) {
            this.ProductionDate = ProductionDate;
        }

        public String getBoxId() {
            return BoxId;
        }

        public void setBoxId(String BoxId) {
            this.BoxId = BoxId;
        }
    }
}
