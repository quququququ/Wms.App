package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.StockTaskDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.StockCheckTaskModel;

/**
 * Created by qupengcheng on 2018/1/10.
 */

public class StockCheckTaskAdapter extends DataAdapter<StockCheckTaskModel.DetailsBean> {
    private Context context;

    public StockCheckTaskAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void renderData(int position, DataViewHolder vh) {
        final StockCheckTaskModel.DetailsBean instock= getItemT(position);
        TextView Code = vh.getView(TextView.class, R.id.Code);   //货区
        TextView PurchaseNO = vh.getView(TextView.class, R.id.PurchaseNO); //货位
        PurchaseNO.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        TextView CustGoodsName = vh.getView(TextView.class, R.id.CustGoodsName); //盘点单号
//        TextView InformDate = vh.getView(TextView.class, R.id.InformDate); //创建时间
        Code.setText(instock.getGoodsAreaName());
        PurchaseNO.setText(instock.getGoodsPosName ());
        CustGoodsName.setText(instock.getStockCheckTaskCode());
//        InformDate.setText(instock.getStockCheckTaskCreateDate());

        PurchaseNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,StockTaskDetailActivity.class);
                //模拟数据
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra(StockTaskDetailActivity.STOCK_CHECKTASK_CODE,"BAJPT17121500003");
//                intent.putExtra(StockTaskDetailActivity.STOCK_GOODSPOS_CODE,"32F3AF01AB0F7364E0502D0AC89412A2");
                intent.putExtra(StockTaskDetailActivity.STOCK_CHECKTASK_CODE,instock.getStockCheckTaskCode());
                intent.putExtra(StockTaskDetailActivity.STOCK_GOODSPOS_CODE,instock.getGoodsPosCode());
                intent.putExtra(StockTaskDetailActivity.STOCK_GOODSPOS_NAME,instock.getGoodsPosName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int[] getFindViewByIDs() {
        return new int[]{R.id.instock_lin ,R.id.Code, R.id.PurchaseNO, R.id.CustGoodsName};
    }

    @Override
    public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
        return getResourceView(R.layout.item_stock_check_adapter);
    }
}
