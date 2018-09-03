package com.wms.newwmsapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.InstockDetailActivity;
import com.wms.newwmsapp.model.TaskDetailModel;
import com.wms.newwmsapp.model.UnitModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by qupengcheng on 2018/1/10.
 */

public class StockDetailAdapter extends BaseAdapter {
    private Context context;
    private List<TaskDetailModel.DetailsBean> taskDetailList;

    public StockDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TaskDetailModel.DetailsBean> list) {
        if (null != list && list.size() > 0) {
            this.taskDetailList = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return null == taskDetailList ? 0 : taskDetailList.size();
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
    public View getView(final int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final TaskDetailModel.DetailsBean data = taskDetailList.get(i);
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stock_task, parent, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.task_name);
            holder.tvBianma = (TextView) convertView.findViewById(R.id.task_bianma);
            holder.tvTiaoma = (TextView) convertView.findViewById(R.id.task_tiaoma);
            holder.tvProductTime = (TextView) convertView.findViewById(R.id.tv_product_time);
            holder.tvProductId = (EditText) convertView.findViewById(R.id.tv_product_id);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tvPanNum = (TextView) convertView.findViewById(R.id.tv_pan_num);

//            holder.tvPanNum.setFocusable(false);
//            holder.tvPanNum.setEnabled(false);
            holder.tvProductId.setFocusable(false);
            holder.tvProductId.setEnabled(false);
            holder.tvProductTime.setFocusable(false);
            holder.tvProductTime.setEnabled(false);
            holder.llStockTaskDetail = (LinearLayout) convertView.findViewById(R.id.ll_stock_task_detail);
            holder.ll_select_num_quality = (LinearLayout) convertView.findViewById(R.id.ll_select_num_quality);
            holder.ll_num = (LinearLayout) convertView.findViewById(R.id.ll_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (data.isAdd()) {
            holder.llStockTaskDetail.setBackgroundResource(R.drawable.picklist_ll_blue_bg);
        } else if (null == data.getNum() || "0".equals(data.getNum())) {
            holder.llStockTaskDetail.setBackgroundResource(R.drawable.picklist_linearlayout_white_bg);
        } else {
            holder.llStockTaskDetail.setBackgroundResource(R.drawable.picklist_linearlayout_bg);
        }
        holder.tvName.setText(data.getGoodsName());
        holder.tvBianma.setText(data.getCustomerCode());
        holder.tvTiaoma.setText(data.getBarCode());
        holder.tvProductTime.setText(data.getProductionDate());
        holder.tvProductId.setText(data.getProductId() + "");
        holder.tvNum.setText(data.getOldSum() + "(" + data.getUnitName() + ")" + data.getQualityName());
        holder.tvPanNum.setText(data.getNum());

        holder.tvPanNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view1 = inflater.inflate(R.layout.dialog_customercode, null);

                final AlertDialog ad = new AlertDialog.Builder(context).create();
                ad.setView(view1);
                ad.show();

                final EditText CustomerCode_et = (EditText) view1.findViewById(R.id.CustomerCode);
                TextView textView2 = (TextView) view1.findViewById(R.id.textView2);
                textView2.setText("输入盘点数量");
                Button button_ok = (Button) view1.findViewById(R.id.button_ok);
                button_ok.setText("确定");

                button_ok.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub

                        if (TextUtils.isEmpty(CustomerCode_et.getText().toString().trim())) {
                            Toast.makeText(context, "不能输入为空", Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            data.setNum(CustomerCode_et.getText().toString().trim());
                            data.setAdd(true);
                            notifyDataSetChanged();
                            ad.dismiss();
                        }

                    }
                });


            }
        });
        return convertView;
    }

    public static class ViewHolder {
        private TextView tvName, tvBianma, tvTiaoma,tvPanNum,  tvNum, tvProductTime;
        private EditText tvProductId;
        private LinearLayout llStockTaskDetail, ll_select_num_quality, ll_num;
    }

    public List<TaskDetailModel.DetailsBean> upData() {
        List<TaskDetailModel.DetailsBean> detailsBeen = new ArrayList<TaskDetailModel.DetailsBean>();
        detailsBeen = taskDetailList;
        return detailsBeen;
    }
}


