package com.wms.newwmsapp.model;

import java.util.List;

public class StockInSearchModel {

	public String RecordCount;
	public String getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(String recordCount) {
		RecordCount = recordCount;
	}

	public String getGoodsTypeSum() {
		return GoodsTypeSum;
	}

	public void setGoodsTypeSum(String goodsTypeSum) {
		GoodsTypeSum = goodsTypeSum;
	}

	public String getGoodsSum() {
		return GoodsSum;
	}

	public void setGoodsSum(String goodsSum) {
		GoodsSum = goodsSum;
	}

	public List<StockInSearchListModel> getList() {
		return list;
	}

	public void setList(List<StockInSearchListModel> list) {
		this.list = list;
	}

	public String GoodsTypeSum;
	public String GoodsSum;

	public List<StockInSearchListModel> list;
}
