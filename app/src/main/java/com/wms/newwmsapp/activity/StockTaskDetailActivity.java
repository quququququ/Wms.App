package com.wms.newwmsapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.QualityAdapter;
import com.wms.newwmsapp.adapter.StockDetailAdapter;
import com.wms.newwmsapp.adapter.UnitAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.InstockDetailModel;
import com.wms.newwmsapp.model.QualityMode;
import com.wms.newwmsapp.model.StockTaskDetailModel;
import com.wms.newwmsapp.model.TaskDetailModel;
import com.wms.newwmsapp.model.UnitModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.DateUtil;
import com.wms.newwmsapp.tool.JSONHelper;
import com.wms.newwmsapp.tool.JsonDateFormate;
import com.wms.newwmsapp.tool.MyListView;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by qupengcheng on 2018/1/10.
 */

public class StockTaskDetailActivity extends BaseActivity {
    public static final String STOCK_CHECKTASK_CODE = "stock.checktask.code";
    public static final String STOCK_GOODSPOS_CODE = "stock.goodspos.code";
    public static final String NEED_ADD_ITEM = "need.add.item";
    public static final String STOCK_GOODSPOS_NAME = "stock.goodspos.name";
    private String checkTaskCode, goodsPosCode;
    private MyListView lvTaskDetail;
    private StockDetailAdapter stockDetailAdapter;
    private ImageView back;
    private EditText etSearchCode;
    private Button searchOneCode, allList;
    private List<String> codeList = new ArrayList<String>();
    private List<TaskDetailModel.DetailsBean> details = new ArrayList<TaskDetailModel.DetailsBean>();
    private List<TaskDetailModel.DetailsBean> detailsNew = new ArrayList<TaskDetailModel.DetailsBean>();
    private TaskDetailModel taskDetailModel = new TaskDetailModel();
    private boolean needAdd;
    private Button btnToAdd;
    private List<QualityMode> qualitys;
    private List<UnitModel> units;
    private Button btn_to_commit;
    private List<StockTaskDetailModel> stockTaskDetailmodel = new ArrayList<StockTaskDetailModel>();
    private String QualityCode, QualityName, GoodsUnitCode;
    private TextView tvPanNum;
    private EditText tvProductId;
    private TextView tvProductTime;
    private List<StockTaskDetailModel> stockTaskDetailModel = new ArrayList<StockTaskDetailModel>();
    public static StockTaskDetailActivity stockTaskDetailActivity;
    private BroadcastReceiver mReceiver;
    private ScrollView add_sv_layout;
    private TextView stock_goodspos_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        stockTaskDetailActivity = this;
        checkTaskCode = getIntent().getStringExtra(STOCK_CHECKTASK_CODE);
        goodsPosCode = getIntent().getStringExtra(STOCK_GOODSPOS_CODE);
        needAdd = getIntent().getBooleanExtra(NEED_ADD_ITEM, false);

        try {
            initView();
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        stock_goodspos_name = (TextView) findViewById(R.id.stock_goodspos_name);
        stock_goodspos_name.setText("货位号："+getIntent().getStringExtra(STOCK_GOODSPOS_NAME));
        add_sv_layout = (ScrollView) findViewById(R.id.add_sv_layout);
        lvTaskDetail = (MyListView) findViewById(R.id.lv_task_detail);
        stockDetailAdapter = new StockDetailAdapter(StockTaskDetailActivity.this);

        lvTaskDetail.setAdapter(stockDetailAdapter);
        btn_to_commit = (Button) findViewById(R.id.btn_to_commit);
        back = (ImageView) findViewById(R.id.back);

        etSearchCode = (EditText) findViewById(R.id.et_search_code);
        searchOneCode = (Button) findViewById(R.id.search_one_code);
        allList = (Button) findViewById(R.id.all_list);
        btnToAdd = (Button) findViewById(R.id.btn_to_add);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        searchOneCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch();
            }
        });

        allList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockDetailAdapter.setData(details);
            }
        });

        btnToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StockTaskDetailActivity.this, AddNewStockTaskActivity.class);
                intent.putExtra(AddNewStockTaskActivity.STOCK_CHECKTASK_CODE, checkTaskCode);
                intent.putExtra(AddNewStockTaskActivity.STOCK_GOODSPOS_CODE, goodsPosCode);
                startActivity(intent);
            }
        });


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

