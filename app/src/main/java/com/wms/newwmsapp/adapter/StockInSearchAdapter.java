package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.PickUpConfirmDetailActivity;
import com.wms.newwmsapp.activity.UpDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.PickUpConfirmModel;
import com.wms.newwmsapp.model.StockInSearchListModel;
import com.wms.newwmsapp.model.StockInSearchModel;
import com.wms.newwmsapp.tool.JsonDateFormate;

public class StockInSearchAdapter extends DataAdapter<StockInSearchListModel> {

	private Context context;

	public StockInSearchAdapter(Context context) {
		super(context);
	}

	public StockInSearchAdapter(Context context, boolean isCircle) {
		super(context, isCircle);
		this.context = context;
	}

	@Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
		final StockInSearchListModel pick = getItemT(position);
		LinearLayout pick_lin = vh.getView(LinearLayout.class, R.id.stockinserch_lin);
		TextView CustomerCode= vh.getView(TextView.class, R.id.CustomerCode);
		TextView GoodsName= vh.getView(TextView.class, R.id.GoodsName);
		TextView BarCode = vh.getView(TextView.class, R.id.BarCode);
		TextView Num = vh.getView(TextView.class,R.id.Num);
		TextView OffNum= vh.getView(TextView.class, R.id.OffNum);
		TextView IsNoOut = vh.getView(TextView.class, R.id.IsNoOut);
//		TextView Information = vh.getView(TextView.class, R.id.Information);

		CustomerCode.setText("编码： " + pick.getCustomerCode());
		GoodsName.setText("名称: " + pick.getGoodsName());
		BarCode.setText("条码: " + pick.getBarCode());
		Num.setText("数量: " +pick.getNum());
		OffNum.setText("锁定数量: "+ pick.getOffNum());
		if(pick.getIsNoOut()=="0")
		{
			IsNoOut.setText("禁止出库: 是" );
			
		}else
		{
			IsNoOut.setText("禁止出库: 否" );
		}
//		Information.setText(" 汇总信息         商品种类:"+pick.getGoodsTypeSum()+"   商品总数:"+pick.getGoodsSum());
	
	}

	@Override
	public int[] getFindViewByIDs() {
		return new int[] { R.id.stockinserch_lin, R.id.CustomerCode,
				R.id.GoodsName, R.id.BarCode, R.id.Num,
				R.id.OffNum, R.id.IsNoOut};
	}

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_stockinsearch);
	}
}
