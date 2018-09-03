package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.InstockDetailActivity;
import com.wms.newwmsapp.activity.OutStockConfirmDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.InstockDetailModel;
import com.wms.newwmsapp.model.OutStockConfirmDetailModel;

public class OutStockConfirmDetailAdapter extends DataAdapter<OutStockConfirmDetailModel> {

	public OutStockConfirmDetailAdapter(Context context) {
		super(context);
	}

	public OutStockConfirmDetailAdapter(Context context, boolean isCircle) {
		super(context, isCircle);
	}

	@Override
	public void renderData(final int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
		final OutStockConfirmDetailModel detail = getItemT(position);
		LinearLayout lin = vh.getView(LinearLayout.class, R.id.outstockconfirmDetail_lin);
		TextView CustomerCode = vh.getView(TextView.class, R.id.CustomerCode);
		TextView GoodsName = vh.getView(TextView.class, R.id.GoodsName);
		TextView Num = vh.getView(TextView.class, R.id.Num);
		TextView GoodsPosName = vh.getView(TextView.class, R.id.GoodsPosName);
		TextView ProductId = vh.getView(TextView.class, R.id.ProductId);
		TextView BoxId = vh.getView(TextView.class, R.id.BoxId);
		TextView UnitName = vh.getView(TextView.class, R.id.UnitName);
		Button delete = vh.getView(Button.class, R.id.delete);

		CustomerCode.setText(detail.getCustomerCode());
		GoodsName.setText(detail.getGoodsName());
		Num.setText(detail.getNum()+"");
		GoodsPosName.setText(detail.getGoodsPosName());
		ProductId.setText(detail.getProductId());
		BoxId.setText(detail.getBoxId());
		UnitName.setText(detail.getUnitName());
		lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 1;
				msg.obj = position;
			    OutStockConfirmDetailActivity.myHandler.sendMessage(msg);
			}
		});
		
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 2;
				msg.obj = detail;
			    OutStockConfirmDetailActivity.myHandler.sendMessage(msg);
			}
		});
	}

	@Override
	public int[] getFindViewByIDs() {
		return new int[] {R.id.outstockconfirmDetail_lin ,R.id.CustomerCode, R.id.GoodsName, R.id.Num,
				R.id.GoodsPosName, R.id.ProductId, R.id.BoxId, R.id.UnitName, R.id.delete};
	}

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_outstockconfirmdetail_item);
	}
}
