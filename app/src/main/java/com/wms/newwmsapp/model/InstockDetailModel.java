package com.wms.newwmsapp.model;

import java.io.Serializable;

public class InstockDetailModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -813977666400857010L;
	public String CustomerCode;//货品编码
	public String GoodsName;//货品名称
	public int Num;//数量(最大上限数)
	public int Loc_Num=0;//数量(实收数量:本地生成)
	public String ProductId;//批次号
	public String BoxId;//箱号
	public String ProductionDate;//生产日期
	
	public String StandUnitName;//规格名称
	public String QualityCode;//品质code
	public String QualityName;//品质名称
	public String GoodsUnitCode;//单位
	public String UnitName;//单位名称
	
	public String Code;
	public String GoodsCode;
	public String InStockCheckCode;
	public String InStockInformCode;
	public Double InstockPrice = 0.0;
	public int IsNoOut;
	public String GoodsPosCode;
	public String CreateDate;
	public String CreateBy;
	public String UpdateDate;
	public String Common1;
	public String Common2;
	public String Common3;
	public String Common4;
	public String Common5;
	public String ReturnGoodsDetailCode;
	public String StockCode;
	public String CustGoodsCode;
	public String CustGoodsName;
	public Double Unit = 1.0;
	public Double StandGrossWeight = 0.0;
	public Double StandNetWeight = 0.0;
	public Double StandVolume = 0.0;
	public int ShelfLife;
	public String InStockTypeCode;
	public String InformDate;
	public String Sender;
	public String Transer;
	public int InStockStatusCode;
	public Double InStockNum = 0.0;
	public String GoodsCategoryCode;
	public String GoodsCategoryMidCode;
	public String GoodsCategoryTopCode;
	public int IsProductionDate;
	public String GoodsPosName;
	public String GoodsAreaCode;
	public String GoodsAreaName;
	public String GangwayNo;
	public String FloorNo;
	public String ColumnNo;
	public String LimitStockArea;
	public String LimitGoodsArea;
	
	public int getLoc_Num() {
		return Loc_Num;
	}
	public void setLoc_Num(int loc_Num) {
		Loc_Num = loc_Num;
	}
	public int getNum() {
		return Num;
	}
	public void setNum(int num) {
		Num = num;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getGoodsCode() {
		return GoodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		GoodsCode = goodsCode;
	}
	public String getInStockCheckCode() {
		return InStockCheckCode;
	}
	public void setInStockCheckCode(String inStockCheckCode) {
		InStockCheckCode = inStockCheckCode;
	}
	public String getInStockInformCode() {
		return InStockInformCode;
	}
	public void setInStockInformCode(String inStockInformCode) {
		InStockInformCode = inStockInformCode;
	}
	public String getGoodsUnitCode() {
		return GoodsUnitCode;
	}
	public void setGoodsUnitCode(String goodsUnitCode) {
		GoodsUnitCode = goodsUnitCode;
	}
	public Double getInstockPrice() {
		return InstockPrice;
	}
	public void setInstockPrice(Double instockPrice) {
		InstockPrice = instockPrice;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getBoxId() {
		return BoxId;
	}
	public void setBoxId(String boxId) {
		BoxId = boxId;
	}
	public String getProductionDate() {
		return ProductionDate;
	}
	public void setProductionDate(String productionDate) {
		ProductionDate = productionDate;
	}
	public String getQualityCode() {
		return QualityCode;
	}
	public void setQualityCode(String qualityCode) {
		QualityCode = qualityCode;
	}
	public int getIsNoOut() {
		return IsNoOut;
	}
	public void setIsNoOut(int isNoOut) {
		IsNoOut = isNoOut;
	}
	public String getGoodsPosCode() {
		return GoodsPosCode;
	}
	public void setGoodsPosCode(String goodsPosCode) {
		GoodsPosCode = goodsPosCode;
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
	public String getCommon1() {
		return Common1;
	}
	public void setCommon1(String common1) {
		Common1 = common1;
	}
	public String getCommon2() {
		return Common2;
	}
	public void setCommon2(String common2) {
		Common2 = common2;
	}
	public String getCommon3() {
		return Common3;
	}
	public void setCommon3(String common3) {
		Common3 = common3;
	}
	public String getCommon4() {
		return Common4;
	}
	public void setCommon4(String common4) {
		Common4 = common4;
	}
	public String getCommon5() {
		return Common5;
	}
	public void setCommon5(String common5) {
		Common5 = common5;
	}
	public String getReturnGoodsDetailCode() {
		return ReturnGoodsDetailCode;
	}
	public void setReturnGoodsDetailCode(String returnGoodsDetailCode) {
		ReturnGoodsDetailCode = returnGoodsDetailCode;
	}
	public String getStockCode() {
		return StockCode;
	}
	public void setStockCode(String stockCode) {
		StockCode = stockCode;
	}
	public String getCustGoodsCode() {
		return CustGoodsCode;
	}
	public void setCustGoodsCode(String custGoodsCode) {
		CustGoodsCode = custGoodsCode;
	}
	public String getCustGoodsName() {
		return CustGoodsName;
	}
	public void setCustGoodsName(String custGoodsName) {
		CustGoodsName = custGoodsName;
	}
	public String getCustomerCode() {
		return CustomerCode;
	}
	public void setCustomerCode(String customerCode) {
		CustomerCode = customerCode;
	}
	public String getGoodsName() {
		return GoodsName;
	}
	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}
	public Double getUnit() {
		return Unit;
	}
	public void setUnit(Double unit) {
		Unit = unit;
	}
	public String getStandUnitName() {
		return StandUnitName;
	}
	public void setStandUnitName(String standUnitName) {
		StandUnitName = standUnitName;
	}
	public Double getStandGrossWeight() {
		return StandGrossWeight;
	}
	public void setStandGrossWeight(Double standGrossWeight) {
		StandGrossWeight = standGrossWeight;
	}
	public Double getStandNetWeight() {
		return StandNetWeight;
	}
	public void setStandNetWeight(Double standNetWeight) {
		StandNetWeight = standNetWeight;
	}
	public Double getStandVolume() {
		return StandVolume;
	}
	public void setStandVolume(Double standVolume) {
		StandVolume = standVolume;
	}
	public int getShelfLife() {
		return ShelfLife;
	}
	public void setShelfLife(int shelfLife) {
		ShelfLife = shelfLife;
	}
	public String getQualityName() {
		return QualityName;
	}
	public void setQualityName(String qualityName) {
		QualityName = qualityName;
	}
	public String getUnitName() {
		return UnitName;
	}
	public void setUnitName(String unitName) {
		UnitName = unitName;
	}
	public String getInStockTypeCode() {
		return InStockTypeCode;
	}
	public void setInStockTypeCode(String inStockTypeCode) {
		InStockTypeCode = inStockTypeCode;
	}
	public String getInformDate() {
		return InformDate;
	}
	public void setInformDate(String informDate) {
		InformDate = informDate;
	}
	public String getSender() {
		return Sender;
	}
	public void setSender(String sender) {
		Sender = sender;
	}
	public String getTranser() {
		return Transer;
	}
	public void setTranser(String transer) {
		Transer = transer;
	}
	public int getInStockStatusCode() {
		return InStockStatusCode;
	}
	public void setInStockStatusCode(int inStockStatusCode) {
		InStockStatusCode = inStockStatusCode;
	}
	public Double getInStockNum() {
		return InStockNum;
	}
	public void setInStockNum(Double inStockNum) {
		InStockNum = inStockNum;
	}
	public String getGoodsCategoryCode() {
		return GoodsCategoryCode;
	}
	public void setGoodsCategoryCode(String goodsCategoryCode) {
		GoodsCategoryCode = goodsCategoryCode;
	}
	public String getGoodsCategoryMidCode() {
		return GoodsCategoryMidCode;
	}
	public void setGoodsCategoryMidCode(String goodsCategoryMidCode) {
		GoodsCategoryMidCode = goodsCategoryMidCode;
	}
	public String getGoodsCategoryTopCode() {
		return GoodsCategoryTopCode;
	}
	public void setGoodsCategoryTopCode(String goodsCategoryTopCode) {
		GoodsCategoryTopCode = goodsCategoryTopCode;
	}
	public int getIsProductionDate() {
		return IsProductionDate;
	}
	public void setIsProductionDate(int isProductionDate) {
		IsProductionDate = isProductionDate;
	}
	public String getGoodsPosName() {
		return GoodsPosName;
	}
	public void setGoodsPosName(String goodsPosName) {
		GoodsPosName = goodsPosName;
	}
	public String getGoodsAreaCode() {
		return GoodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		GoodsAreaCode = goodsAreaCode;
	}
	public String getGoodsAreaName() {
		return GoodsAreaName;
	}
	public void setGoodsAreaName(String goodsAreaName) {
		GoodsAreaName = goodsAreaName;
	}
	public String getGangwayNo() {
		return GangwayNo;
	}
	public void setGangwayNo(String gangwayNo) {
		GangwayNo = gangwayNo;
	}
	public String getFloorNo() {
		return FloorNo;
	}
	public void setFloorNo(String floorNo) {
		FloorNo = floorNo;
	}
	public String getColumnNo() {
		return ColumnNo;
	}
	public void setColumnNo(String columnNo) {
		ColumnNo = columnNo;
	}
	public String getLimitStockArea() {
		return LimitStockArea;
	}
	public void setLimitStockArea(String limitStockArea) {
		LimitStockArea = limitStockArea;
	}
	public String getLimitGoodsArea() {
		return LimitGoodsArea;
	}
	public void setLimitGoodsArea(String limitGoodsArea) {
		LimitGoodsArea = limitGoodsArea;
	}
}