//        etSearchCode.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
//                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
//                    //处理事件
//                    goSearch();
//                    return true;
//                }
//                return false;
//            }
//        });
        btn_to_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (needAdd) {
                    if (TextUtils.isEmpty(tvPanNum.getText().toString().trim())) {
                        MyToast.showDialog(StockTaskDetailActivity.this, "请输入盘点数量");
//                    } else if (TextUtils.isEmpty(tvProductId.getText().toString().trim())) {
//                        MyToast.showDialog(StockTaskDetailActivity.this, "批次号");
//                    } else if (TextUtils.isEmpty(tvProductTime.getText().toString().trim())) {
//                        MyToast.showDialog(StockTaskDetailActivity.this, "生产时间");
                    } else {
                        commitGoods();
                    }
                } else {
                    commitGoods();
//                    MyToast.showDialog(StockTaskDetailActivity.this, "还没有增加的商品");
                }
            }
        });

        add_sv_layout.setOnTouchListener(new View.OnTouchListener() {
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
        if (TextUtils.isEmpty(etSearchCode.getText().toString())) {
            Toast.makeText(StockTaskDetailActivity.this, "请输入查询商品条码", Toast.LENGTH_LONG).show();
        } else {
            if (codeList.contains(etSearchCode.getText().toString())) {
                detailsNew.clear();
                for (int i = 0; i < taskDetailModel.getDetails().size(); i++) {
                    if (taskDetailModel.getDetails().get(i).getBarCode().equals(etSearchCode.getText().toString())) {
                        detailsNew.add(taskDetailModel.getDetails().get(i));
                    }
                }
                stockDetailAdapter.setData(detailsNew);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(StockTaskDetailActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否新增商品！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(StockTaskDetailActivity.this, AddNewStockTaskActivity.class);
                        intent.putExtra(AddNewStockTaskActivity.SEARCH_CODE, etSearchCode.getText().toString());
                        intent.putExtra(AddNewStockTaskActivity.STOCK_CHECKTASK_CODE, checkTaskCode);
                        intent.putExtra(AddNewStockTaskActivity.STOCK_GOODSPOS_CODE, goodsPosCode);
                        if (!TextUtils.isEmpty(taskDetailModel.getDetails().get(0).getCustGoodsCode()))
                            intent.putExtra(AddNewStockTaskActivity.CUST_GOODS_CODE, taskDetailModel.getDetails().get(0).getCustGoodsCode());
                        dialogInterface.dismiss();
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        }
    }

    private void showAddItem() {
        final LinearLayout footView = (LinearLayout) findViewById(R.id.add_item);
        footView.setVisibility(View.VISIBLE);

        LinearLayout ll_select_num_quality = (LinearLayout) findViewById(R.id.ll_select_num_quality);
        LinearLayout ll_num = (LinearLayout) findViewById(R.id.ll_num);
        LinearLayout llStockTaskDetail = (LinearLayout) findViewById(R.id.ll_stock_task_detail);
        llStockTaskDetail.setBackgroundResource(R.drawable.picklist_ll_blue_red);
        TextView tvName = (TextView) findViewById(R.id.task_name);
        TextView tvBianma = (TextView) findViewById(R.id.task_bianma);
        TextView tvTiaoma = (TextView) findViewById(R.id.task_tiaoma);
        tvProductId = (EditText) findViewById(R.id.tv_product_id);

        tvProductTime = (TextView) findViewById(R.id.tv_product_time);

        tvProductId.setOnClickListener(new View.OnClickListener() {
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
        TextView tvNum = (TextView) findViewById(R.id.tv_num);
        tvPanNum = (TextView) findViewById(R.id.tv_pan_num);
        tvPanNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View view1 = LayoutInflater.from(StockTaskDetailActivity.this).inflate(R.layout.dialog_customercode, null);
//                LayoutInflater inflater = LayoutInflater.from(StockTaskDetailActivity.this);
//                View view1 = inflater.inflate(R.layout.dialog_customercode, null);

                final AlertDialog ad = new AlertDialog.Builder(StockTaskDetailActivity.this).create();
                ad.setView(view1);
                ad.show();

                final EditText CustomerCode_et = (EditText) view1.findViewById(R.id.CustomerCode);
                TextView textView2 = (TextView) view1.findViewById(R.id.textView2);
                textView2.setText("输入盘点数量");
                Button button_ok = (Button) view1.findViewById(R.id.button_ok);
                button_ok.setText("确定");

                button_ok.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub

                        if (TextUtils.isEmpty(CustomerCode_et.getText().toString().trim())) {
                            Toast.makeText(StockTaskDetailActivity.this, "不能输入为空", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            tvPanNum.setText(CustomerCode_et.getText().toString().trim());
                            ad.dismiss();
                        }

                    }
                });
            }
        });
        ImageView iv_delete_image = (ImageView) findViewById(R.id.iv_delete_image);
        iv_delete_image.setVisibility(View.VISIBLE);
        final TextView tv_danwei = (TextView) footView.findViewById(R.id.tv_danwei);
        Spinner spinnerNum = (Spinner) findViewById(R.id.UnitName);
        Spinner spinnerQuality = (Spinner) findViewById(R.id.QualityName);

        tvName.setText(getIntent().getStringExtra("goodsName"));
        tvBianma.setText(getIntent().getStringExtra("customerCode"));
        tvTiaoma.setText(getIntent().getStringExtra("barCode"));

        ll_select_num_quality.setVisibility(View.VISIBLE);
        ll_num.setVisibility(View.GONE);

        UnitAdapter unitAdapter = new UnitAdapter(getApplicationContext(), true);
        spinnerNum.setAdapter(unitAdapter);
        unitAdapter.appendList(units);
        spinnerNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        QualityAdapter qualityAdapter = new QualityAdapter(getApplicationContext(), true);
        spinnerQuality.setAdapter(qualityAdapter);
        qualityAdapter.appendList(qualitys);
        spinnerQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                QualityCode = qualitys.get(arg2).getCode();
                QualityName = qualitys.get(arg2).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        iv_delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                lvTaskDetail.removeFooterView(footView);
                footView.setVisibility(View.GONE);
                needAdd = false;
            }
        });
