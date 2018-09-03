package com.wms.newwmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.UpToOtherAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.BaseModel;
import com.wms.newwmsapp.model.UpDownGoodsModel;
import com.wms.newwmsapp.model.UpToOtherListModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyChooseToastDialog;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2018/7/18.
 */

public class UpToOtherDetailActivity extends BaseActivity implements UpToOtherAdapter.DownToOtherListener {
    public static final String GOOD_POST_NAME = "GoodsPosName";
    public static final String GOOD_POST_CODE = "GoodsPosCode";
    private TextView tvPostName;
    private ImageView back;
    private EditText goodsPosCode;

    private UpToOtherListModel modelNew;
    private UpToOtherAdapter mAdapter;
    private ListView lvUpToOther;
    private Button btnList, btnToSubmit;
    private List<UpToOtherListModel.DataBean> upDataList = new ArrayList<UpToOtherListModel.DataBean>();
    private List<UpDownGoodsModel> postDataList = new ArrayList<UpDownGoodsModel>();
    private boolean hasList;
    private BaseModel baseModel;
    private UpToOtherListModel model;
    private Button btnSure;
    private MyChooseToastDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_upto_other);

        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        tvPostName = (TextView) findViewById(R.id.GoodsposName);
        tvPostName.setText(getIntent().getStringExtra(GOOD_POST_NAME));

        goodsPosCode = (EditText) findViewById(R.id.GoodsposCode);
        goodsPosCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    goNext();
                    goodsPosCode.setText("");
                    goodsPosCode.setFocusable(true);
                    return true;
                }
                return true;
            }
        });

        mAdapter = new UpToOtherAdapter(this);
        lvUpToOther = (ListView) findViewById(R.id.lv_up_to_other);
        lvUpToOther.setAdapter(mAdapter);
        mAdapter.setOnIPostPackageNoListener(this);

        btnList = (Button) findViewById(R.id.btn_list);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (upDataList.size() == 0) {
                    Toast.makeText(UpToOtherDetailActivity.this, "没有移库商品", Toast.LENGTH_LONG).show();
                    return;
                }
                for (int i = 0; i < upDataList.size(); i++) {

                    if (!TextUtils.isEmpty(upDataList.get(i).getUpNo())) {
                        hasList = true;
                    }
                }

                if (hasList) {
                    Intent intent = new Intent(UpToOtherDetailActivity.this, HasUpListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("upData", (Serializable) upDataList);//序列化
                    intent.putExtras(bundle);//发送数据
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(UpToOtherDetailActivity.this, "没有移库商品", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        btnToSubmit = (Button) findViewById(R.id.btn_to_submit);
        btnToSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = MyToast.showChooseDialog(UpToOtherDetailActivity.this, "是否确认提交？",
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                try {
                                    toSubmit();
                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    progressDialog.dismiss();

                                }

                            }
                        });
            }
        });

        btnSure = (Button) findViewById(R.id.btn_to_sure);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
                goodsPosCode.setText("");
                goodsPosCode.setFocusable(true);
            }
        });
    }

    private void toSubmit() {
        startProgressDialog("Loading...");

        //判断是否有提交下架的数据
        boolean canPost = false;

        if (upDataList.size() != 0) {
            for (int i = 0; i < upDataList.size(); i++) {
                if (!TextUtils.isEmpty(upDataList.get(i).getUpNo())) {
                    canPost = true;
                }
            }
        } else {
            stopProgressDialog();
            MyToast.showDialog(this, "没有选择上架的商品");
            return;
        }
        if (canPost) {

            for (int i = 0; i < upDataList.size(); i++) {
                if (!TextUtils.isEmpty((upDataList.get(i).getUpNo()))) {
                    UpDownGoodsModel postDownGoodsModel = new UpDownGoodsModel();
                    if (null != upDataList.get(i).getBoxId())
                        postDownGoodsModel.setBoxId(upDataList.get(i).getBoxId());
                    else
                        postDownGoodsModel.setBoxId("");
                    if (null != upDataList.get(i).getCustGoodsCode())
                        postDownGoodsModel.setCustGoodsCode(upDataList.get(i).getCustGoodsCode());
                    else
                        postDownGoodsModel.setCustGoodsCode("");
                    if (null != upDataList.get(i).getGoodsCode())
                        postDownGoodsModel.setGoodsCode(upDataList.get(i).getGoodsCode());
                    else
                        postDownGoodsModel.setGoodsCode("");
                    if (null != upDataList.get(i).getGoodsPosCode())
                        postDownGoodsModel.setGoodsPosCode(upDataList.get(i).getGoodsPosCode());
                    else
                        postDownGoodsModel.setGoodsPosCode("");
                    if (null != upDataList.get(i).getGoodsUnitCode())
                        postDownGoodsModel.setGoodsUnitCode(upDataList.get(i).getGoodsUnitCode());
                    else
                        postDownGoodsModel.setGoodsUnitCode("");
                    if (null != upDataList.get(i).getProductId())
                        postDownGoodsModel.setProductId(upDataList.get(i).getProductId());
                    else
                        postDownGoodsModel.setProductId("");
                    if (null != upDataList.get(i).getProductionDate())
                        postDownGoodsModel.setProductionDate(upDataList.get(i).getProductionDate());
                    else
                        postDownGoodsModel.setProductionDate("");
                    if (null != upDataList.get(i).getQualityCode())
                        postDownGoodsModel.setQualityCode(upDataList.get(i).getQualityCode());
                    else
                        postDownGoodsModel.setQualityCode("");
                    if (null != upDataList.get(i).getUpNo())
                        postDownGoodsModel.setStockTransferGetOutNum(upDataList.get(i).getUpNo());
                    else
                        postDownGoodsModel.setStockTransferGetOutNum("");
                    postDataList.add(postDownGoodsModel);
                }

            }
        } else {
            stopProgressDialog();
            MyToast.showDialog(this, "没有选择上架的商品");
            return;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Detail", postDataList);
        params.put("StockCode", stockCode);
        params.put("UserName", preferences.getString("name", ""));
        params.put("PosCode", getIntent().getStringExtra(GOOD_POST_CODE));
        String json = JSON.toJSONString(params);

        String url = Constants.url_GetStockTransferPutAway;
        RequestQueue mRequestQueue = Volley.newRequestQueue(UpToOtherDetailActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                json.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    baseModel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), BaseModel.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(UpToOtherDetailActivity.this, "上架成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        MyToast.showDialog(UpToOtherDetailActivity.this, baseModel.getErrorMessage().toString());
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

    private void goNext() {
        if (TextUtils.isEmpty(goodsPosCode.getText().toString().trim())) {
            MyToast.showDialog(this, "请输入或扫描商品条码");
            return;
        }

        startProgressDialog("Loading...");
        String url = Constants.url_GetStockTransferPutAwayCheck + "stockCode=" + stockCode + "&barCode=" + goodsPosCode.getText().toString().trim();
        JSONStringer jsonStr = new JSONStringer();

        RequestQueue mRequestQueue = Volley.newRequestQueue(UpToOtherDetailActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    model = JSON.parseObject(response.toString(), UpToOtherListModel.class);
                    if (model.isIsSuccess()) {
                        mAdapter.clearData();
                        mAdapter.setData(model.getData());
                    } else {
                        MyToast.showDialog(UpToOtherDetailActivity.this, model.getErrorMessage());
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
                goodsPosCode.setText("");
                goodsPosCode.setFocusable(true);
            }
        }, handler);
        mRequestQueue.add(request);
    }

    @Override
    public void deletePost(UpToOtherListModel.DataBean dataBean) {
        upDataList.add(dataBean);
        mAdapter.clearData();
//        upDataList.get(position).setNum(String.valueOf(Integer.parseInt(upDataList.get(position).getNum()) - Integer.parseInt(num)));
//        Log.i("走不走", String.valueOf(upDataList.get(position).getOriginNo() - Integer.parseInt(upDataList.get(position).getNum())));
//        upDataList.get(position).setUpNo(String.valueOf(upDataList.get(position).getOriginNo() - Integer.parseInt(upDataList.get(position).getNum())));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {
            if (requestCode == 1) {
                upDataList = (List<UpToOtherListModel.DataBean>) data.getSerializableExtra("deleteNum");
            }
        }
    }

}
