package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.InstockDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.InstockModel;
import com.wms.newwmsapp.tool.JsonDateFormate;

public class InstockAdapter extends DataAdapter<InstockModel>{

	private Context context;
	
	public InstockAdapter(Context context) {
        super(context);
    }

    public InstockAdapter(Context context, boolean isCircle) {
        super(context, isCircle);
        this.context = context;
    }
    
    @Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
    	final InstockModel instock= getItemT(position);
    	LinearLayout instock_lin = vh.getView(LinearLayout.class, R.id.instock_lin);
        TextView Code = vh.getView(TextView.class, R.id.Code);
        TextView PurchaseNO = vh.getView(TextView.class, R.id.PurchaseNO);
        TextView CustGoodsName = vh.getView(TextView.class, R.id.CustGoodsName);
        TextView InformDate = vh.getView(TextView.class, R.id.InformDate);
        
        Code.setText(instock.getCode());
        PurchaseNO.setText(instock.getPurchaseNO());
        CustGoodsName.setText(instock.getCustGoodsName());
        InformDate.setText(JsonDateFormate.dataFormate(instock.getInformDate()));
        instock_lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, InstockDetailActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				intent.putExtra("instock", instock);
				context.startActivity(intent);
			}
		});
	}

    @Override
    public int[] getFindViewByIDs() {
    	return new int[]{R.id.instock_lin ,R.id.Code, R.id.PurchaseNO, R.id.CustGoodsName, R.id.InformDate};
    }

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_instock_item);
	}
}
