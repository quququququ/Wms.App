package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.InstockDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.InstockDetailModel;

public class InstockDetailAdapter extends DataAdapter<InstockDetailModel> {

	public InstockDetailAdapter(Context context) {
		super(context);
	}

	public InstockDetailAdapter(Context context, boolean isCircle) {
		super(context, isCircle);
	}

	@Override
	public void renderData(final int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
		final InstockDetailModel instockDetail = getItemT(position);
		LinearLayout lin = vh.getView(LinearLayout.class, R.id.lin);
		TextView CustomerCode = vh.getView(TextView.class, R.id.CustomerCode);
		TextView Loc_Num = vh.getView(TextView.class, R.id.Loc_Num);
		TextView ProductId = vh.getView(TextView.class, R.id.ProductId);
		TextView GoodsName = vh.getView(TextView.class, R.id.GoodsName);
		TextView StandUnitName = vh.getView(TextView.class, R.id.StandUnitName);
		TextView QualityName = vh.getView(TextView.class, R.id.QualityName);
		TextView UnitName = vh.getView(TextView.class, R.id.UnitName);
		Button delete = vh.getView(Button.class, R.id.delete);

		CustomerCode.setText(instockDetail.getCustomerCode());
		Loc_Num.setText(instockDetail.getLoc_Num()+"");
		ProductId.setText(instockDetail.getTranser());
		GoodsName.setText(instockDetail.getGoodsName());
		StandUnitName.setText(instockDetail.getStandUnitName());
		QualityName.setText(instockDetail.getQualityName());
		UnitName.setText(instockDetail.getUnitName());
		lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 1;
				msg.obj = position;
				InstockDetailActivity.myHandler.sendMessage(msg);
			}
		});
		
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 2;
				msg.obj = instockDetail;
				InstockDetailActivity.myHandler.sendMessage(msg);
			}
		});
	}

	@Override
	public int[] getFindViewByIDs() {
		return new int[] {R.id.lin ,R.id.CustomerCode, R.id.Loc_Num, R.id.ProductId,
				R.id.GoodsName, R.id.StandUnitName, R.id.QualityName, R.id.UnitName, R.id.delete};
	}

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_instockdetail_item);
	}
}
