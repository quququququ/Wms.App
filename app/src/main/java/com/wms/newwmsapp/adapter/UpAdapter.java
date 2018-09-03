package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.UpDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.UpModel;
import com.wms.newwmsapp.tool.JsonDateFormate;

public class UpAdapter extends DataAdapter<UpModel>{

	private Context context;
	
	public UpAdapter(Context context) {
        super(context);
    }

    public UpAdapter(Context context, boolean isCircle) {
        super(context, isCircle);
        this.context = context;
    }
    
    @Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
    	final UpModel up= getItemT(position);
    	LinearLayout up_lin = vh.getView(LinearLayout.class, R.id.up_lin);
        TextView Code = vh.getView(TextView.class, R.id.Code);
        TextView InStockPutawayStatusName = vh.getView(TextView.class, R.id.InStockPutawayStatusName);
        TextView PutawayDate = vh.getView(TextView.class, R.id.PutawayDate);
        TextView Remark = vh.getView(TextView.class, R.id.Remark);
        TextView CreateBy = vh.getView(TextView.class, R.id.CreateBy);
        TextView CreateDate = vh.getView(TextView.class, R.id.CreateDate);
        
        Code.setText("上架单号: "+up.getCode());
        InStockPutawayStatusName.setText("上架状态: "+up.getInStockPutawayStatusName());
        PutawayDate.setText("上架时间: "+JsonDateFormate.dataFormate(up.getPutawayDate()));
        Remark.setText("备注: "+up.getRemark());
        CreateBy.setText("创建人: "+up.getCreateBy());
        CreateDate.setText("创建时间: "+JsonDateFormate.dataFormate(up.getCreateDate()));
        up_lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, UpDetailActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				intent.putExtra("up", up);
				context.startActivity(intent);
			}
		});
	}

    @Override
    public int[] getFindViewByIDs() {
    	return new int[]{R.id.up_lin, R.id.Code, R.id.InStockPutawayStatusName, R.id.PutawayDate, R.id.Remark, R.id.CreateBy, R.id.CreateDate};
    }

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_up_item);
	}
}
