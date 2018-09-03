package com.wms.newwmsapp.model;

import java.util.List;

/**
 * Created by cheng on 2018/8/30.
 */

public class BatchTaskModel {

    /**
     * Total : 3
     * Data : [{"WaveOutStockCode":"BAJW170824000002","StatusCode":2,"StatusDate":"2017-09-11T14:14:51.864647","Remark":null,"StockCode":"BAJ","StockName":"BAJ","StatusName":"波次分拣确认","ShipCode":null,"ShipCarNo":"","ShipPlate":null,"ConfirmUserName":null,"SortUserCode":null,"SortUserName":null,"CustGoodsCodes":null,"PackageCode":null,"DeliveryStockCode":null,"OutstockInformNum":2,"PickType":1,"Code":"BAJP170824000003","WaveOutStockPickCode":"BAJP170824000003","IsRfEnforce":0,"IsConfirm":0,"ConfirmUserCode":null,"CreateDate":"2017-09-11T14:15:53.614","CreateBy":"dev","UpdateDate":"2017-09-12T13:56:25.073699","UpdateBy":"dev"},{"WaveOutStockCode":"BAJW170824000002","StatusCode":2,"StatusDate":"2017-09-12T13:55:59.050861","Remark":"","StockCode":"BAJ","StockName":"BAJ","StatusName":"波次分拣确认","ShipCode":null,"ShipCarNo":"","ShipPlate":null,"ConfirmUserName":null,"SortUserCode":null,"SortUserName":null,"CustGoodsCodes":null,"PackageCode":null,"DeliveryStockCode":null,"OutstockInformNum":1,"PickType":1,"Code":"BAJP170912000001","WaveOutStockPickCode":"BAJP170912000001","IsRfEnforce":0,"IsConfirm":0,"ConfirmUserCode":null,"CreateDate":"2017-09-12T13:56:58.222","CreateBy":"dev","UpdateDate":"0001-01-01T00:00:00","UpdateBy":null},{"WaveOutStockCode":"BAJW180829000001","StatusCode":2,"StatusDate":"2018-08-29T14:12:59.233308","Remark":"","StockCode":"BAJ","StockName":"BAJ","StatusName":"波次分拣确认","ShipCode":null,"ShipCarNo":"","ShipPlate":null,"ConfirmUserName":null,"SortUserCode":null,"SortUserName":null,"CustGoodsCodes":"BAJ001","PackageCode":null,"DeliveryStockCode":null,"OutstockInformNum":2,"PickType":1,"Code":"BAJP180829000001","WaveOutStockPickCode":"BAJP180829000001","IsRfEnforce":0,"IsConfirm":0,"ConfirmUserCode":null,"CreateDate":"2018-08-29T14:12:59.061","CreateBy":"dev","UpdateDate":"0001-01-01T00:00:00","UpdateBy":null}]
     */

    private int Total;
    private List<DataBean> Data;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * WaveOutStockCode : BAJW170824000002
         * StatusCode : 2
         * StatusDate : 2017-09-11T14:14:51.864647
         * Remark : null
         * StockCode : BAJ
         * StockName : BAJ
         * StatusName : 波次分拣确认
         * ShipCode : null
         * ShipCarNo :
         * ShipPlate : null
         * ConfirmUserName : null
         * SortUserCode : null
         * SortUserName : null
         * CustGoodsCodes : null
         * PackageCode : null
         * DeliveryStockCode : null
         * OutstockInformNum : 2
         * PickType : 1
         * Code : BAJP170824000003
         * WaveOutStockPickCode : BAJP170824000003
         * IsRfEnforce : 0
         * IsConfirm : 0
         * ConfirmUserCode : null
         * CreateDate : 2017-09-11T14:15:53.614
         * CreateBy : dev
         * UpdateDate : 2017-09-12T13:56:25.073699
         * UpdateBy : dev
         */

