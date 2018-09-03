package com.wms.newwmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.SeedingOtherModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONStringer;

public class SeedingOtherActivity extends BaseActivity {
    private ImageView back;
    private Button btnSearch;
    private EditText txtWavePickupConfirmCode;
    private SeedingOtherModel model = new SeedingOtherModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_seeding_other);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        txtWavePickupConfirmCode = (EditText) findViewById(R.id.WavePickupConfirmCode);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String pickCode = txtWavePickupConfirmCode.getText().toString().trim();
                if (pickCode.isEmpty()) {
                    MyToast.showDialog(SeedingOtherActivity.this, "请输入波次单号！");
                    return;

                }
                GetDetail(pickCode);
            }
        });

    }

    private void GetDetail(String pickCode) {
        startProgressDialog("Loading...");
        String url = Constants.url_GetWavePickConfirmForSowing
                + "?code=" + pickCode;

        JSONStringer jsonStr = new JSONStringer();

        RequestQueue mRequestQueue = Volley
                .newRequestQueue(SeedingOtherActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Method.GET, url,
                jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    com.alibaba.fastjson.JSONObject jsonObject = JSON
                            .parseObject(response.toString());
                    model = JSON.parseObject(response.toString(),
                            SeedingOtherModel.class);
                    if (model.isIsSuccess()) {
                        Intent intent = new Intent(SeedingOtherActivity.this, SeedingOtherDetailActivity.class);
                        intent.putExtra("Code", model.getCode());
                        intent.putExtra("AllNum", model.getTotalNum());
                        intent.putExtra("AllOrderCount", model.getTotalOrderCount());
                        startActivity(intent);
                    } else {
                        MyToast.showDialog(SeedingOtherActivity.this, "请输入波次单号！");
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                stopProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopProgressDialog();

            }
        }, handler);
        mRequestQueue.add(request);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seeding, menu);
        return true;
    }

}
