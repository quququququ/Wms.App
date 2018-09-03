package com.wms.newwmsapp.model;

import java.io.Serializable;

/**
 * Created by qupengcheng on 2018/1/12.
 */

public class StockTaskDetailModel implements Serializable{
    public String StockCheckTaskCode;

    public String GoodsCode;

    public String GoodsName ;
    public String QualityCode ;
    public String ProductId ;
    public String ProductionDate ;
    public String CustGoodsName;
    public String QualityName ;
    public String Num;
    public int OldNum ;
    public String GoodsUnitCode  ;

    public String getStockCheckTaskCode() {
        return StockCheckTaskCode;
    }

    public void setStockCheckTaskCode(String stockCheckTaskCode) {
        StockCheckTaskCode = stockCheckTaskCode;
    }

    public String getGoodsCode() {
        return GoodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        GoodsCode = goodsCode;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public String getQualityCode() {
        return QualityCode;
    }

    public void setQualityCode(String qualityCode) {
        QualityCode = qualityCode;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductionDate() {
        return ProductionDate;
    }

    public void setProductionDate(String productionDate) {
        ProductionDate = productionDate;
    }

    public String getCustGoodsName() {
        return CustGoodsName;
    }

    public void setCustGoodsName(String custGoodsName) {
        CustGoodsName = custGoodsName;
    }

    public String getQualityName() {
        return QualityName;
    }

    public void setQualityName(String qualityName) {
        QualityName = qualityName;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public int getOldNum() {
        return OldNum;
    }

    public void setOldNum(int oldNum) {
        OldNum = oldNum;
    }

    public String getGoodsunitcode() {
        return GoodsUnitCode ;
    }

    public void setGoodsunitcode(String GoodsUnitCode ) {
        this.GoodsUnitCode  = GoodsUnitCode ;
    }
}
