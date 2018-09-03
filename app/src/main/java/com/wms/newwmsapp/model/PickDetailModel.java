package com.wms.newwmsapp.model;

import java.io.Serializable;

public class PickDetailModel implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public String GoodsCode;
		public String getGoodsCode() {
			return GoodsCode;
		}
		public void setGoodsCode(String goodsCode) {
			GoodsCode = goodsCode;
		}
		public String getProductId() {
			return ProductId;
		}
		public void setProductId(String productId) {
			ProductId = productId;
		}
		public String getGoodsPosCode() {
			return GoodsPosCode;
		}
		public void setGoodsPosCode(String goodsPosCode) {
			GoodsPosCode = goodsPosCode;
		}
		public String getGoodsName() {
			return GoodsName;
		}
		public void setGoodsName(String goodsName) {
			GoodsName = goodsName;
		}
		public String getGoodsCustomerCode() {
			return GoodsCustomerCode;
		}
		public void setGoodsCustomerCode(String goodsCustomerCode) {
			GoodsCustomerCode = goodsCustomerCode;
		}
		public String getGoodsColor() {
			return GoodsColor;
		}
		public void setGoodsColor(String goodsColor) {
			GoodsColor = goodsColor;
		}
		public String getGoodsBarCode() {
			return GoodsBarCode;
		}
		public void setGoodsBarCode(String goodsBarCode) {
			GoodsBarCode = goodsBarCode;
		}
		public String getGoodsModel() {
			return GoodsModel;
		}
		public void setGoodsModel(String goodsModel) {
			GoodsModel = goodsModel;
		}
		public String getGoodsPosName() {
			return GoodsPosName;
		}
		public void setGoodsPosName(String goodsPosName) {
			GoodsPosName = goodsPosName;
		}
		public String getNum() {
			return Num;
		}
		public void setNum(String num) {
			Num = num;
		}
		public String ProductId;
		public String GoodsPosCode;
		public String GoodsName;
		public String GoodsCustomerCode;
		public String GoodsColor;
		public String GoodsBarCode;
		public String GoodsModel;
		public String GoodsPosName;
		public String Num;
		public String getOutStockDeliveryCode() {
			return OutStockDeliveryCode;
		}
		public void setOutStockDeliveryCode(String outStockDeliveryCode) {
			OutStockDeliveryCode = outStockDeliveryCode;
		}
		public int getSortNo() {
			return SortNo;
		}
		public void setSortNo(int sortNo) {
			SortNo = sortNo;
		}
		public String OutStockDeliveryCode;
		public int SortNo;
	public boolean IsScan;
	public boolean isIsScan() {
		return IsScan;
	}
	public void setIsScan(boolean isScan) {
		IsScan = isScan;
	}
	
	public int getScanNum() {
		return ScanNum;
	}
	public void setScanNum(int scanNum) {
		ScanNum = scanNum;
	}
	public int ScanNum;

}
