package com.wms.newwmsapp.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.PickUpConfirmDetailActivity;
import com.wms.newwmsapp.activity.UpDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.GoodsPosModel;
import com.wms.newwmsapp.model.PickUpConfirmDetailModel;

public class PickUpGoodsPosAdapter extends
		DataAdapter<PickUpConfirmDetailModel> {

	private String Code;

	public PickUpGoodsPosAdapter(Context context) {
		super(context);
	}

	public PickUpGoodsPosAdapter(Context context, boolean isCircle, String Code) {
		super(context, isCircle);

		this.Code = Code;
	}

	@Override
	public void renderData(final int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
		final PickUpConfirmDetailModel goodspos = getItemT(position);
		TextView GoodsPosName = vh.getView(TextView.class, R.id.GoodsPosName);
		TextView Loc_Num = vh.getView(TextView.class, R.id.Loc_Num);
		TextView delete = vh.getView(TextView.class, R.id.delete);

		GoodsPosName.setText(goodspos.getGoodsPosName());
		Loc_Num.setText("(" + goodspos.getNum() + ")" + goodspos.getLoc_Num());

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				map.put("chilren_position", position + "");
				map.put("Code", Code);
				Message msg = new Message();
				msg.what = 2;
				msg.obj = map;
				PickUpConfirmDetailActivity.myHandler.sendMessage(msg);
			}
		});

		// 起草状态(1.删除按钮显示 2.括号内显示Loc_Num )
		// 其他状态(1.删除按钮隐藏 2.括号内显示Num)
		delete.setVisibility(View.VISIBLE);

		// if(InStockPutawayStatusCode.equals("1")){
		// delete.setVisibility(View.VISIBLE);
		// Loc_Num.setText(goodspos.getLoc_Num()+"");
		// }else{
		// delete.setVisibility(View.INVISIBLE);
		// Loc_Num.setText(goodspos.getNum()+"");
		// }
	}

	@Override
	public int[] getFindViewByIDs() {
		return new int[] { R.id.GoodsPosName, R.id.Loc_Num, R.id.delete };
	}

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_pickup_goodspos_item);
	}
}
