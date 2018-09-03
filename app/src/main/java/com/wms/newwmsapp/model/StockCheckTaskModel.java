package com.wms.newwmsapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qupengcheng on 2018/1/10.
 */

public class StockCheckTaskModel implements Serializable {

    /**
     * ErrorMessage :
     * Details : [{"GoodsAreaName":"B2C分拣区","GoodsPosCode":"32F1CB27B289FCC7E0502D0AC8941FF0","GoodsPosName":"A01-01-01","StockCheckTaskCode":"BAJPT17121500003","StockCheckTaskCreateDate":"2017-12-15T16:20:25.8"}]
     */

    private String ErrorMessage;
    private List<DetailsBean> Details;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public List<DetailsBean> getDetails() {
        return Details;
    }

    public void setDetails(List<DetailsBean> Details) {
        this.Details = Details;
    }

    public static class DetailsBean {
        /**
         * GoodsAreaName : B2C分拣区
         * GoodsPosCode : 32F1CB27B289FCC7E0502D0AC8941FF0
         * GoodsPosName : A01-01-01
         * StockCheckTaskCode : BAJPT17121500003
         * StockCheckTaskCreateDate : 2017-12-15T16:20:25.8
         */

        private String GoodsAreaName;
        private String GoodsPosCode;
        private String GoodsPosName;
        private String StockCheckTaskCode;
        private String StockCheckTaskCreateDate;

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

        public String getStockCheckTaskCode() {
            return StockCheckTaskCode;
        }

        public void setStockCheckTaskCode(String StockCheckTaskCode) {
            this.StockCheckTaskCode = StockCheckTaskCode;
        }

        public String getStockCheckTaskCreateDate() {
            return StockCheckTaskCreateDate;
        }

        public void setStockCheckTaskCreateDate(String StockCheckTaskCreateDate) {
            this.StockCheckTaskCreateDate = StockCheckTaskCreateDate;
        }
    }
}
