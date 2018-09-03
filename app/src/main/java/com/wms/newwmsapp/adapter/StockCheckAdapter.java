package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.OutStockConfirmDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.OutStockConfirmModel;
import com.wms.newwmsapp.model.StockCheckDetailModel;
import com.wms.newwmsapp.tool.JsonDateFormate;

public class StockCheckAdapter  extends DataAdapter<StockCheckDetailModel>  {

	private Context context;

	public StockCheckAdapter(Context context) {
		super(context);
	}

	public StockCheckAdapter(Context context, boolean isCircle) {
		super(context, isCircle);
		this.context = context;
	}

	@Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
		final StockCheckDetailModel outStock = getItemT(position);
		LinearLayout outStock_lin = vh.getView(LinearLayout.class, R.id.stockcheck_lin);
		TextView Code = vh.getView(TextView.class, R.id.Code);
		TextView PurchaseNO = vh.getView(TextView.class, R.id.PurchaseNO);
		TextView CustGoodsName = vh.getView(TextView.class, R.id.CustGoodsName);
		TextView OutStockTypeName = vh.getView(TextView.class,R.id.OutStockTypeName);
		TextView OutStockDate = vh.getView(TextView.class, R.id.OutStockDate);
		TextView Remark = vh.getView(TextView.class, R.id.Remark);

		Code.setText("商家名称: " + outStock.getCustGoodsName() );
		PurchaseNO.setText("货品条码: " + outStock.getBarCode());
		CustGoodsName.setText("货品名称: " + outStock.getGoodsName() );
		OutStockTypeName.setText("数量: " + outStock .getNum());
		OutStockDate.setText("规格: "+ outStock.getModel());
		Remark.setText("颜色: " + outStock .getColor());
	/*	outStock_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, OutStockConfirmDetailActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("OutStock", outStock );
				context.startActivity(intent);
			}
		});*/
	}

	@Override
	public int[] getFindViewByIDs() {
		return new int[] { R.id.outstockConfirm_lin, R.id.Code,
				R.id.PurchaseNO, R.id.CustGoodsName, R.id.OutStockTypeName,
				R.id.OutStockDate, R.id.Remark };
	}

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_stockcheck_lin);
	}
	
}