package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.AddNewStockTaskActivity;
import com.wms.newwmsapp.activity.StockTaskDetailActivity;
import com.wms.newwmsapp.model.AddNewStockTaskModle;

import java.util.List;

/**
 * Created by qupengcheng on 2018/1/11.
 */

public class SearchResultAdapter extends BaseAdapter {
    private Context context;
    private String name, goodsCode;
    private List<AddNewStockTaskModle.DetailsBean.GoodsUnitBean> taskDetailList;
    private String custGoodsCode,checkTaskCode,goodsPosCode,barCode,customerCode;

    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AddNewStockTaskModle.DetailsBean.GoodsUnitBean> list, String name, String goodsCode,
                        String custGoodsCode,String checkTaskCode,String goodsPosCode,String barCode,String customerCode) {
        this.name = name;
        this.goodsCode = goodsCode;
        this.custGoodsCode = custGoodsCode;
        this.checkTaskCode = checkTaskCode;
        this.goodsPosCode = goodsPosCode;
        this.barCode = barCode;
        this.customerCode = customerCode;
        if (null != list && list.size() > 0) {
            this.taskDetailList = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return null == taskDetailList ? 0 : 1;
    }

    @Override
    public Object getItem(int i) {
        return taskDetailList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final AddNewStockTaskModle.DetailsBean.GoodsUnitBean data = taskDetailList.get(i);

        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_instock_item, parent, false);
            holder = new ViewHolder();
            holder.instockLin = (LinearLayout) convertView.findViewById(R.id.instock_lin); //名称
            holder.tvName = (TextView) convertView.findViewById(R.id.Code); //名称
            holder.tvName.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            holder.tvBianma = (TextView) convertView.findViewById(R.id.PurchaseNO); //编码
            holder.tvTiaoma = (TextView) convertView.findViewById(R.id.CustGoodsName); //条码
            holder.tvOwner = (TextView) convertView.findViewById(R.id.InformDate); //货主
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(name))
            holder.tvName.setText(name);
        if (!TextUtils.isEmpty(barCode))
            holder.tvBianma.setText(barCode);
        if (!TextUtils.isEmpty(customerCode))
            holder.tvTiaoma.setText(customerCode);
        if (!TextUtils.isEmpty(custGoodsCode))
            holder.tvOwner.setText(custGoodsCode);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StockTaskDetailActivity.class);
                StockTaskDetailActivity.stockTaskDetailActivity.finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StockTaskDetailActivity.NEED_ADD_ITEM, true);
                intent.putExtra(StockTaskDetailActivity.STOCK_CHECKTASK_CODE,checkTaskCode);
                intent.putExtra(StockTaskDetailActivity.STOCK_GOODSPOS_CODE,goodsPosCode);
                intent.putExtra("goodsName",name);
                intent.putExtra("goodsCode",goodsCode);
                intent.putExtra("barCode",barCode);
                intent.putExtra("getCreateDate",data.getCreateDate());
                intent.putExtra("custGoodsCode",custGoodsCode);
                intent.putExtra("barCode",barCode);
                intent.putExtra("customerCode",customerCode);
                intent.putExtra("Code",data.getCode());
                context.startActivity(intent);
                AddNewStockTaskActivity.addNewStockTaskActivity.finish();
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        private TextView tvName, tvBianma, tvTiaoma, tvOwner;
        private LinearLayout instockLin;
    }
}
