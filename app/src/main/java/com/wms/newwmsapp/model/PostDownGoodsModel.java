package com.wms.newwmsapp.model;

import java.io.Serializable;

/**
 * Created by cheng on 2018/7/9.
 */

public class PostDownGoodsModel implements Serializable {
    public String GoodsPosCode;
    public String GoodsCode;
    public String GoodsUnitCode;
    public String StockTransferGetOutNum;
    public String ProductId;
    public String BoxId;
    public String CustGoodsCode;
    public String QualityCode;
    public String ProductionDate;

    public void setProductionDate(String productionDate) {
        ProductionDate = productionDate;
    }

    public void setGoodsPosCode(String goodsPosCode) {
        GoodsPosCode = goodsPosCode;
    }

    public void setGoodsCode(String goodsCode) {
        GoodsCode = goodsCode;
    }

    public void setGoodsUnitCode(String goodsUnitCode) {
        GoodsUnitCode = goodsUnitCode;
    }

    public void setStockTransferGetOutNum(String stockTransferGetOutNum) {
        StockTransferGetOutNum = stockTransferGetOutNum;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public void setBoxId(String boxId) {
        BoxId = boxId;
    }

    public void setCustGoodsCode(String custGoodsCode) {
        CustGoodsCode = custGoodsCode;
    }

    public void setQualityCode(String qualityCode) {
        QualityCode = qualityCode;
    }

    @Override
    public String toString() {
        return "{" +
                "ProductionDate=" + ProductionDate +
                "GoodsPosCode=" + GoodsPosCode +
                ", GoodsCode=" + GoodsCode +
                ", GoodsUnitCode=" + GoodsUnitCode +
                ", StockTransferGetOutNum=" + StockTransferGetOutNum +
                ", ProductId=" + ProductId +
                ", BoxId=" + BoxId +
                ", CustGoodsCode=" + CustGoodsCode +
                ", QualityCode=" + QualityCode +
                '}';
    }
}
