package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.QualityMode;

public class QualityAdapter extends DataAdapter<QualityMode>{

	public QualityAdapter(Context context) {
        super(context);
    }

    public QualityAdapter(Context context, boolean isCircle) {
        super(context, isCircle);
    }
    
    @Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
    	QualityMode quality= getItemT(position);
        TextView Name = vh.getView(TextView.class, R.id.Name);
        
        Name.setText(quality.getName());
	}

    @Override
    public int[] getFindViewByIDs() {
    	return new int[]{R.id.Name};
    }

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_quality_item);
	}
}
