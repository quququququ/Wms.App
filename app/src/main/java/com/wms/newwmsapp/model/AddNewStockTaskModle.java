package com.wms.newwmsapp.model;

import java.util.List;

/**
 * Created by qupengcheng on 2018/1/12.
 */

public class AddNewStockTaskModle {

    /**
     * ErrorMessage :
     * Details : [{"GoodsUnit":[{"UnitName":"箱","IsDefault":0,"StockKeepingUnit":1,"UpdateBy":null,"CreateDate":"2017-06-14T16:42:28.778","Code":"a7c95bfa-f360-437f-a3a6-7d8943e5f76d","Unit":10,"UnitNo":"111","IsValid":1,"CreateBy":null,"GoodsCode":"001DB031BF46466A99C065C119AE2E3A","UpdateDate":"2017-12-05T10:31:25.276583","SettleUnit":0,"Remark":null},{"UnitName":"瓶","IsDefault":1,"StockKeepingUnit":0,"UpdateBy":null,"CreateDate":"2017-01-09T17:09:18.405","Code":"7cd57404-040b-4418-84cd-26306c805310","Unit":1,"UnitNo":"213","IsValid":1,"CreateBy":null,"GoodsCode":"001DB031BF46466A99C065C119AE2E3A","UpdateDate":"2017-12-05T10:31:25.260823","SettleUnit":0,"Remark":null}],"Name":"36111A电视柜玉兰亮2-2面、底板","Code":"001DB031BF46466A99C065C119AE2E3A"}]
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<DetailsBean> Details;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public List<DetailsBean> getDetails() {
        return Details;
    }

    public void setDetails(List<DetailsBean> Details) {
        this.Details = Details;
    }

    public static class DetailsBean {
        /**
         * GoodsUnit : [{"UnitName":"箱","IsDefault":0,"StockKeepingUnit":1,"UpdateBy":null,"CreateDate":"2017-06-14T16:42:28.778","Code":"a7c95bfa-f360-437f-a3a6-7d8943e5f76d","Unit":10,"UnitNo":"111","IsValid":1,"CreateBy":null,"GoodsCode":"001DB031BF46466A99C065C119AE2E3A","UpdateDate":"2017-12-05T10:31:25.276583","SettleUnit":0,"Remark":null},{"UnitName":"瓶","IsDefault":1,"StockKeepingUnit":0,"UpdateBy":null,"CreateDate":"2017-01-09T17:09:18.405","Code":"7cd57404-040b-4418-84cd-26306c805310","Unit":1,"UnitNo":"213","IsValid":1,"CreateBy":null,"GoodsCode":"001DB031BF46466A99C065C119AE2E3A","UpdateDate":"2017-12-05T10:31:25.260823","SettleUnit":0,"Remark":null}]
         * Name : 36111A电视柜玉兰亮2-2面、底板
         * Code : 001DB031BF46466A99C065C119AE2E3A
         */

        private String Name;
        private String Code;
        private List<GoodsUnitBean> GoodsUnit;
        private String CustomerCode;
        private String BarCode;

        public String getCustomerCode() {
            return CustomerCode;
        }

        public void setCustomerCode(String customerCode) {
            CustomerCode = customerCode;
        }

        public String getBarCode() {
            return BarCode;
        }

        public void setBarCode(String barCode) {
            BarCode = barCode;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public List<GoodsUnitBean> getGoodsUnit() {
            return GoodsUnit;
        }

        public void setGoodsUnit(List<GoodsUnitBean> GoodsUnit) {
            this.GoodsUnit = GoodsUnit;
        }

        public static class GoodsUnitBean {
            /**
             * UnitName : 箱
             * IsDefault : 0
             * StockKeepingUnit : 1
             * UpdateBy : null
             * CreateDate : 2017-06-14T16:42:28.778
             * Code : a7c95bfa-f360-437f-a3a6-7d8943e5f76d
             * Unit : 10
             * UnitNo : 111
             * IsValid : 1
             * CreateBy : null
             * GoodsCode : 001DB031BF46466A99C065C119AE2E3A
             * UpdateDate : 2017-12-05T10:31:25.276583
             * SettleUnit : 0
             * Remark : null
             */

            private String UnitName;
            private int IsDefault;
            private int StockKeepingUnit;
            private String UpdateBy;
            private String CreateDate;
            private String Code;
            private int Unit;
            private String UnitNo;
            private int IsValid;
            private String CreateBy;
            private String GoodsCode;
            private String UpdateDate;
            private int SettleUnit;
            private String Remark;


            public String getUnitName() {
                return UnitName;
            }

            public void setUnitName(String UnitName) {
                this.UnitName = UnitName;
            }

            public int getIsDefault() {
                return IsDefault;
            }

            public void setIsDefault(int IsDefault) {
                this.IsDefault = IsDefault;
            }

            public int getStockKeepingUnit() {
                return StockKeepingUnit;
            }

            public void setStockKeepingUnit(int StockKeepingUnit) {
                this.StockKeepingUnit = StockKeepingUnit;
            }

            public String getUpdateBy() {
                return UpdateBy;
            }

            public void setUpdateBy(String UpdateBy) {
                this.UpdateBy = UpdateBy;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public int getUnit() {
                return Unit;
            }

            public void setUnit(int Unit) {
                this.Unit = Unit;
            }

            public String getUnitNo() {
                return UnitNo;
            }

            public void setUnitNo(String UnitNo) {
                this.UnitNo = UnitNo;
            }

            public int getIsValid() {
                return IsValid;
            }

            public void setIsValid(int IsValid) {
                this.IsValid = IsValid;
            }

            public String getCreateBy() {
                return CreateBy;
            }

            public void setCreateBy(String CreateBy) {
                this.CreateBy = CreateBy;
            }

            public String getGoodsCode() {
                return GoodsCode;
            }

            public void setGoodsCode(String GoodsCode) {
                this.GoodsCode = GoodsCode;
            }

            public String getUpdateDate() {
                return UpdateDate;
            }

            public void setUpdateDate(String UpdateDate) {
                this.UpdateDate = UpdateDate;
            }

            public int getSettleUnit() {
                return SettleUnit;
            }

            public void setSettleUnit(int SettleUnit) {
                this.SettleUnit = SettleUnit;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }
        }
    }
}
