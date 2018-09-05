package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.model.DoneTaskModel;
import com.wms.newwmsapp.tool.EncodingUtils;
import com.wms.newwmsapp.tool.JsonDateFormate;
import com.wms.newwmsapp.tool.PrintCodePop;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cheng on 2018/6/28.
 */

public class DoneTaskAdapter extends BaseAdapter {
    private Context context;
    private List<DoneTaskModel> mData = new ArrayList<DoneTaskModel>();
    private Window window;

    public DoneTaskAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DoneTaskModel> data, Window window) {
        this.window = window;
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;

        }
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
            convertView = View.inflate(context, R.layout.adapter_done_task, null);
            holder = new ViewHolder();
            holder.tvOrderNo = (TextView) convertView.findViewById(R.id.tv_order_no);
            holder.tvOrderTime = (TextView) convertView.findViewById(R.id.tv_order_time);
            holder.tvOrderDivideNo = (TextView) convertView.findViewById(R.id.tv_order_divide_no);
            holder.tvPrintTime = (TextView) convertView.findViewById(R.id.tv_print_time);
            holder.tvPrintName = (TextView) convertView.findViewById(R.id.tv_print_name);
            holder.tvPrintLastTime = (TextView) convertView.findViewById(R.id.tv_print_last_time);
            holder.llOrderItem = (LinearLayout) convertView.findViewById(R.id.ll_order_item);
            holder.btnPrint = (Button) convertView.findViewById(R.id.btn_print);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        final DoneTaskModel dataBean = mData.get(position);
        holder.tvOrderNo.setText("单号：" + dataBean.getCode());
        holder.tvOrderTime.setText("创建时间：" + JsonDateFormate.dataFormate(dataBean.getCreateDate()));
        holder.tvOrderDivideNo.setText("分拣单号：" + dataBean.getWaveOutStockCode());
        holder.tvPrintTime.setText("面单打印次数：" + dataBean.getIsPrint_ShipInfo());
        holder.tvPrintName.setText("最后打印人：" + dataBean.getPrintShipInfoUser());
        holder.tvPrintLastTime.setText("最后打印时间：" + JsonDateFormate.dataFormate(dataBean.getPrintDate()));

        holder.btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                PrintCodePop showMoreMenuPop = new PrintCodePop(context, window, EncodingUtils.createBarcode(dataBean.getCode(), width, 200, false),"3");
                showMoreMenuPop.showUp();
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView tvOrderNo, tvOrderTime, tvOrderDivideNo, tvPrintTime, tvPrintName, tvPrintLastTime;
        private LinearLayout llOrderItem;
        private Button btnPrint;
    }
}
