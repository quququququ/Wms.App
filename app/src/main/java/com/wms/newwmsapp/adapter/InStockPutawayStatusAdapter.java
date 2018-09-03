package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.InStockPutawayStatus;

public class InStockPutawayStatusAdapter extends DataAdapter<InStockPutawayStatus>{

	public InStockPutawayStatusAdapter(Context context) {
        super(context);
    }

    public InStockPutawayStatusAdapter(Context context, boolean isCircle) {
        super(context, isCircle);
    }
    
    @Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
    	InStockPutawayStatus status= getItemT(position);
        TextView InStockPutawayStatusName = vh.getView(TextView.class, R.id.InStockPutawayStatusName);

        InStockPutawayStatusName.setText(status.getInStockPutawayStatusName());
	}

    @Override
    public int[] getFindViewByIDs() {
    	return new int[]{R.id.InStockPutawayStatusName};
    }

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_instockputawaystatus_item);
	}
}
