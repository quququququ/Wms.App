package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wms.newwmsapp.R;

import java.util.List;


/**
 * Created by cheng on 2018/6/25.
 */

public class FinishToCarAdapter extends BaseAdapter {
    private Context context;
    private List<String> detailsBeen;


    public FinishToCarAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> list) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finish_to_car, parent, false);
            holder = new ViewHolder();
            holder.tvPackageName = (TextView) convertView.findViewById(R.id.tv_package);
            holder.ivPackageName = (ImageView) convertView.findViewById(R.id.iv_package);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvPackageName.setText(detailsBeen.get(i));
        holder.ivPackageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = detailsBeen.get(i);
                detailsBeen.remove(i);
                if (mListener != null) {
                    mListener.deleteList(str);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public List<String> getList() {
        return detailsBeen;
    }

    public void clearList() {
        detailsBeen.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        private TextView tvPackageName;
        private ImageView ivPackageName;
    }

    private ICashPaymentPwdListener mListener;

    public void setOnICashPaymentPwdListener(ICashPaymentPwdListener mListener) {
        this.mListener = mListener;
    }


    public interface ICashPaymentPwdListener {

        void deleteList(String str);
    }
}
