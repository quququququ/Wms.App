package com.wms.newwmsapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.BatchTaskAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.BatchTaskModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/8/30.
 */

public class BatchTaskActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private ImageView back;
    private BGARefreshLayout mRefreshLayout;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private int page = 1;
    private BatchTaskAdapter mAdapter;
    private ListView lvBatchTask;
    private BatchTaskModel batchTaskModel;
    private List<BatchTaskModel.DataBean> list = new ArrayList<BatchTaskModel.DataBean>();
    public static BatchTaskActivity batchTaskActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_task);
        batchTaskActivity = this;
        initView();
        initData();
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
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_batch_task);
        mRefreshLayout.setDelegate(this);               //设置listener
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        mAdapter = new BatchTaskAdapter(this);
        lvBatchTask = (ListView) findViewById(R.id.lv_batch_task);
        lvBatchTask.setAdapter(mAdapter);
    }

    private void initData() {
        startProgressDialog("Loading...");

        JSONStringer jsonStr = new JSONStringer();
        String url = Constants.url_GetWavePickClaimList + "stockCode=" + preferences.getString("StockCode", "")+"&pageSize=20&pageIndex="+page;
        Log.i("走不走", url + "");
        RequestQueue mRequestQueue = Volley.newRequestQueue(BatchTaskActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("走不走", response.toString() + "");
                // TODO Auto-generated method stub
                try {
                    batchTaskModel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), BatchTaskModel.class);
                    list = batchTaskModel.getData();
                    if (isLoad) {
                        isLoad = false;
                        if (list.size() < 10)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(list);
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        stopProgressDialog();
                        return;
                    }
                    mAdapter.setData(list);
                    if (list.size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    stopProgressDialog();
                } catch (Exception e) {
                    // TODO: handle exception
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    e.printStackTrace();
                }
                stopProgressDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("走不走", error.toString() + "");
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                stopProgressDialog();
            }

        }, handler);
        mRequestQueue.add(request);


    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoad = false;
        isFirst = true;
        page = 1;
        initData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoad = true;
        isFirst = false;
        page++;
        if (needLoad) {
            initData();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
