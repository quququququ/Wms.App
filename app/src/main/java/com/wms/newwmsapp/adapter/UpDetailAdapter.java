package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.UpDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.UpDetailModel;
import com.wms.newwmsapp.tool.ListViewScrollView;

public class UpDetailAdapter extends DataAdapter<UpDetailModel>{

	private Context context;
	private String InStockPutawayStatusCode;
	
	public UpDetailAdapter(Context context) {
        super(context);
    }

    public UpDetailAdapter(Context context, boolean isCircle, String InStockPutawayStatusCode) {
        super(context, isCircle);
        this.context = context;
        this.InStockPutawayStatusCode = InStockPutawayStatusCode;
    }
    
    @Override
	public void renderData(final int position, DataViewHolder vh) {
		// TODO Auto-generated method stub
    	final UpDetailModel updetail= getItemT(position);
    	LinearLayout lin = vh.getView(LinearLayout.class, R.id.lin);
    	TextView CustomerCode = vh.getView(TextView.class, R.id.CustomerCode);
    	TextView GoodsName = vh.getView(TextView.class, R.id.GoodsName);
    	TextView Num = vh.getView(TextView.class, R.id.Num);
    	ListViewScrollView  goodspos_list = vh.getView(ListViewScrollView .class, R.id.goodspos_list);
    	
    	CustomerCode.setText(updetail.getCustomerCode());
    	Num.setText(updetail.getNum()+"");
    	GoodsName.setText(updetail.getGoodsName());
    	GoodsPosAdapter adapter = new GoodsPosAdapter(context, true, InStockPutawayStatusCode, updetail.getCode());
    	goodspos_list.setAdapter(adapter);
    	adapter.appendList(updetail.getList());
    	
    	lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 1;
				msg.obj = updetail.getCode();
				UpDetailActivity.myHandler.sendMessage(msg);
			}
		});

    	//起草状态(1.可编辑)
    	//其他状态(1.不可编辑)
    	if(InStockPutawayStatusCode.equals("1")){
    		lin.setClickable(true);
    	}else{
    		lin.setClickable(false);
    	}
	}

    @Override
    public int[] getFindViewByIDs() {
    	return new int[]{R.id.lin, R.id.CustomerCode, R.id.GoodsName, R.id.Num, R.id.goodspos_list};
    }

	@Override
	public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getResourceView(R.layout.adapter_updetail_item);
	}
}
