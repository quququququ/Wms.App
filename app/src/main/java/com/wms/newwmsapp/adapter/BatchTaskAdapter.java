package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.TaskDetailActivity;
import com.wms.newwmsapp.model.BatchTaskModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cheng on 2018/6/28.
 */

public class BatchTaskAdapter extends BaseAdapter {
    private Context context;
    private List<BatchTaskModel.DataBean> mData = new ArrayList<BatchTaskModel.DataBean>();

    public BatchTaskAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BatchTaskModel.DataBean> data) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;

        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<BatchTaskModel.DataBean> data) {
        if (null == data || data.size() == 0 || mData == null)
            return;
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.adapter_batch_task, null);
            holder = new ViewHolder();
            holder.tvOrderNo = (TextView) convertView.findViewById(R.id.tv_order_no);
            holder.tvOrderTime = (TextView) convertView.findViewById(R.id.tv_order_time);
            holder.tvOrderNum = (TextView) convertView.findViewById(R.id.tv_order_num);
            holder.tvGoodsNum = (TextView) convertView.findViewById(R.id.tv_goods_num);
            holder.tvOrderType = (TextView) convertView.findViewById(R.id.tv_order_type);
            holder.llOrderItem = (LinearLayout) convertView.findViewById(R.id.ll_order_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        final BatchTaskModel.DataBean dataBean = mData.get(position);
        holder.tvOrderNo.setText("单          号：" + dataBean.getCode().substring(dataBean.getCode().length() - 4, dataBean.getCode().length()));
        holder.tvOrderTime.setText("创建时间：" + dataBean.getCreateDate().substring(5, 10));
        holder.tvOrderNum.setText("订单数量：" + dataBean.getOutstockInformNum());
        holder.tvGoodsNum.setText("商品数量：" + dataBean.getGoodsTypeNum());
        String typeName = "";
        if (dataBean.getPickType().equals("1")) {
            typeName = "播种单";
        } else if (dataBean.getPickType().equals("2")) {
            typeName = "一单一件";
        } else if (dataBean.getPickType().equals("3")) {
            typeName = "爆品单";
        }
        holder.tvOrderType.setText("类          型：" + typeName);
        holder.llOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskDetailActivity.class);
                intent.putExtra(TaskDetailActivity.CODE, dataBean.getCode());
                intent.putExtra(TaskDetailActivity.ORDER_NUM, dataBean.getOutstockInformNum());
                intent.putExtra(TaskDetailActivity.DIVIDER_NUM, "");
                intent.putExtra(TaskDetailActivity.GOODS_TYPE, dataBean.getGoodsTypeNum());
                intent.putExtra(TaskDetailActivity.TYPE, dataBean.getPickType());
                intent.putExtra(TaskDetailActivity.EXPRESS, "");
                intent.putExtra(TaskDetailActivity.CREAT_TIME, dataBean.getCreateDate().substring(5, 10));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView tvOrderNo, tvOrderTime, tvOrderNum, tvGoodsNum, tvOrderType;
        private LinearLayout llOrderItem;
    }
}
