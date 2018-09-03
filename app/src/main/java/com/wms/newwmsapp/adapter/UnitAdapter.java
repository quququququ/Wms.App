package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.UnitModel;

public class UnitAdapter extends DataAdapter<UnitModel>{

	public UnitAdapter(Context context) {
        super(context);
    }

    public UnitAdapter(Context context, boolean isCircle) {
        super(context, isCircle);
    }
    
    @Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
    	UnitModel unit= getItemT(position);
        TextView UnitName = vh.getView(TextView.class, R.id.UnitName);
        
        UnitName.setText(unit.getUnitName());
	}

    @Override
    public int[] getFindViewByIDs() {
    	return new int[]{R.id.UnitName};
    }

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_unit_item);
	}
}
