package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.CustGoodModel;

public class PickUpCustGoodsAdapter extends DataAdapter<CustGoodModel> {
	public PickUpCustGoodsAdapter(Context context) {
        super(context);
    }

    public PickUpCustGoodsAdapter(Context context, boolean isCircle) {
        super(context, isCircle);
    }
    
    @Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
    	CustGoodModel custgood= getItemT(position);
        TextView custgoodname = vh.getView(TextView.class, R.id.custgoodname);

        custgoodname.setText(custgood.getCustGoodsName());
	}

    @Override
    public int[] getFindViewByIDs() {
    	return new int[]{R.id.custgoodname};
    }

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_pickup_custgoods_item);
	}
}
