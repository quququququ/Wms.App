package com.wms.newwmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.wms.newwmsapp.adapter.DownToOtherAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.BaseModel;
import com.wms.newwmsapp.model.DownToOtherModel;
import com.wms.newwmsapp.model.PostDownGoodsModel;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2018/7/4.
 */

public class PostDownToOtherActivity extends BaseActivity implements DownToOtherAdapter.DownToOtherListener {
    public static final String GOOD_POST_NAME = "GoodsPosName";
    public static final String GOOD_POS_CODE = "GoodsPosCode";
    private String goodPosCode;
    private TextView tvPostName;
    private ImageView back;
    private EditText goodsPosCode;
    private DownToOtherModel downToOtherModel;
    private DownToOtherAdapter mAdapter;
    private ListView lvDownToOther;
    private Button btnList, btnToSubmit;
    private List<DownToOtherModel.DataBean> downDataList;
    private List<DownToOtherModel.DataBean> upDataList = new ArrayList<DownToOtherModel.DataBean>();
    private boolean hasList = false;
    private List<PostDownGoodsModel> postDataList = new ArrayList<PostDownGoodsModel>();
    private BaseModel baseModel;
    private Button btnSure;
    private String strInput = "";
    private MyChooseToastDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_down_to_other);
        goodPosCode = getIntent().getStringExtra(GOOD_POS_CODE);
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

        lvDownToOther = (ListView) findViewById(R.id.lv_up_to_other);
        mAdapter = new DownToOtherAdapter(this);
        mAdapter.setOnIPostPackageNoListener(this);
        lvDownToOther.setAdapter(mAdapter);

        tvPostName = (TextView) findViewById(R.id.GoodsposName);
        tvPostName.setText(getIntent().getStringExtra(GOOD_POST_NAME));

        downToOtherModel = (DownToOtherModel) getIntent().getSerializableExtra("user");

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

        btnList = (Button) findViewById(R.id.btn_list);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < upDataList.size(); i++) {
                    if (Double.parseDouble(upDataList.get(i).getUpNo()) != 0.0) {
                        hasList = true;

                    }
                }

                if (hasList) {
                    Intent intent = new Intent(PostDownToOtherActivity.this, HasDownListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("postuser", (Serializable) upDataList);//序列化
                    intent.putExtras(bundle);//发送数据
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(PostDownToOtherActivity.this, "没有移库商品", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        btnToSubmit = (Button) findViewById(R.id.btn_to_submit);
        btnToSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = MyToast.showChooseDialog(PostDownToOtherActivity.this, "是否确认提交？",
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

        if (null != upDataList && upDataList.size() != 0) {
            for (int i = 0; i < upDataList.size(); i++) {
                if (!TextUtils.isEmpty(upDataList.get(i).getUpNo())) {
                    canPost = true;
                }
            }
        } else {
            stopProgressDialog();
            MyToast.showDialog(this, "没有选择下架的商品");
            return;
        }
        if (canPost) {

            for (int i = 0; i < upDataList.size(); i++) {
                if (!TextUtils.isEmpty((upDataList.get(i).getUpNo()))) {
                    PostDownGoodsModel postDownGoodsModel = new PostDownGoodsModel();
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
                    if (null != upDataList.get(i).getQualityCode())
                        postDownGoodsModel.setQualityCode(upDataList.get(i).getQualityCode());
                    else
                        postDownGoodsModel.setQualityCode("");
                    if (null != upDataList.get(i).getUpNo())
                        postDownGoodsModel.setStockTransferGetOutNum(upDataList.get(i).getUpNo());
                    else
                        postDownGoodsModel.setStockTransferGetOutNum("");
                    if (null != upDataList.get(i).getProductionDate())
                        postDownGoodsModel.setProductionDate(upDataList.get(i).getProductionDate());
                    else
                        postDownGoodsModel.setProductionDate("");
                    postDataList.add(postDownGoodsModel);
                }
            }
        } else {
            stopProgressDialog();
            MyToast.showDialog(this, "没有选择下架的商品");
            return;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Data", postDataList);
        params.put("StockCode", stockCode);
        params.put("GoodsPosCode", goodPosCode);
        params.put("UserName", preferences.getString("name", ""));

        String json = JSON.toJSONString(params);
        String url = Constants.url_GetStockTransferGetOutSubmit;

        RequestQueue mRequestQueue = Volley.newRequestQueue(PostDownToOtherActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                json.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    baseModel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), BaseModel.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(PostDownToOtherActivity.this, "下架成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        MyToast.showDialog(PostDownToOtherActivity.this, baseModel.getErrorMessage().toString());
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

        boolean canShow = false;
        downDataList = new ArrayList<DownToOtherModel.DataBean>();
        for (int i = 0; i < downToOtherModel.getData().size(); i++) {
            if (!TextUtils.isEmpty(downToOtherModel.getData().get(i).getBarCode()) && downToOtherModel.getData().get(i).getBarCode().equals(goodsPosCode.getText().toString().trim())) {
                downDataList.add(downToOtherModel.getData().get(i));
                canShow = true;
            }
        }

        if (canShow) {
            for (int i = 0; i < downDataList.size(); i++) {
                downDataList.get(i).setOriginNo(Double.parseDouble(downDataList.get(i).getNum()));
            }
            mAdapter.setData(downDataList, goodsPosCode.getText().toString().trim());

        } else {
            MyToast.showDialog(this, "该商品条码没有匹配数据");
            goodsPosCode.setText("");
            goodsPosCode.setFocusable(true);
            return;
        }


    }

    @Override
    public void deletePost(DownToOtherModel.DataBean dataBean) {
//        downDataList.get(position).setNum(String.valueOf(Double.parseDouble(downDataList.get(position).getNum()) - Double.parseDouble(num)));
//        Log.i("走不走", String.valueOf(downDataList.get(position).getOriginNo() - Double.parseDouble(downDataList.get(position).getNum())));
//        downDataList.get(position).setUpNo(String.valueOf(downDataList.get(position).getOriginNo() - Double.parseDouble(downDataList.get(position).getNum())));

        upDataList.add(dataBean);
        mAdapter.clearData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {
            if (requestCode == 1) {
                upDataList = (List<DownToOtherModel.DataBean>) data.getSerializableExtra("deleteNum");
//                mAdapter.setData(downDataList,goodsPosCode.getText().toString().trim());
            }
        }
    }
}
