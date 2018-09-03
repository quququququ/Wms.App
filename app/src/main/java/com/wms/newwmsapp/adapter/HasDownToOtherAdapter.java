package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.model.DownToOtherModel;

import java.util.List;


/**
 * Created by cheng on 2018/6/25.
 */

public class HasDownToOtherAdapter extends BaseAdapter {
    private Context context;
    private List<DownToOtherModel.DataBean> detailsBeen;


    public HasDownToOtherAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DownToOtherModel.DataBean> list) {
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
    public View getView(final int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_down_to_other, parent, false);
            holder = new ViewHolder();
            holder.tvDownBianCode = (TextView) convertView.findViewById(R.id.task_bianma);
            holder.tvDownTiaoCode = (TextView) convertView.findViewById(R.id.task_tiaoma);
            holder.tvDownName = (TextView) convertView.findViewById(R.id.tv_down_name);
            holder.tvDownNum = (TextView) convertView.findViewById(R.id.tv_down_num);
            holder.tvDownPici = (TextView) convertView.findViewById(R.id.tv_dowm_pici);
            holder.tvDownDate = (TextView) convertView.findViewById(R.id.tv_down_time);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btn_to_delete);
            holder.llDownToOther = (RelativeLayout) convertView.findViewById(R.id.ll_down_to_other);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DownToOtherModel.DataBean dataBean = detailsBeen.get(i);
        holder.tvDownBianCode.setText(dataBean.getCustomerCode());
        holder.tvDownTiaoCode.setText(dataBean.getBarCode());
        holder.tvDownName.setText(dataBean.getGoodsName());
        holder.tvDownNum.setText(dataBean.getUpNo() + dataBean.getGoodsUnitName());
        holder.tvDownPici.setText(dataBean.getBarCode());
        holder.tvDownDate.setText(dataBean.getProductionDate());
        if (Double.parseDouble(dataBean.getNum()) != 0.0) {
            holder.btnDelete.setVisibility(View.VISIBLE);
        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != downToOtherListener) {
                    downToOtherListener.deletePost(i);
                }
            }
        });

        if (Double.parseDouble(dataBean.getUpNo()) != 0.0) {
            holder.llDownToOther.setVisibility(View.VISIBLE);
        }else{
            holder.llDownToOther.setVisibility(View.GONE);
        }

        return convertView;
    }


    public static class ViewHolder {
        private TextView tvDownBianCode, tvDownTiaoCode, tvDownName, tvDownNum, tvDownPici, tvDownDate;
        private Button btnDelete;
        private RelativeLayout llDownToOther;
    }

    private DownToOtherListener downToOtherListener;

    public void setOnIPostPackageNoListener(DownToOtherListener downToOtherListener) {
        this.downToOtherListener = downToOtherListener;
    }

    public interface DownToOtherListener {
        void deletePost(int position);
    }

}
