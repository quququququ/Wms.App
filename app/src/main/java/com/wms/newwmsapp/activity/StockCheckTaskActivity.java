package com.wms.newwmsapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.StockCheckTaskAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.StockCheckTaskModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.volley.Request.Method;
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
 * Created by qupengcheng on 2018/1/9.
 */

public class StockCheckTaskActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ImageView back;
    private ListView stockCheckTaskList;
    private StockCheckTaskAdapter stockCheckTaskAdapter;
    private List<StockCheckTaskModel.DetailsBean> pickList = new ArrayList<StockCheckTaskModel.DetailsBean>();
    private BGARefreshLayout mrefresh;
    private int page = 1;
    private boolean needLoad;
    public static StockCheckTaskActivity stockCheckTaskActivity;
    private boolean refresh;
    private ArrayList<String> codeList = new ArrayList<String>();
    private EditText etSearchCode;
    private List<StockCheckTaskModel.DetailsBean> detailsNew = new ArrayList<StockCheckTaskModel.DetailsBean>();
    private StockCheckTaskModel stockchecktaskmodel = new StockCheckTaskModel();
    private List<StockCheckTaskModel.DetailsBean> list = new ArrayList<StockCheckTaskModel.DetailsBean>();
    private Button searchOneCode, allList;
    private boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_check_task);
        stockCheckTaskActivity = this;
        initView();
        initData(1);
    }


    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        stockCheckTaskList = (ListView) findViewById(R.id.pull_stock_check_task);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        mrefresh = (BGARefreshLayout) findViewById(R.id.mrefresh);
        mrefresh.setDelegate(this);               //设置listener
        mrefresh.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        searchOneCode = (Button) findViewById(R.id.search_one_code);
        allList = (Button) findViewById(R.id.all_list);
        allList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh = true;
                isSearch = false;
                initData(1);
            }
        });
        searchOneCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch();
            }
        });

        stockCheckTaskAdapter = new StockCheckTaskAdapter(getApplicationContext());
        stockCheckTaskList.setAdapter(stockCheckTaskAdapter);

        etSearchCode = (EditText) findViewById(R.id.et_search_code);
        etSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearchCode.setFocusable(true);//设置输入框可聚集
                etSearchCode.setFocusableInTouchMode(true);//设置触摸聚焦
                etSearchCode.requestFocus();//请求焦点
                etSearchCode.findFocus();//获取焦点
                InputMethodManager imm1 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm1.showSoftInput(etSearchCode, InputMethodManager.SHOW_FORCED);// 显示输入法
            }
        });

        etSearchCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    goSearch();
                    etSearchCode.setText("");
                    return true;
                }
                return true;
            }
        });

        stockCheckTaskList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etSearchCode.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
                        InputMethodManager imm1 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if (imm1.isActive()) {
                            imm1.hideSoftInputFromWindow(etSearchCode.getWindowToken(), 0);// 隐藏输入法
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        etSearchCode.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
                        InputMethodManager imm2 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if (imm2.isActive()) {
                            imm2.hideSoftInputFromWindow(etSearchCode.getWindowToken(), 0);// 隐藏输入法
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        etSearchCode.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
                        InputMethodManager imm3 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if (imm3.isActive()) {
                            imm3.hideSoftInputFromWindow(etSearchCode.getWindowToken(), 0);// 隐藏输入法
                        }

                        break;
                }
                return false;
            }
        });
    }

    private void goSearch() {

        isSearch = true;
        startProgressDialog("Loading...");
        if (TextUtils.isEmpty(etSearchCode.getText().toString())) {
            Toast.makeText(StockCheckTaskActivity.this, "请输入查询货位号", Toast.LENGTH_LONG).show();
            stopProgressDialog();
        } else {
            JSONStringer jsonStr = new JSONStringer();
            String url = Constants.url_StockCheckTaskByUserCode + "?stockCode=" + preferences.getString("StockCode", "")
                    + "&userCode=" + preferences.getString("code", "") + "&pagesize=100" + "&pageindex=" + page + "&posname=" + etSearchCode.getText().toString();
            RequestQueue mRequestQueue = Volley.newRequestQueue(StockCheckTaskActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(
                    Method.GET, url, jsonStr.toString(), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    // TODO Auto-generated method stub
                    try {
                        stockchecktaskmodel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), StockCheckTaskModel.class);
                        list.clear();
                        list = stockchecktaskmodel.getDetails();
//                        for (int i = 0; i < list.size(); i++) {
//                            codeList.add(list.get(i).getGoodsPosName());
//                            Log.i("走不走",codeList.toString()+"我");
//                        }
                        if (list.size() != 0) {
                            stockCheckTaskAdapter.clear();
                            stockCheckTaskAdapter.setDataList(list);

//                            if (list.size() < 100)
//                                needLoad = false;
//                            else
//                                needLoad = true;
//
//                            if (refresh) {
//                                stockCheckTaskAdapter.clear();
//                                stockCheckTaskAdapter.setDataList(list);
//                            } else {
//                                stockCheckTaskAdapter.appendList(list);
//                            }

//                        stockCheckTaskAdapter.setDataList(list);
//                            stockCheckTaskAdapter.clear();
//                            stockCheckTaskAdapter.appendList(list);
                        } else {
                            Toast.makeText(StockCheckTaskActivity.this, "没有该货位", Toast.LENGTH_LONG).show();
                        }
                        mrefresh.endRefreshing();
                        mrefresh.endLoadingMore();
                        stopProgressDialog();
                    } catch (Exception e) {
                        // TODO: handle exception
                        mrefresh.endRefreshing();
                        mrefresh.endLoadingMore();
                        e.printStackTrace();
                    }
                    stopProgressDialog();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    mrefresh.endRefreshing();
                    mrefresh.endLoadingMore();
                    stopProgressDialog();
                }

            }, handler);
            mRequestQueue.add(request);

