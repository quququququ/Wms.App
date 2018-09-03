package com.wms.newwmsapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.model.UpToOtherListModel;

import java.util.List;


/**
 * Created by cheng on 2018/6/25.
 */

public class UpToOtherAdapter extends BaseAdapter {
    private Context context;
    private List<UpToOtherListModel.DataBean> detailsBeen;


    public UpToOtherAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<UpToOtherListModel.DataBean> list) {
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
            holder.llDownToOther = (RelativeLayout) convertView.findViewById(R.id.ll_down_to_other);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final UpToOtherListModel.DataBean dataBean = detailsBeen.get(i);
        holder.tvDownBianCode.setText(dataBean.getCustomerCode());
        holder.tvDownTiaoCode.setText(dataBean.getBarCode());
        holder.tvDownName.setText(dataBean.getGoodsName());
        holder.tvDownNum.setText(dataBean.getNum() + dataBean.getGoodsUnitName());
        holder.tvDownPici.setText(dataBean.getProductId());
        holder.tvDownDate.setText(dataBean.getProductionDate());

        if(detailsBeen.size() == 1){
            final EditText inputServer = new EditText(context);
            inputServer.setFocusable(true);

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("请输入上架数量").setView(inputServer).setNegativeButton(
                    "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    String inputName = inputServer.getText().toString();
                    if (TextUtils.isEmpty(inputName) || Integer.parseInt(inputName) == 0) {
                        Toast.makeText(context, "请输入上架数量", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (Integer.parseInt(inputName) > Integer.parseInt(dataBean.getNum())) {
                        Toast.makeText(context, "上架数量不得大于商品数量", Toast.LENGTH_LONG).show();
                        return;
                    }
                    detailsBeen.get(0).setUpNo(inputName);
                    if (null != downToOtherListener) {
                        downToOtherListener.deletePost(dataBean);
                        notifyDataSetChanged();
                    }

                }
            });
            builder.show();
        }

        holder.llDownToOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer = new EditText(context);
                inputServer.setFocusable(true);

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("请输入上架数量").setView(inputServer).setNegativeButton(
                        "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = inputServer.getText().toString();
                        if (TextUtils.isEmpty(inputName) || Integer.parseInt(inputName) == 0) {
                            Toast.makeText(context, "请输入上架数量", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (Integer.parseInt(inputName) > Integer.parseInt(dataBean.getNum())) {
                            Toast.makeText(context, "上架数量不得大于商品数量", Toast.LENGTH_LONG).show();
                            return;
                        }
                        dataBean.setUpNo(inputName);
                        if (null != downToOtherListener) {
                            downToOtherListener.deletePost(dataBean);
                            notifyDataSetChanged();
                        }

                    }
                });
                builder.show();

            }
        });
        return convertView;
    }

    public void clearData() {
        if (null != detailsBeen && detailsBeen.size() != 0) {
            detailsBeen.clear();
            notifyDataSetChanged();
        }
    }


    public static class ViewHolder {
        private TextView tvDownBianCode, tvDownTiaoCode, tvDownName, tvDownNum, tvDownPici, tvDownDate;
        private RelativeLayout llDownToOther;
    }

    private DownToOtherListener downToOtherListener;

    public void setOnIPostPackageNoListener(DownToOtherListener downToOtherListener) {
        this.downToOtherListener = downToOtherListener;
    }

    public interface DownToOtherListener {
        void deletePost(UpToOtherListModel.DataBean dataBean);
    }

}
