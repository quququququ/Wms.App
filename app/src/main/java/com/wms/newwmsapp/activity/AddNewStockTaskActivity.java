package com.wms.newwmsapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.SearchResultAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.AddNewStockTaskModle;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by qupengcheng on 2018/1/11.
 */

public class AddNewStockTaskActivity extends BaseActivity {
    public static final String SEARCH_CODE = "search.code";
    public static final String CUST_GOODS_CODE = "cust.goods.code";
    public static final String STOCK_CHECKTASK_CODE = "stock.checktask.code";
    public static final String STOCK_GOODSPOS_CODE = "stock.goodspos.code";
    private ImageView back;
    private EditText etInputTiaoma, etInputName, etInputCode;
    private SearchResultAdapter searchResultAdapter;
    private Button addToSearch;
    private String custGoodsCode;
    private AddNewStockTaskModle taskDetailModel;
    private ListView pull_stock_check_task;
    private String checkTaskCode, goodsPosCode;
    public static AddNewStockTaskActivity addNewStockTaskActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_code);

        addNewStockTaskActivity = this;
        checkTaskCode = getIntent().getStringExtra(STOCK_CHECKTASK_CODE);
        goodsPosCode = getIntent().getStringExtra(STOCK_GOODSPOS_CODE);
        custGoodsCode = getIntent().getStringExtra(CUST_GOODS_CODE);
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

        etInputTiaoma = (EditText) findViewById(R.id.et_input_tiaoma);
        etInputTiaoma.setText(getIntent().getStringExtra(SEARCH_CODE));
        addToSearch = (Button) findViewById(R.id.add_to_search);
        etInputName = (EditText) findViewById(R.id.et_input_name);
        etInputCode = (EditText) findViewById(R.id.et_input_code);
        pull_stock_check_task = (ListView) findViewById(R.id.pull_stock_check_task);
        pull_stock_check_task.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        addToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etInputTiaoma.getText().toString().trim()) && TextUtils.isEmpty(etInputName.getText().toString().trim())
                        && TextUtils.isEmpty(etInputCode.getText().toString().trim())) {
                    Toast.makeText(AddNewStockTaskActivity.this, "请输入名称或者编码或者条码", Toast.LENGTH_LONG).show();
                } else {
                    searchGoodsList(custGoodsCode, etInputName.getText().toString().trim(), etInputTiaoma.getText().toString().trim(), etInputCode.getText().toString().trim());
                }
            }
        });


        etInputName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etInputName.getText().toString().trim())) {
                    Toast.makeText(AddNewStockTaskActivity.this, "请输入名称", Toast.LENGTH_LONG).show();
                } else {
                    searchGoodsList(custGoodsCode, etInputName.getText().toString().trim(), etInputTiaoma.getText().toString().trim(), etInputCode.getText().toString().trim());
                }
            }
        });

        etInputCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etInputCode.getText().toString().trim())) {
                    Toast.makeText(AddNewStockTaskActivity.this, "请输入编码", Toast.LENGTH_LONG).show();
                } else {
                    searchGoodsList(custGoodsCode, etInputName.getText().toString().trim(), etInputTiaoma.getText().toString().trim(), etInputCode.getText().toString().trim());
                }
            }
        });

        etInputTiaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etInputTiaoma.getText().toString().trim())) {
                    Toast.makeText(AddNewStockTaskActivity.this, "请输入条码", Toast.LENGTH_LONG).show();
                } else {
                    searchGoodsList(custGoodsCode, etInputName.getText().toString().trim(), etInputTiaoma.getText().toString().trim(), etInputCode.getText().toString().trim());
                }
            }
        });

        etInputTiaoma.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (TextUtils.isEmpty(etInputTiaoma.getText().toString().trim())) {
                        Toast.makeText(AddNewStockTaskActivity.this, "请输入条码", Toast.LENGTH_LONG).show();
                    } else {
                        searchGoodsList(custGoodsCode, etInputName.getText().toString().trim(), etInputTiaoma.getText().toString().trim(), etInputCode.getText().toString().trim());
                    }
                    etInputTiaoma.setText("");
                    return true;
                }
                return true;
            }
        });

        etInputCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (TextUtils.isEmpty(etInputCode.getText().toString().trim())) {
                        Toast.makeText(AddNewStockTaskActivity.this, "请输入编码", Toast.LENGTH_LONG).show();
                    } else {
                        searchGoodsList(custGoodsCode, etInputName.getText().toString().trim(), etInputTiaoma.getText().toString().trim(), etInputCode.getText().toString().trim());
                    }
                    etInputCode.setText("");
                    return true;
                }
                return true;
            }
        });

        etInputName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (TextUtils.isEmpty(etInputName.getText().toString().trim())) {
                        Toast.makeText(AddNewStockTaskActivity.this, "请输入名称", Toast.LENGTH_LONG).show();
                    } else {
                        searchGoodsList(custGoodsCode, etInputName.getText().toString().trim(), etInputTiaoma.getText().toString().trim(), etInputCode.getText().toString().trim());
                    }
                    etInputName.setText("");
                    return true;
                }
                return true;
            }
        });
    }

    private void searchGoodsList(final String custGoodsCode, String goodsName, String barCode, String purchaseNo) {
        startProgressDialog("Loading...");
        JSONStringer jsonStr = null;
//        {"Code":null,"InstockDate":null,"PurchaseNo":"03030045","StockCode":"BAJ","InstockCode":null,
//                "BarCode":null,"InstockEndDate":null,"CustGoodsCode":"BAJ001","GoodsName":null}
        try {
            if (TextUtils.isEmpty(goodsName)) {
                goodsName = null;
            }
            if (TextUtils.isEmpty(barCode)) {
                barCode = null;
            }
            if (TextUtils.isEmpty(purchaseNo)) {
                purchaseNo = null;
            }
            jsonStr = new JSONStringer().object()
                    .key("Code").value(null)
                    .key("InstockDate").value(null)
                    .key("PurchaseNo").value(purchaseNo) //商品编码
                    .key("StockCode").value(preferences.getString("StockCode", ""))
                    .key("InstockCode").value(null)
                    .key("BarCode").value(barCode) //商品条码
                    .key("InstockEndDate").value(null)
                    .key("CustGoodsCode").value(custGoodsCode)
                    .key("GoodsName").value(goodsName) //商品名称
                    .endObject();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final RequestQueue mRequestQueue = Volley.newRequestQueue(AddNewStockTaskActivity.this);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                Constants.url_GetGoodsInfoByParam, jsonStr.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        taskDetailModel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), AddNewStockTaskModle.class);
                        if (taskDetailModel.isIsSuccess()) {
                            searchResultAdapter = new SearchResultAdapter(AddNewStockTaskActivity.this);
                            if (taskDetailModel.getDetails().size() > 0 && taskDetailModel.getDetails() != null) {
                                searchResultAdapter.setData(taskDetailModel.getDetails().get(0).getGoodsUnit(), taskDetailModel.getDetails().get(0).getName(),
                                        taskDetailModel.getDetails().get(0).getCode(), custGoodsCode, checkTaskCode, goodsPosCode
                                        , taskDetailModel.getDetails().get(0).getBarCode(), taskDetailModel.getDetails().get(0).getCustomerCode());

                                pull_stock_check_task.setAdapter(searchResultAdapter);
//                                MyToast.showDialog(AddNewStockTaskActivity.this, "操作成功！");
                            } else {
                                MyToast.showDialog(AddNewStockTaskActivity.this, "没有结果！");
                            }

                        } else {
                            MyToast.showDialog(AddNewStockTaskActivity.this, taskDetailModel.getErrorMessage());
                        }

                        stopProgressDialog();
//                        Intent intent= new Intent();
//                        intent.setClass(AddNewStockTaskActivity.this, OutStockConfirmActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopProgressDialog();
            }
        }, handler);
        mRequestQueue.add(request);
    }


}
