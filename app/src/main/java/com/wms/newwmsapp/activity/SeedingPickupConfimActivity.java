package com.wms.newwmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.WavePickupConfirmAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.WavePickupModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonArrayRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class SeedingPickupConfimActivity extends BaseActivity {

    private ImageView back, refrash;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mFilterLayout;
    private TextView tvTask,tvDoneTask;
    private ListView pick_list;
    private List<WavePickupModel> pickList = new ArrayList<WavePickupModel>();
    private WavePickupConfirmAdapter pickUpadapter;
    private Button button_check;
    private EditText txtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeding_pickup_confirm);

        back = (ImageView) findViewById(R.id.back);
        refrash = (ImageView) findViewById(R.id.refrash);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFilterLayout = (LinearLayout) findViewById(R.id.filter_layout);

        // 开始不弹出查询页面
        // if (!mDrawerLayout.isDrawerOpen(mFilterLayout)) {
        // mDrawerLayout.openDrawer(mFilterLayout);
        // }

        pick_list = (ListView) findViewById(R.id.pick_list);

        button_check = (Button) findViewById(R.id.button_check);
        tvDoneTask = (TextView) findViewById(R.id.tv_done_task);

        txtCode = (EditText) findViewById(R.id.Code);

        getPickUpList(txtCode.getText().toString());

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        refrash.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                getPickUpList(txtCode.getText().toString());
            }
        });

        button_check.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                getPickUpList(txtCode.getText().toString());
            }
        });

        tvTask = (TextView) findViewById(R.id.tv_task);
        tvTask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeedingPickupConfimActivity.this, BatchTaskActivity.class);
                startActivity(intent);
            }
        });

        tvDoneTask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeedingPickupConfimActivity.this, DoneTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getPickUpList(final String pickUpConfirmCode) {
        startProgressDialog("Loading...");

        JSONStringer jsonStr = new JSONStringer();

        RequestQueue mRequestQueue = Volley.newRequestQueue(SeedingPickupConfimActivity.this);
        JsonArrayRequest request = new JsonArrayRequest(
                Method.GET, Constants.url_WavePickupConfirm + "?stockCode=" + preferences.getString("StockCode", "")
                + "&userCode=" + preferences.getString("code", ""),
                jsonStr.toString(), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub

                try {
                    pickList = com.alibaba.fastjson.JSONArray.parseArray(response.toString(),
                            WavePickupModel.class);
                    List<WavePickupModel> list = new ArrayList<WavePickupModel>();
                    if (!pickUpConfirmCode.isEmpty()) {
                        for (int i = 0; i < pickList.size(); i++) {
                            if (pickList.get(i).Code == pickUpConfirmCode) {
                                list.add(pickList.get(i));
                            }

                        }

                    } else {
                        list.addAll(pickList);

                    }

                    if (pickUpadapter == null) {
                        pickUpadapter = new WavePickupConfirmAdapter(SeedingPickupConfimActivity.this, true, "SeedingPickupDetailActivity");
                        pick_list.setAdapter(pickUpadapter);
                        pickUpadapter.appendList(list);
                    } else {
                        pickUpadapter.clear();
                        pickUpadapter.appendList(list);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    stopProgressDialog();
                    e.printStackTrace();
                }
                stopProgressDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }

        }, handler);
        mRequestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.batch_pickup_confirm, menu);
        return true;
    }

}
