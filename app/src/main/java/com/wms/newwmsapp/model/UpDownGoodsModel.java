package com.wms.newwmsapp.model;

import java.io.Serializable;

/**
 * Created by cheng on 2018/7/19.
 */

public class UpDownGoodsModel implements Serializable {


    public String CustGoodsCode;
    public String GoodsPosCode;
    public String GoodsCode;
    public String GoodsUnitCode;
    public String StockTransferGetOutNum;
    public String ProductId;
    public String QualityCode;
    public String ProductionDate;
    public String BoxId;

    public void setCustGoodsCode(String custGoodsCode) {
        CustGoodsCode = custGoodsCode;
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

    public void setQualityCode(String qualityCode) {
        QualityCode = qualityCode;
    }

    public void setProductionDate(String productionDate) {
        ProductionDate = productionDate;
    }

    public void setBoxId(String boxId) {
        BoxId = boxId;
    }

    @Override
    public String toString() {
        return "{" +
                "CustGoodsCode=" + CustGoodsCode +
                ", GoodsPosCode=" + GoodsPosCode +
                ", GoodsCode=" + GoodsCode +
                ", GoodsUnitCode=" + GoodsUnitCode +
                ", StockTransferGetOutNum=" + StockTransferGetOutNum +
                ", ProductId=" + ProductId +
                ", QualityCode=" + QualityCode +
                ", ProductionDate=" + ProductionDate +
                ", BoxId=" + BoxId +
                '}';
    }

}
