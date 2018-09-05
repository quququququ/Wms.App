package com.wms.newwmsapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.DoneTaskAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.DoneTaskModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonArrayRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/9/4.
 */

public class DoneTaskActivity extends BaseActivity {
    private ListView lvDoneTask;
    private DoneTaskAdapter mAdapter;
    private List<DoneTaskModel> pickList = new ArrayList<DoneTaskModel>();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_has_done_task);
        initView();
        initData();
    }

    private void initView() {
        lvDoneTask = (ListView) findViewById(R.id.lv_done_task);
        mAdapter = new DoneTaskAdapter(this);
        lvDoneTask.setAdapter(mAdapter);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//                int width = wm.getDefaultDisplay().getWidth();
//                PrintCodePop showMoreMenuPop = new PrintCodePop(DoneTaskActivity.this, getWindow(), EncodingUtils.createBarcode("1314520", width, 200, false));
//                showMoreMenuPop.showUp();
//            }
//        });
    }

    private void initData() {
        startProgressDialog("Loading...");

        JSONStringer jsonStr = new JSONStringer();
        String url = Constants.url_GetWavePickCompleteList + "stockCode=" + preferences.getString("StockCode", "") + "&userCode=" + preferences.getString("code", "");
        Log.i("走不走", url + "");
        RequestQueue mRequestQueue = Volley.newRequestQueue(DoneTaskActivity.this);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, jsonStr.toString(), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i("走不走", response.toString() + "");
                // TODO Auto-generated method stub
                try {
                    pickList = com.alibaba.fastjson.JSONArray.parseArray(response.toString(), DoneTaskModel.class);
                    mAdapter.setData(pickList, getWindow());
                    stopProgressDialog();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                stopProgressDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("走不走", error.toString() + "");
                stopProgressDialog();
            }

        }, handler);
        mRequestQueue.add(request);


    }

}
