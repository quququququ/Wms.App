package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.model.OtherDetailData;

import java.util.List;

/**
 * Created by cheng on 2018/6/19.
 */

public class OtherDetailAdapter extends BaseAdapter {
    private Context context;

    private List<OtherDetailData.DetailsBean> detailsBeen;


    public OtherDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<OtherDetailData.DetailsBean> list) {
        if (null != list && list.size() > 0) {
            this.detailsBeen = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return null == detailsBeen ? 0 : detailsBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return detailsBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final OtherDetailData.DetailsBean data = detailsBeen.get(i);

        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_batch_pickup_detail, parent, false);
            holder = new ViewHolder();
            holder.tvCode = (TextView) convertView.findViewById(R.id.GoodsCode); //编码
            holder.tvSortNo = (TextView) convertView.findViewById(R.id.GoodsBarCode); //序号
            holder.tvName = (TextView) convertView.findViewById(R.id.GoodsCustomerCode); //名称
            holder.tvColor = (TextView) convertView.findViewById(R.id.GoodsColor); //颜色
            holder.tvModel = (TextView) convertView.findViewById(R.id.GoodsModel); //规格
            holder.tvGoodsNum = (TextView) convertView.findViewById(R.id.GoodsNum); //数量
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvCode.setText("商品编码：  " + data.getGoodsCustomerCode());
        holder.tvSortNo.setText("商品序号：  " + data.getSortNo());
        holder.tvName.setText("商品名称：  " + data.getGoodsName());
        holder.tvColor.setText("商品颜色：  " + data.getGoodsColor());
        holder.tvModel.setText("商品规格：  " + data.getGoodsModel());
        holder.tvGoodsNum.setVisibility(View.VISIBLE);
        holder.tvGoodsNum.setText("商品数量：  " + data.getNum());
        return convertView;
    }

    public static class ViewHolder {
        private TextView tvCode, tvName, tvColor, tvModel, tvSortNo,tvGoodsNum;
    }
}
