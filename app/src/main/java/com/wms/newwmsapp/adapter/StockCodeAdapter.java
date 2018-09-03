package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.StockModel;

public class StockCodeAdapter extends DataAdapter<StockModel>{

	public StockCodeAdapter(Context context) {
        super(context);
    }

    public StockCodeAdapter(Context context, boolean isCircle) {
        super(context, isCircle);
    }
    
    @Override
	public void renderData(int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
    	StockModel stock= getItemT(position);
        TextView stockname = vh.getView(TextView.class, R.id.stockname);

        stockname.setText(stock.getStockName());
	}

    @Override
    public int[] getFindViewByIDs() {
    	return new int[]{R.id.stockname};
    }

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_stockcode_item);
	}
}
