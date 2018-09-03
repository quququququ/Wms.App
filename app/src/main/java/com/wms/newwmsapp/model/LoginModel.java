package com.wms.newwmsapp.model;

import java.util.List;

/**
 * Created by qupengcheng on 2018/1/12.
 */

public class LoginModel {

    /**
     * UserCode : dev
     * IsSuccess : true
     * ErrorMessage : null
     * UserName : dev
     * StockList : [{"CustGoods":[{"IfProductId":0,"CustGoodsName":"测试货主","IfBoxId":0,"CustGoodsCode":"TEST"}],"StockName":"测试库","StockCode":"TEST"},{"CustGoods":[{"IfProductId":0,"CustGoodsName":"华新客户一号","IfBoxId":0,"CustGoodsCode":"HXKHYH"}],"StockName":"华新仓","StockCode":"ZAS"},{"CustGoods":[{"IfProductId":0,"CustGoodsName":"熊二","IfBoxId":0,"CustGoodsCode":"ZQ"}],"StockName":"星沙仓","StockCode":"XSC"},{"CustGoods":[{"IfProductId":0,"CustGoodsName":"亮晶晶开啡","IfBoxId":0,"CustGoodsCode":"LJJKF"}],"StockName":"大家好的仓库","StockCode":"DAO"},{"CustGoods":[{"IfProductId":0,"CustGoodsName":"猪八戒","IfBoxId":0,"CustGoodsCode":"ZBJ"}],"StockName":"东莞大岭山仓","StockCode":"POP"},{"CustGoods":[{"IfProductId":0,"CustGoodsName":"唐生","IfBoxId":0,"CustGoodsCode":"DFG"}],"StockName":"沈阳仓","StockCode":"DFG"},{"CustGoods":[{"IfProductId":0,"CustGoodsName":"BAJ","IfBoxId":0,"CustGoodsCode":"BAJ001"}],"StockName":"BAJ","StockCode":"BAJ"},{"CustGoods":[{"IfProductId":0,"CustGoodsName":"雅迪","IfBoxId":0,"CustGoodsCode":"YD"}],"StockName":"怀安仓","StockCode":"HAC"}]
     * HasWarning : false
     * WarningMessage : null
     * SuccessMessage : null
     */

    private String UserCode;
    private boolean IsSuccess;
    private String ErrorMessage;
    private String UserName;
    private boolean HasWarning;
    private Object WarningMessage;
    private Object SuccessMessage;
    private List<StockListBean> StockList;

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

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public boolean isHasWarning() {
        return HasWarning;
    }

    public void setHasWarning(boolean HasWarning) {
        this.HasWarning = HasWarning;
    }

    public Object getWarningMessage() {
        return WarningMessage;
    }

    public void setWarningMessage(Object WarningMessage) {
        this.WarningMessage = WarningMessage;
    }

    public Object getSuccessMessage() {
        return SuccessMessage;
    }

    public void setSuccessMessage(Object SuccessMessage) {
        this.SuccessMessage = SuccessMessage;
    }

    public List<StockListBean> getStockList() {
        return StockList;
    }

    public void setStockList(List<StockListBean> StockList) {
        this.StockList = StockList;
    }

    public static class StockListBean {
        /**
         * CustGoods : [{"IfProductId":0,"CustGoodsName":"测试货主","IfBoxId":0,"CustGoodsCode":"TEST"}]
         * StockName : 测试库
         * StockCode : TEST
         */

        private String StockName;
        private String StockCode;
        private List<CustGoodsBean> CustGoods;

        public String getStockName() {
            return StockName;
        }

        public void setStockName(String StockName) {
            this.StockName = StockName;
        }

        public String getStockCode() {
            return StockCode;
        }

        public void setStockCode(String StockCode) {
            this.StockCode = StockCode;
        }

        public List<CustGoodsBean> getCustGoods() {
            return CustGoods;
        }

        public void setCustGoods(List<CustGoodsBean> CustGoods) {
            this.CustGoods = CustGoods;
        }

        public static class CustGoodsBean {
            /**
             * IfProductId : 0
             * CustGoodsName : 测试货主
             * IfBoxId : 0
             * CustGoodsCode : TEST
             */

            private int IfProductId;
            private String CustGoodsName;
            private int IfBoxId;
            private String CustGoodsCode;

            public int getIfProductId() {
                return IfProductId;
            }

            public void setIfProductId(int IfProductId) {
                this.IfProductId = IfProductId;
            }

            public String getCustGoodsName() {
                return CustGoodsName;
            }

            public void setCustGoodsName(String CustGoodsName) {
                this.CustGoodsName = CustGoodsName;
            }

            public int getIfBoxId() {
                return IfBoxId;
            }

            public void setIfBoxId(int IfBoxId) {
                this.IfBoxId = IfBoxId;
            }

            public String getCustGoodsCode() {
                return CustGoodsCode;
            }

            public void setCustGoodsCode(String CustGoodsCode) {
                this.CustGoodsCode = CustGoodsCode;
            }
        }
    }

}