//        lvTaskDetail.addFooterView(footView);

        tvProductTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickDlg();
            }
        });
        stopProgressDialog();
    }

    private void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(StockTaskDetailActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int showMonth = monthOfYear + 1;
                StockTaskDetailActivity.this.tvProductTime.setText(year + "-" + showMonth + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void commitGoods() {
        startProgressDialog("Loading...");
        List<TaskDetailModel.DetailsBean> taskDetailList = stockDetailAdapter.upData();
        com.alibaba.fastjson.JSONArray jsonArray = new com.alibaba.fastjson.JSONArray();
        if (needAdd) {
            com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
            jsonObject.put("StockCheckTaskCode", checkTaskCode);
            jsonObject.put("GoodsCode", getIntent().getStringExtra("goodsCode"));
            jsonObject.put("GoodsName", getIntent().getStringExtra("goodsName"));
            jsonObject.put("QualityCode", QualityCode);
            jsonObject.put("ProductId", tvProductId.getText().toString().trim());
            if(!TextUtils.isEmpty(tvProductTime.getText().toString().trim())&&tvProductTime.getText().toString().trim()!=null) {
                jsonObject.put("ProductionDate", tvProductTime.getText().toString().trim());
            }
            jsonObject.put("CustGoodsName", "");
            jsonObject.put("QualityName",QualityName);
            jsonObject.put("Num", tvPanNum.getText().toString().trim());
            jsonObject.put("OldNum", 0);
            jsonObject.put("GoodsUnitCode", getIntent().getStringExtra("Code"));
            jsonArray.add(jsonObject);
        }
        for (int i = 0; i < taskDetailList.size(); i++) {
            com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
            object.put("StockCheckTaskCode", taskDetailList.get(i).getStockCheckTaskCode());
            object.put("GoodsCode", taskDetailList.get(i).getGoodsCode());
            object.put("GoodsName", taskDetailList.get(i).getGoodsName()); //商品名称
            object.put("QualityCode", taskDetailList.get(i).getQualityCode());
            object.put("ProductId", taskDetailList.get(i).getProductId());  //批次号
            if (!TextUtils.isEmpty(taskDetailList.get(i).getProductionDate() + "")&&null != taskDetailList.get(i).getProductionDate()) {
                object.put("ProductionDate", taskDetailList.get(i).getProductionDate() + "");
            }
            object.put("CustGoodsName", "");
            object.put("QualityName", taskDetailList.get(i).getQualityName());
            object.put("Num", taskDetailList.get(i).getNum());
            object.put("OldNum", taskDetailList.get(i).getOldSum());
            object.put("GoodsUnitCode", taskDetailList.get(i).getGoodsUnitCode());
            jsonArray.add(object);
        }
//        Log.i("走不走", jsonArray.toString() + "");
        RequestQueue mRequestQueue = Volley.newRequestQueue(StockTaskDetailActivity.this);
//        Log.i("走不走", "请求url" + Constants.url_PostAuditStockCheckTask + "?stockCheckTaskCode=" + checkTaskCode + "&poscode=" + goodsPosCode
//                + "&userCode=" + preferences.getString("code", "") + "&userName=" + preferences.getString("name", ""));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                Constants.url_PostAuditStockCheckTask + "?stockCheckTaskCode=" + checkTaskCode + "&poscode=" + goodsPosCode
                        + "&userCode=" + preferences.getString("code", "") + "&userName=" + preferences.getString("name", ""), jsonArray.toString(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if (needAdd) {
                            AddNewStockTaskActivity.addNewStockTaskActivity.finish();
                        }
                        StockCheckTaskActivity.stockCheckTaskActivity.finish();
                        Intent intent = new Intent(StockTaskDetailActivity.this, StockCheckTaskActivity.class);
                        startActivity(intent);
                        finish();
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

    private void getQualityList(final String CustomerCode) {
//        startProgressDialog("Loading...");
        JSONStringer jsonStr = null;
        try {
            jsonStr = new JSONStringer().object()
                    .key("instockParams").object()
                    .key("Code").value(CustomerCode)
                    .key("InstockDate").value("")
                    .key("PurchaseNo").value("")
                    .key("StockCode").value(stockCode)
                    .key("BarCode").value("")
                    .key("InstockCode").value("")
                    .endObject().endObject();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestQueue mRequestQueue = Volley.newRequestQueue(StockTaskDetailActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                Constants.url_GetAllQuality, jsonStr.toString(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
                        com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
                        qualitys = com.alibaba.fastjson.JSONArray.parseArray(jsonArray.toString(), QualityMode.class);
                        getUnitList(getIntent().getStringExtra("customerCode"));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopProgressDialog();
            }
        }, handler);
        mRequestQueue.add(request);
    }

    // 获取对应货品可选的单位列表
    private void getUnitList(String CustomerCode) {
//        startProgressDialog("Loading...");
        JSONStringer jsonStr = null;
        try {
            jsonStr = new JSONStringer().object()
                    .key("instockParams").object()
                    .key("Code").value(CustomerCode)
                    .key("InstockDate").value("")
                    .key("PurchaseNo").value("")
                    .key("StockCode").value(stockCode)
                    .key("BarCode").value("")
                    .key("InstockCode").value("")
                    .endObject()
                    .endObject();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestQueue mRequestQueue = Volley.newRequestQueue(StockTaskDetailActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                Constants.url_GetAllUnityByGoodsCode, jsonStr.toString(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
                        com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
                        units = com.alibaba.fastjson.JSONArray.parseArray(jsonArray.toString(), UnitModel.class);
                        showAddItem();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopProgressDialog();
            }
        }, handler);
        mRequestQueue.add(request);
    }

    private void initData() {
        startProgressDialog("Loading...");

        JSONStringer jsonStr = new JSONStringer();
        String url = Constants.url_GetStockCheckTaskByUCodeAndPos + "?stockCheckTaskCode=" + checkTaskCode
                + "&userCode=" + preferences.getString("code", "") + "&poscode=" + goodsPosCode;
        RequestQueue mRequestQueue = Volley.newRequestQueue(StockTaskDetailActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                taskDetailModel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), TaskDetailModel.class);
                if (taskDetailModel.isIsSuccess()) {
                    details = taskDetailModel.getDetails();
                    for (int i = 0; i < details.size(); i++) {
                        codeList.add(details.get(i).getBarCode());
                    }
                    stockDetailAdapter.setData(details);
                } else {
                    MyToast.showDialog(StockTaskDetailActivity.this, taskDetailModel.getErrorMessage());
                }

                if (needAdd) {
                    etSearchCode.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
                    getQualityList(getIntent().getStringExtra("customerCode"));
                    stopProgressDialog();
                } else {
                    stopProgressDialog();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                stopProgressDialog();
            }

        }, handler);
        mRequestQueue.add(request);
    }

}
