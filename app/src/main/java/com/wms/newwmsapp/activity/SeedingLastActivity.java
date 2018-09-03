package com.wms.newwmsapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.OtherDetailAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.OtherDetailData;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by cheng on 2018/6/19.
 */

public class SeedingLastActivity extends BaseActivity {

    private ImageView back;
    private ListView lvSeedingLast;
    private OtherDetailAdapter mAdapter;
    private OtherDetailData model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeding_last);

        code = getIntent().getStringExtra("Code");

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        initView();
        initData();

    }

    private void initData() {
        startProgressDialog("Loading...");
        String url = Constants.url_GetWavePickConfirmDetailForSowing
                + "?code=" + getIntent().getStringExtra("Code") ;

        JSONStringer jsonStr = new JSONStringer();

        RequestQueue mRequestQueue = Volley
                .newRequestQueue(SeedingLastActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    com.alibaba.fastjson.JSONObject jsonObject = JSON
                            .parseObject(response.toString());
                    model = JSON.parseObject(response.toString(),
                            OtherDetailData.class);
                    if (model.isIsSuccess()) {
                        mAdapter.setData(model.getDetails());
                    } else {
                        MyToast.showDialog(SeedingLastActivity.this, "查询失败！");
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

    private void initView() {
        lvSeedingLast = (ListView) findViewById(R.id.lv_seeding_last);
        mAdapter = new OtherDetailAdapter(this);
        lvSeedingLast.setAdapter(mAdapter);
    }
}