        private String WaveOutStockCode;
        private String StatusCode;
        private String StatusDate;
        private String Remark;
        private String StockCode;
        private String StockName;
        private String StatusName;
        private String ShipCode;
        private String ShipCarNo;
        private String ShipPlate;
        private String ConfirmUserName;
        private String SortUserCode;
        private String SortUserName;
        private String CustGoodsCodes;
        private String PackageCode;
        private String DeliveryStockCode;
        private String OutstockInformNum;
        private String PickType;
        private String Code;
        private String WaveOutStockPickCode;
        private String IsRfEnforce;
        private String IsConfirm;
        private String ConfirmUserCode;
        private String CreateDate;
        private String CreateBy;
        private String UpdateDate;
        private String UpdateBy;

        public String getWaveOutStockCode() {
            return WaveOutStockCode;
        }

        public void setWaveOutStockCode(String waveOutStockCode) {
            WaveOutStockCode = waveOutStockCode;
        }

        public String getStatusCode() {
            return StatusCode;
        }

        public void setStatusCode(String statusCode) {
            StatusCode = statusCode;
        }

        public String getStatusDate() {
            return StatusDate;
        }

        public void setStatusDate(String statusDate) {
            StatusDate = statusDate;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getStockCode() {
            return StockCode;
        }

        public void setStockCode(String stockCode) {
            StockCode = stockCode;
        }

        public String getStockName() {
            return StockName;
        }

        public void setStockName(String stockName) {
            StockName = stockName;
        }

        public String getStatusName() {
            return StatusName;
        }

        public void setStatusName(String statusName) {
            StatusName = statusName;
        }

        public String getShipCode() {
            return ShipCode;
        }

        public void setShipCode(String shipCode) {
            ShipCode = shipCode;
        }

        public String getShipCarNo() {
            return ShipCarNo;
        }

        public void setShipCarNo(String shipCarNo) {
            ShipCarNo = shipCarNo;
        }

        public String getShipPlate() {
            return ShipPlate;
        }

        public void setShipPlate(String shipPlate) {
            ShipPlate = shipPlate;
        }

        public String getConfirmUserName() {
            return ConfirmUserName;
        }

        public void setConfirmUserName(String confirmUserName) {
            ConfirmUserName = confirmUserName;
        }

        public String getSortUserCode() {
            return SortUserCode;
        }

        public void setSortUserCode(String sortUserCode) {
            SortUserCode = sortUserCode;
        }

        public String getSortUserName() {
            return SortUserName;
        }

        public void setSortUserName(String sortUserName) {
            SortUserName = sortUserName;
        }

        public String getCustGoodsCodes() {
            return CustGoodsCodes;
        }

        public void setCustGoodsCodes(String custGoodsCodes) {
            CustGoodsCodes = custGoodsCodes;
        }

        public String getPackageCode() {
            return PackageCode;
        }

        public void setPackageCode(String packageCode) {
            PackageCode = packageCode;
        }

        public String getDeliveryStockCode() {
            return DeliveryStockCode;
        }

        public void setDeliveryStockCode(String deliveryStockCode) {
            DeliveryStockCode = deliveryStockCode;
        }

        public String getOutstockInformNum() {
            return OutstockInformNum;
        }

        public void setOutstockInformNum(String outstockInformNum) {
            OutstockInformNum = outstockInformNum;
        }

        public String getPickType() {
            return PickType;
        }

        public void setPickType(String pickType) {
            PickType = pickType;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getWaveOutStockPickCode() {
            return WaveOutStockPickCode;
        }

        public void setWaveOutStockPickCode(String waveOutStockPickCode) {
            WaveOutStockPickCode = waveOutStockPickCode;
        }

        public String getIsRfEnforce() {
            return IsRfEnforce;
        }

        public void setIsRfEnforce(String isRfEnforce) {
            IsRfEnforce = isRfEnforce;
        }

        public String getIsConfirm() {
            return IsConfirm;
        }

        public void setIsConfirm(String isConfirm) {
            IsConfirm = isConfirm;
        }

        public String getConfirmUserCode() {
            return ConfirmUserCode;
        }

        public void setConfirmUserCode(String confirmUserCode) {
            ConfirmUserCode = confirmUserCode;
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
}
