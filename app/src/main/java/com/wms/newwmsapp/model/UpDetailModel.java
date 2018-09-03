package com.wms.newwmsapp.model;

import java.io.Serializable;
import java.util.List;

public class UpDetailModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6542879292863114416L;
	public String CustomerCode;
	public String GoodsName;
	public String Code;//货品主键
	public int Num;
	public String ProductionDate;
	public String BoxId;//箱号
	public String ProductId;//批次id号
	public String ColumnNo;//
	public String Common2;//备注2
	public String Common3;//备注3
	public String Common4;//备注4
	public String Common5;//备注5
	public String ContianNum;//
	public String CreateBy;//创建者
	public String CreateDate;//创建时间
	public String CustGoodsCode;//货主
	public String GoodsPosCode;
	public String GoodsPosName;
	public String FloorNo;//
	public String GangwayNo;//
	public String GoodsCode;
	public String GoodsUnitCode;//单位
	public String InStockCheckDetailCode;//入库验收明细单号
	public String InStockInformCode;//入库通知单号
	public String InStockNum;//入库数量
	public String InStockPutawayCode;//上架单号
	public String InstockDate;//入库时间
	public String PurchaseNo;//采购单号
	public String StockCode;//仓库号
	public String TotalNum;//入库验收单总数量
	public String Unit;//单位换算
	public String UnitName;//单位名称
	public String UpdateBy;//修改人
	public String UpdateDate;//修改时间
	public String InStockPrice;//单价
	public String Shelflife;//保质期
	public String QualityCode;//品质Code
	public String QualityName;//品质Name
	public String IsNoOut;//是否禁止出库
	public String InStockCheckCode;//入库确认单号
	public String StandGrossWeight;//毛重
	public String StandNetWeight;//净重
	public String StandVolume;//总体积
	public String Common1;//备注1
	public List<GoodsPosModel> list;
	public boolean IsFinish;

	public boolean isIsFinish() {
		return IsFinish;
	}
	public void setIsFinish(boolean isFinish) {
		IsFinish = isFinish;
	}
	public String getGoodsPosCode() {
		return GoodsPosCode;
	}
	public void setGoodsPosCode(String goodsPosCode) {
		GoodsPosCode = goodsPosCode;
	}
	public String getGoodsPosName() {
		return GoodsPosName;
	}
	public void setGoodsPosName(String goodsPosName) {
		GoodsPosName = goodsPosName;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getBoxId() {
		return BoxId;
	}
	public void setBoxId(String boxId) {
		BoxId = boxId;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getColumnNo() {
		return ColumnNo;
	}
	public void setColumnNo(String columnNo) {
		ColumnNo = columnNo;
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
	public String getContianNum() {
		return ContianNum;
	}
	public void setContianNum(String contianNum) {
		ContianNum = contianNum;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public String getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}
	public String getCustGoodsCode() {
		return CustGoodsCode;
	}
	public void setCustGoodsCode(String custGoodsCode) {
		CustGoodsCode = custGoodsCode;
	}
	public String getFloorNo() {
		return FloorNo;
	}
	public void setFloorNo(String floorNo) {
		FloorNo = floorNo;
	}
	public String getGangwayNo() {
		return GangwayNo;
	}
	public void setGangwayNo(String gangwayNo) {
		GangwayNo = gangwayNo;
	}
	public String getGoodsCode() {
		return GoodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		GoodsCode = goodsCode;
	}
	public String getGoodsUnitCode() {
		return GoodsUnitCode;
	}
	public void setGoodsUnitCode(String goodsUnitCode) {
		GoodsUnitCode = goodsUnitCode;
	}
	public String getInStockCheckDetailCode() {
		return InStockCheckDetailCode;
	}
	public void setInStockCheckDetailCode(String inStockCheckDetailCode) {
		InStockCheckDetailCode = inStockCheckDetailCode;
	}
	public String getInStockInformCode() {
		return InStockInformCode;
	}
	public void setInStockInformCode(String inStockInformCode) {
		InStockInformCode = inStockInformCode;
	}
	public String getInStockNum() {
		return InStockNum;
	}
	public void setInStockNum(String inStockNum) {
		InStockNum = inStockNum;
	}
	public String getInStockPutawayCode() {
		return InStockPutawayCode;
	}
	public void setInStockPutawayCode(String inStockPutawayCode) {
		InStockPutawayCode = inStockPutawayCode;
	}
	public String getInstockDate() {
		return InstockDate;
	}
	public void setInstockDate(String instockDate) {
		InstockDate = instockDate;
	}
	public String getPurchaseNo() {
		return PurchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		PurchaseNo = purchaseNo;
	}
	public String getStockCode() {
		return StockCode;
	}
	public void setStockCode(String stockCode) {
		StockCode = stockCode;
	}
	public String getTotalNum() {
		return TotalNum;
	}
	public void setTotalNum(String totalNum) {
		TotalNum = totalNum;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getUnitName() {
		return UnitName;
	}
	public void setUnitName(String unitName) {
		UnitName = unitName;
	}
	public String getUpdateBy() {
		return UpdateBy;
	}
	public void setUpdateBy(String updateBy) {
		UpdateBy = updateBy;
	}
	public String getUpdateDate() {
		return UpdateDate;
	}
	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}
	public String getInStockPrice() {
		return InStockPrice;
	}
	public void setInStockPrice(String inStockPrice) {
		InStockPrice = inStockPrice;
	}
	public String getShelflife() {
		return Shelflife;
	}
	public void setShelflife(String shelflife) {
		Shelflife = shelflife;
	}
	public String getQualityCode() {
		return QualityCode;
	}
	public void setQualityCode(String qualityCode) {
		QualityCode = qualityCode;
	}
	public String getQualityName() {
		return QualityName;
	}
	public void setQualityName(String qualityName) {
		QualityName = qualityName;
	}
	public String getIsNoOut() {
		return IsNoOut;
	}
	public void setIsNoOut(String isNoOut) {
		IsNoOut = isNoOut;
	}
	public String getInStockCheckCode() {
		return InStockCheckCode;
	}
	public void setInStockCheckCode(String inStockCheckCode) {
		InStockCheckCode = inStockCheckCode;
	}
	public String getStandGrossWeight() {
		return StandGrossWeight;
	}
	public void setStandGrossWeight(String standGrossWeight) {
		StandGrossWeight = standGrossWeight;
	}
	public String getStandNetWeight() {
		return StandNetWeight;
	}
	public void setStandNetWeight(String standNetWeight) {
		StandNetWeight = standNetWeight;
	}
	public String getStandVolume() {
		return StandVolume;
	}
	public void setStandVolume(String standVolume) {
		StandVolume = standVolume;
	}
	public String getCommon1() {
		return Common1;
	}
	public void setCommon1(String common1) {
		Common1 = common1;
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
	public int getNum() {
		return Num;
	}
	public void setNum(int num) {
		Num = num;
	}
	public String getProductionDate() {
		return ProductionDate;
	}
	public void setProductionDate(String productionDate) {
		ProductionDate = productionDate;
	}
	public List<GoodsPosModel> getList() {
		return list;
	}
	public void setList(List<GoodsPosModel> list) {
		this.list = list;
	}
}
