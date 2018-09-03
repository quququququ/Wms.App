package com.wms.newwmsapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cheng on 2018/7/4.
 */

public class DownToOtherModel implements Serializable {

    /**
     * IsSuccess : true
     * ErrorMessage : null
     * RecordCount : 1
     * Data : [{"Code":null,"StockCode":"BAJ","StockName":"BAJ","GoodsAreaCode":"32ED7F8C78FFA697E0502D0AC8943299","GoodsAreaName":"B2C分拣区","GoodsPosCode":"32F3AF01AD2C7364E0502D0AC89412A2","GoodsPosName":"A10-01-03","CustomerCode":"货品编码","CustGoodsName":"BAJ","GoodsCode":"货品主键","GoodsName":"货品名称","GoodsUnitCode":"fe4e70cf-8972-4a92-b71b-66ccd663b631","GoodsUnitName":"货品单位","Num":"在库数量","OutNum":"0","OffNum":"0","BarCode":"货品条码","ProductId":"\u201c批次号\u201d","IsNoOut":0,"ProductionDate":"生产日期","BoxId":null}]
     * GoodsTypeSum : 1
     * GoodsSum : 10
     */

    private boolean IsSuccess;
    private String ErrorMessage;
    private String RecordCount;
    private String GoodsTypeSum;
    private String GoodsSum;
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

    public String getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(String RecordCount) {
        this.RecordCount = RecordCount;
    }

    public String getGoodsTypeSum() {
        return GoodsTypeSum;
    }

    public void setGoodsTypeSum(String GoodsTypeSum) {
        this.GoodsTypeSum = GoodsTypeSum;
    }

    public String getGoodsSum() {
        return GoodsSum;
    }

    public void setGoodsSum(String GoodsSum) {
        this.GoodsSum = GoodsSum;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable{
        /**
         * Code : null
         * StockCode : BAJ
         * StockName : BAJ
         * GoodsAreaCode : 32ED7F8C78FFA697E0502D0AC8943299
         * GoodsAreaName : B2C分拣区
         * GoodsPosCode : 32F3AF01AD2C7364E0502D0AC89412A2
         * GoodsPosName : A10-01-03
         * CustomerCode : 货品编码
         * CustGoodsName : BAJ
         * GoodsCode : 货品主键
         * GoodsName : 货品名称
         * GoodsUnitCode : fe4e70cf-8972-4a92-b71b-66ccd663b631
         * GoodsUnitName : 货品单位
         * Num : 在库数量
         * OutNum : 0
         * OffNum : 0
         * BarCode : 货品条码
         * ProductId : “批次号”
         * IsNoOut : 0
         * ProductionDate : 生产日期
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
        private String CustGoodsName;
        private String GoodsCode;
        private String GoodsName;
        private String GoodsUnitCode;
        private String GoodsUnitName;
        private String Num;
        private String OutNum;
        private String OffNum;
        private String BarCode;
        private String ProductId;
        private double IsNoOut;
        private String ProductionDate;
        private String BoxId;
        private String QualityCode;
        private String CustGoodsCode;
        private String upNo; //减的数量
        private double originNo;  //原始数量

        public String getUpNo() {
            return upNo;
        }

        public void setUpNo(String upNo) {
            this.upNo = upNo;
        }

        public double getOriginNo() {
            return originNo;
        }

        public void setOriginNo(double originNo) {
            this.originNo = originNo;
        }

        public String getQualityCode() {
            return QualityCode;
        }

        public void setQualityCode(String qualityCode) {
            QualityCode = qualityCode;
        }

        public String getCustGoodsCode() {
            return CustGoodsCode;
        }

        public void setCustGoodsCode(String custGoodsCode) {
            CustGoodsCode = custGoodsCode;
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

        public double getIsNoOut() {
            return IsNoOut;
        }

        public void setIsNoOut(double IsNoOut) {
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