//            if (codeList.contains(etSearchCode.getText().toString())) {
//                Log.i("走不走",etSearchCode.getText().toString()+"是");
//                detailsNew.clear();
//                for (int i = 0; i < stockchecktaskmodel.getDetails().size(); i++) {
//                    if (stockchecktaskmodel.getDetails().get(i).getGoodsPosName().equals(etSearchCode.getText().toString())) {
//                        detailsNew.add(stockchecktaskmodel.getDetails().get(i));
//                    }
//                }
//                stockchecktaskmodel.setDetails(detailsNew);
//            } else {
//                Toast.makeText(StockCheckTaskActivity.this, "没有该货位", Toast.LENGTH_LONG).show();
//            }
        }
        etSearchCode.setText("");
    }

    //请求数据

    private void initData(int page) {
//        List<StockCheckTaskModel.DetailsBean> list = new ArrayList<StockCheckTaskModel.DetailsBean>();
//        StockCheckTaskModel.DetailsBean databean = new StockCheckTaskModel.DetailsBean();
//        for (int i = 0; i < 10; i++) {
//            databean.setGoodsAreaName("项目" + i);
//            databean.setGoodsPosCode("位置" + i);
//            databean.setGoodsPosName("位置名字" + i);
//            databean.setStockCheckTaskCode("项目编号" + i);
//            databean.setStockCheckTaskCreateDate("时间" + i);
//            list.add(databean);
//        }
//        stockCheckTaskAdapter = new StockCheckTaskAdapter(getApplicationContext());
//        stockCheckTaskList.setAdapter(stockCheckTaskAdapter);
//        stockCheckTaskAdapter.appendList(list);

//
        startProgressDialog("Loading...");

//        JSONStringer jsonObject = new JSONStringer();
//        RequestQueue mRequestQueue = Volley.newRequestQueue(StockCheckTaskActivity.this);
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,"http://116.236.124.150:7077/STMS.ServiceHost/stms_api.svc/getCategory",jsonObject.toString(),
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i("走不走",response.toString()+"111111");
////                        data = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), CategoryListData.class);
////                        if(data.getRespStatus().isIsSuccess()){
////                            for (int i = 0; i <data.getRespData().size() ; i++) {
////                                serviceTypes.add(data.getRespData().get(i));
////                            }
////
////                        }else{
////                            Log.i("走不走","23232323");
////                            Toast.makeText(context, data.getRespStatus().getMessage().toString(), Toast.LENGTH_LONG).show();
////                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("走不走",error.toString()+"222222");
////                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
//            }
//        },handler);
//        mRequestQueue.add(request);

        JSONStringer jsonStr = new JSONStringer();
        String url = Constants.url_StockCheckTaskByUserCode + "?stockCode=" + preferences.getString("StockCode", "")
                + "&userCode=" + preferences.getString("code", "") + "&pagesize=100" + "&pageindex=" + page;
        Log.i("走不走",url+"");
        RequestQueue mRequestQueue = Volley.newRequestQueue(StockCheckTaskActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Method.GET, url, jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("走不走",222222+"");
                // TODO Auto-generated method stub
                try {
                    stockchecktaskmodel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), StockCheckTaskModel.class);
                    list = stockchecktaskmodel.getDetails();
                    for (int i = 0; i < list.size(); i++) {
                        codeList.add(list.get(i).getGoodsPosName());
                    }
                    if (list.size() != 0) {
                        if (list.size() < 100)
                            needLoad = false;
                        else
                            needLoad = true;

                        if (refresh) {

                            stockCheckTaskAdapter.clear();
                            stockCheckTaskAdapter.setDataList(list);
                        } else {

                            stockCheckTaskAdapter.appendList(list);
                        }

//                        stockCheckTaskAdapter.setDataList(list);
//                            stockCheckTaskAdapter.clear();
//                            stockCheckTaskAdapter.appendList(list);
                    }
                    mrefresh.endRefreshing();
                    mrefresh.endLoadingMore();
                    stopProgressDialog();
                } catch (Exception e) {
                    // TODO: handle exception
                    mrefresh.endRefreshing();
                    mrefresh.endLoadingMore();
                    e.printStackTrace();
                }
                stopProgressDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("走不走",error+"1111111");
                mrefresh.endRefreshing();
                mrefresh.endLoadingMore();
                stopProgressDialog();
            }

        }, handler);
        mRequestQueue.add(request);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!isSearch) {
            page = 1;
            refresh = true;
            initData(page);
        } else {
            mrefresh.endRefreshing();
            mrefresh.endLoadingMore();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!isSearch) {
            page++;
        }
        if(!isSearch){
            if(needLoad){
                refresh = false;
                initData(page);
            }
            return true;
        }else{
            mrefresh.endRefreshing();
            mrefresh.endLoadingMore();
            return false;
        }
    }
}
