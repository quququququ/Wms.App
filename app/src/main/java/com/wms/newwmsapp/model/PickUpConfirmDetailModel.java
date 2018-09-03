package com.wms.newwmsapp.model;

import java.util.List;

public class PickUpConfirmDetailModel {
	
	public boolean IsFinish;
	
	public boolean isIsFinish() {
		return IsFinish;
	}

	public void setIsFinish(boolean isFinish) {
		IsFinish = isFinish;
	}

	public int Loc_Num;
	
	public int getLoc_Num() {
		return Loc_Num;
	}

	public void setLoc_Num(int loc_Num) {
		Loc_Num = loc_Num;
	}

	public String Code ;

    public String OutStockInformCode ;

    public String OutStockPickConfirmCode ;

    public String GoodsCode ;

    public int Num ;

    public String GoodsUnitCode ;

    public Double OutStockPrice ;

    public String QualityCode ;

    public String ProductId ;

    public String BoxId ;

    public String ProductionDate ;

    public String GoodsPosCode ;
   
    public String CreateDate ;

    public String CreateBy ;

    public String UpdateDate ;

    public String UpdateBy ;

    public String InStockGoodsCode ;

    /// <summary>
    /// 增加属性
    /// </summary>
    public String Common1 ;
    public String Common2 ;
    public String Common3 ;
    public String Common4 ;
    public String Common5 ;
    public int IsGift ;
    
    public String CustomerCode ;

    public String GoodsName ;

    public String StandUnitName ;

    public Double StandGrossWeight ;

    public Double StandNetWeight ;

    public Double StandVolume ;

    public int ShelfLife ;

    public String CustGoodsName ;

    public Double Unit ;

    public String BarCode ;
    public String UnitName ;

    public String GoodsPosName ;
    
    public List<PickUpConfirmDetailModel> getList() {
		return list;
	}

	public void setList(List<PickUpConfirmDetailModel> list) {
		this.list = list;
	}

	public List<PickUpConfirmDetailModel> list;
	
	 public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getOutStockInformCode() {
		return OutStockInformCode;
	}

	public void setOutStockInformCode(String outStockInformCode) {
		OutStockInformCode = outStockInformCode;
	}

	public String getOutStockPickConfirmCode() {
		return OutStockPickConfirmCode;
	}

	public void setOutStockPickConfirmCode(String outStockPickConfirmCode) {
		OutStockPickConfirmCode = outStockPickConfirmCode;
	}

	public String getGoodsCode() {
		return GoodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		GoodsCode = goodsCode;
	}

	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
	}

	public String getGoodsUnitCode() {
		return GoodsUnitCode;
	}

	public void setGoodsUnitCode(String goodsUnitCode) {
		GoodsUnitCode = goodsUnitCode;
	}

	public Double getOutStockPrice() {
		return OutStockPrice;
	}

	public void setOutStockPrice(Double outStockPrice) {
		OutStockPrice = outStockPrice;
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

	public String getUpdateBy() {
		return UpdateBy;
	}

	public void setUpdateBy(String updateBy) {
		UpdateBy = updateBy;
	}

	public String getInStockGoodsCode() {
		return InStockGoodsCode;
	}

	public void setInStockGoodsCode(String inStockGoodsCode) {
		InStockGoodsCode = inStockGoodsCode;
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

	public int getIsGift() {
		return IsGift;
	}

	public void setIsGift(int isGift) {
		IsGift = isGift;
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

	public String getCustGoodsName() {
		return CustGoodsName;
	}

	public void setCustGoodsName(String custGoodsName) {
		CustGoodsName = custGoodsName;
	}

	public Double getUnit() {
		return Unit;
	}

	public void setUnit(Double unit) {
		Unit = unit;
	}

	public String getBarCode() {
		return BarCode;
	}

	public void setBarCode(String barCode) {
		BarCode = barCode;
	}

	public String getUnitName() {
		return UnitName;
	}

	public void setUnitName(String unitName) {
		UnitName = unitName;
	}

	public String getGoodsPosName() {
		return GoodsPosName;
	}

	public void setGoodsPosName(String goodsPosName) {
		GoodsPosName = goodsPosName;
	}

	
     
}
