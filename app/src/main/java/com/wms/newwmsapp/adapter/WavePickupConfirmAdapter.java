package com.wms.newwmsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.activity.BatchPickupDetailActivity;
import com.wms.newwmsapp.activity.BatchPickupOtherActivity;
import com.wms.newwmsapp.activity.PickUpOtherActivity;
import com.wms.newwmsapp.activity.SeedingPickupDetailActivity;
import com.wms.newwmsapp.base.DataAdapter;
import com.wms.newwmsapp.base.DataViewHolder;
import com.wms.newwmsapp.model.WavePickupModel;
import com.wms.newwmsapp.tool.JsonDateFormate;


public class WavePickupConfirmAdapter extends DataAdapter<WavePickupModel> {

    private Context context;
    private String newContext;
    private boolean isOhter = false;

    public WavePickupConfirmAdapter(Context context) {
        super(context);
    }

    public WavePickupConfirmAdapter(Context context, boolean isCircle, String newContext) {
        super(context, isCircle);
        this.context = context;
        this.newContext = newContext;
    }

    public WavePickupConfirmAdapter(Context context, boolean isCircle, String newContext, boolean isOther) {
        super(context, isCircle);
        this.isOhter = isOther;
        this.context = context;
        this.newContext = newContext;
    }

    @Override
    public void renderData(int position, DataViewHolder vh) {
        // TODO Auto-generated method stub
        final WavePickupModel pick = getItemT(position);
        LinearLayout pick_lin = vh.getView(LinearLayout.class, R.id.wave_pickup_confirm);
        TextView Code = vh.getView(TextView.class, R.id.Code);
        TextView WaveOutStockPickCode = vh.getView(TextView.class, R.id.WaveOutStockPickCode);
        TextView CreateDate = vh.getView(TextView.class, R.id.CreateDate);


        Code.setText("单号: " + pick.getCode());
        WaveOutStockPickCode.setText("分拣单号: " + pick.getWaveOutStockPickCode());
        CreateDate.setText("创建时间: " + JsonDateFormate.dataFormate(pick.getCreateDate()));

        pick_lin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (newContext == "BatchPickupDetailActivity") {
                    Intent intent = new Intent(context, BatchPickupDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Pick", pick);
                    context.startActivity(intent);
                } else if (newContext == "SeedingPickupDetailActivity") {
                    Intent intent = new Intent(context, SeedingPickupDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Pick", pick);
                    intent.putExtra("isOther", isOhter);
                    context.startActivity(intent);
                } else if ("PickUpOtherActivity".equals(newContext)) {
                    Intent intent = new Intent(context, BatchPickupOtherActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Pick", pick);
                    intent.putExtra("isOther", isOhter);

                    context.startActivity(intent);
                    PickUpOtherActivity.pickUpOtherActivity.finish();
                }
            }
        });
    }

    @Override
    public int[] getFindViewByIDs() {
        return new int[]{R.id.wave_pickup_confirm, R.id.Code,
                R.id.CreateDate, R.id.WaveOutStockPickCode};
    }

    @Override
    public View getLayout(int position, DataViewHolder vh, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getResourceView(R.layout.adapter_wavepickup_confirm);
    }
}
