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
import com.wms.newwmsapp.tool.JsonDateFormate;

public class PickUpConkfirmAdapter extends DataAdapter<PickUpConfirmModel> {

	private Context context;

	public PickUpConkfirmAdapter(Context context) {
		super(context);
	}

	public PickUpConkfirmAdapter(Context context, boolean isCircle) {
		super(context, isCircle);
		this.context = context;
	}

	@Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
		final PickUpConfirmModel pick = getItemT(position);
		LinearLayout pick_lin = vh.getView(LinearLayout.class, R.id.pickupconfirm_lin);
		TextView Code = vh.getView(TextView.class, R.id.Code);
		TextView PurchaseNO = vh.getView(TextView.class, R.id.PurchaseNO);
		TextView CustGoodsName = vh.getView(TextView.class, R.id.CustGoodsName);
		TextView OutStockTypeName = vh.getView(TextView.class,R.id.OutStockTypeName);
		TextView OutStockDate = vh.getView(TextView.class, R.id.OutStockDate);
		TextView Remark = vh.getView(TextView.class, R.id.Remark);

		Code.setText("分拣确认单号: " + pick.getCode());
		PurchaseNO.setText("采购单号: " + pick.getPurchaseNO());
		CustGoodsName.setText("货主: " + pick.getCustGoodsName());
		OutStockTypeName.setText("出库类型: " + pick.getOutStockTypeName());
		OutStockDate.setText("出库日期: "+ JsonDateFormate.dataFormate(pick.getOutStockDate()));
		Remark.setText("备注: " + pick.getRemark());
		pick_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, PickUpConfirmDetailActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("Pick", pick);
				context.startActivity(intent);
			}
		});
	}

	@Override
	public int[] getFindViewByIDs() {
		return new int[] { R.id.pickupconfirm_lin, R.id.Code,
				R.id.PurchaseNO, R.id.CustGoodsName, R.id.OutStockTypeName,
				R.id.OutStockDate, R.id.Remark };
	}

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_pickupconfirm);
	}
}
