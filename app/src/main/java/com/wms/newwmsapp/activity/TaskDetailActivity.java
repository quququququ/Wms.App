package com.wms.newwmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.BaseModel;
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
 * Created by cheng on 2018/8/30.
 */

public class TaskDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String CODE = "code";
    public static final String ORDER_NUM = "order.num";
    public static final String DIVIDER_NUM = "divider.num";
    public static final String GOODS_TYPE = "goods.type";
    public static final String TYPE = "type";
    public static final String EXPRESS = "express";
    public static final String CREAT_TIME = "creat.time";
    private TextView tvOrderNo, tvOrderNum, tvFenjianNum, tvGoodsType, tvType, tvExpress, tvOrderTime;
    private Button btnGetTask, btnDivideTask, btnCancel;
    private ImageView back;
    private String waveCode;
    private BaseModel baseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_task_detail);
        waveCode = getIntent().getStringExtra(CODE);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        tvOrderNo = (TextView) findViewById(R.id.tv_task_no);
        tvOrderNum = (TextView) findViewById(R.id.tv_order_num);
        tvFenjianNum = (TextView) findViewById(R.id.tv_fenjian_num);
        tvGoodsType = (TextView) findViewById(R.id.tv_goods_type);
        tvType = (TextView) findViewById(R.id.tv_order_type);
        tvExpress = (TextView) findViewById(R.id.tv_express);
        tvOrderTime = (TextView) findViewById(R.id.tv_task_time);
        tvOrderNo.setText(getIntent().getStringExtra(CODE));
        tvOrderNum.setText(getIntent().getStringExtra(ORDER_NUM));
        tvFenjianNum.setText(getIntent().getStringExtra(DIVIDER_NUM));
        tvGoodsType.setText(getIntent().getStringExtra(GOODS_TYPE));
        tvType.setText(getIntent().getStringExtra(TYPE));
        tvExpress.setText(getIntent().getStringExtra(EXPRESS));
        tvOrderTime.setText(getIntent().getStringExtra(CREAT_TIME));

        btnGetTask = (Button) findViewById(R.id.btn_get_task);
        btnDivideTask = (Button) findViewById(R.id.btn_divide_task);
        btnCancel = (Button) findViewById(R.id.btn_cancel_task);
        btnGetTask.setOnClickListener(this);
        btnDivideTask.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_task:
                getTask();
                break;
            case R.id.btn_divide_task:
                divideTask();
                break;
            case R.id.btn_cancel_task:
                cancelTask();
                break;
        }
    }

    private void cancelTask() {
        finish();
    }

    private void divideTask() {
        startProgressDialog("Loading...");

        JSONStringer jsonStr = new JSONStringer();
        String url = Constants.url_WavePickClaim + "stockCode=" + preferences.getString("StockCode", "") + "&wavepickconfirmCode=" + waveCode + "userCode" + preferences.getString("code", "");
        RequestQueue mRequestQueue = Volley.newRequestQueue(TaskDetailActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    baseModel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), BaseModel.class);
                    if (baseModel.isIsSuccess()) {
                        Intent intent = new Intent(TaskDetailActivity.this,BatchPickupOtherActivity.class);
                        intent.putExtra("pickcode",waveCode);
                        finish();
                    } else {
                        Toast.makeText(TaskDetailActivity.this, baseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    stopProgressDialog();
                }
                stopProgressDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                MyToast.showDialog(TaskDetailActivity.this, error.toString());
                stopProgressDialog();
            }

        }, handler);
        mRequestQueue.add(request);
    }

    private void getTask() {
        startProgressDialog("Loading...");

        JSONStringer jsonStr = new JSONStringer();
        String url = Constants.url_WavePickClaim + "stockCode=" + preferences.getString("StockCode", "") + "&wavepickconfirmCode=" + waveCode + "&  userCode" + preferences.getString("code", "");
        Log.i("走不走", url + "");
        RequestQueue mRequestQueue = Volley.newRequestQueue(TaskDetailActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("走不走", response.toString() + "");
                // TODO Auto-generated method stub
                try {
                    baseModel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), BaseModel.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(TaskDetailActivity.this, "认领成功", Toast.LENGTH_LONG).show();
                        BatchTaskActivity.batchTaskActivity.finish();
                        finish();
                    } else {
                        Toast.makeText(TaskDetailActivity.this, baseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                stopProgressDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                MyToast.showDialog(TaskDetailActivity.this, error.toString());
                stopProgressDialog();
            }

        }, handler);
        mRequestQueue.add(request);

    }


}
