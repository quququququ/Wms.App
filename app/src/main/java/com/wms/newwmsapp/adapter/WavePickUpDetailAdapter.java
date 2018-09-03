
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

import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;

import com.wms.newwmsapp.model.PickDetailModel;
import com.wms.newwmsapp.model.WavePickupDetailModel;
import com.wms.newwmsapp.model.WavePickupModel;
import com.wms.newwmsapp.tool.JsonDateFormate;


public class WavePickUpDetailAdapter extends DataAdapter<PickDetailModel> {

	private Context context;

	public WavePickUpDetailAdapter(Context context) {
		super(context);
	}

	public WavePickUpDetailAdapter(Context context, boolean isCircle) {
		super(context, isCircle);
		this.context = context;
	}

	@Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
		final PickDetailModel pick = getItemT(position);
		LinearLayout pick_lin = vh.getView(LinearLayout.class, R.id.batch_pickup_detail);
		TextView  GoodsCode= vh.getView(TextView.class, R.id.GoodsCode);
		TextView  GoodsBarCode= vh.getView(TextView.class, R.id.GoodsBarCode);
		TextView GoodsCustomerCode = vh.getView(TextView.class, R.id.GoodsCustomerCode);
		TextView GoodsModel= vh.getView(TextView.class, R.id.GoodsModel);
		TextView GoodsColor = vh.getView(TextView.class, R.id.GoodsColor);
		GoodsCode.setVisibility(0);
		
		GoodsBarCode.setText("条码: " + pick.getGoodsBarCode());
		GoodsCustomerCode.setText("商家编码: "+pick.getGoodsCustomerCode());
		GoodsModel.setText("规格: "+pick.getGoodsModel());
		GoodsColor.setText("颜色: "+pick.getGoodsColor());
	
	}

	@Override
	public int[] getFindViewByIDs() {
		return new int[] { R.id.batch_pickup_detail, R.id.GoodsCode,R.id.GoodsBarCode,
				R.id.GoodsCustomerCode, R.id.GoodsModel ,R.id.GoodsColor };
	}

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_batch_pickup_detail);
	}
}
