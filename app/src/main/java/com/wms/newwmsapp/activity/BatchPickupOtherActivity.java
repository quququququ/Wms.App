package com.wms.newwmsapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.FinishToCarAdapter;
import com.wms.newwmsapp.adapter.WavePickUpDetailAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.PickDetailModel;
import com.wms.newwmsapp.model.WavePickupDetailModel;
import com.wms.newwmsapp.model.WavePickupModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyChooseToastDialog;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.tool.SelectPackageToastDialog;
import com.wms.newwmsapp.tool.SoundUtils;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;
import com.zltd.industry.ScannerManager;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class BatchPickupOtherActivity extends BaseActivity implements FinishToCarAdapter.ICashPaymentPwdListener {

    private ImageView back;
    private WavePickupModel pick;
    private WavePickupDetailModel model = new WavePickupDetailModel();
    private WavePickUpDetailAdapter pickUpadapter;
    private ListView pick_list;
    private List<PickDetailModel> showList = new ArrayList<PickDetailModel>();
    private TextView txtGoodsPosName;
    private TextView Sum;
    private TextView TypeSum;
    private TextView pickUpNum;
    private TextView HavePickUpNum;
    private EditText GoodsposCode;
    private LinearLayout sure_lin;
    private LinearLayout save_lin;
    private MyChooseToastDialog progressDialog;
    private Button btnResetSubmit;
    private Button btnSubmitAgain;
    private String num = "0";

    private SoundUtils mSoundUtils;
    private ScannerManager mScannerManager;
    private boolean isOther = false;
    private SelectPackageToastDialog mDialog;
    private String postStr;
    private Button btnToBind;
    private boolean hasBind;
    private TextView package_no;
    private ListView lvPackageNum;
    private FinishToCarAdapter mAdapter;
    private List<String> strList = new ArrayList<String>();
    private LinearLayout llAll;
    private Button btnSure;
    private String pickCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_pickup_detail);
        pickCode = getIntent().getStringExtra("pickcode");
        btnSure = (Button) findViewById(R.id.btn_sure);
        lvPackageNum = (ListView) findViewById(R.id.package_list);
        mAdapter = new FinishToCarAdapter(this);
        mAdapter.setOnICashPaymentPwdListener(this);
        lvPackageNum.setAdapter(mAdapter);
        llAll = (LinearLayout) findViewById(R.id.ll_all);
        package_no = (EditText) findViewById(R.id.package_no);
        isOther = getIntent().getBooleanExtra("isOther", false);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(BatchPickupOtherActivity.this, PickUpOtherActivity.class);
                intent.putExtra("isOther", true);
                startActivity(intent);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();

        pick = (WavePickupModel) intent.getSerializableExtra("Pick");

        pick_list = (ListView) findViewById(R.id.pick_list);

        if (TextUtils.isEmpty(pickCode))
            GetDetail(pick.getCode());
        else
            GetDetail(pickCode);
        GoodsposCode = (EditText) findViewById(R.id.GoodsposCode);
        txtGoodsPosName = (TextView) findViewById(R.id.GoodsposName);

        Sum = (TextView) findViewById(R.id.Sum);
        TypeSum = (TextView) findViewById(R.id.TypeSum);
        pickUpNum = (TextView) findViewById(R.id.pickUpNum);
        HavePickUpNum = (TextView) findViewById(R.id.HavePickUpNum);
        sure_lin = (LinearLayout) findViewById(R.id.sure_lin);
        save_lin = (LinearLayout) findViewById(R.id.save_lin);

        btnToBind = (Button) findViewById(R.id.btn_to_bind);
        btnToBind.setVisibility(View.VISIBLE);
        btnToBind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                GoodsposCode.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
//                InputMethodManager imm1 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                if (imm1.isActive()) {
//                    imm1.hideSoftInputFromWindow(GoodsposCode.getWindowToken(), 0);// 隐藏输入法
//                }

                //重新扫，清空输入框
                postStr = "";
                mAdapter.clearList();
//                mDialog = SelectPackageToastDialog.createDialog(BatchPickupOtherActivity.this);
//                mDialog.setOnIPostPackageNoListener(BatchPickupOtherActivity.this);

//                mDialog.show();
//                mDialog = MyToast.showSelectPackageDialog(BatchPickupOtherActivity.this, "输入绑定包裹号", new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (mDialog.getStr().size() == 0) {
//                            Toast.makeText(BatchPickupOtherActivity.this, "请输入包裹号", Toast.LENGTH_LONG);
//                            return;
//                        }
//                        for (int i = 0; i < mDialog.getStr().size(); i++) {
//                            postStr = postStr +","+mDialog.getStr().get(i);
//                        }
//
//                        postStr = postStr.substring(1,postStr.length());
//                        package_no.setText(postStr);
//                        hasBind = true;
//                    }
//                });

            }
        });
        package_no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsposCode.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
                package_no.setFocusable(true);//设置输入框可聚集
                package_no.setFocusableInTouchMode(true);//设置触摸聚焦
                package_no.requestFocus();//请求焦点
                package_no.findFocus();//获取焦点
                InputMethodManager imm1 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm1.showSoftInput(GoodsposCode, InputMethodManager.SHOW_FORCED);// 显示输入法


            }
        });
        package_no.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (TextUtils.isEmpty(package_no.getText().toString().trim())) {
                        MyToast.showDialog(BatchPickupOtherActivity.this, "包裹号为空！");
                    } else {
                        strList.add(package_no.getText().toString().trim());
                        mAdapter.setData(strList);
                        package_no.setText("");
                    }
                    return true;
                }
                return true;
            }
        });

        btnSure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(package_no.getText().toString().trim())) {
                    MyToast.showDialog(BatchPickupOtherActivity.this, "包裹号为空！");
                } else {
                    strList.add(package_no.getText().toString().trim());
                    mAdapter.setData(strList);
                    package_no.setText("");
                }
            }
        });
        GoodsposCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                package_no.setFocusable(false);
                GoodsposCode.setFocusable(true);//设置输入框可聚集
                GoodsposCode.setFocusableInTouchMode(true);//设置触摸聚焦
                GoodsposCode.requestFocus();//请求焦点
                GoodsposCode.findFocus();//获取焦点
                InputMethodManager imm1 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm1.showSoftInput(GoodsposCode, InputMethodManager.SHOW_FORCED);// 显示输入法
            }
        });
        GoodsposCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    Scanner();

                    GoodsposCode.setFocusable(true);
                    GoodsposCode.setFocusableInTouchMode(true);
                    GoodsposCode.requestFocus();
                    GoodsposCode.setText("");

                    return true;
                }
                return true;
            }
        });


        sure_lin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Scanner();
            }
        });

        save_lin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (Integer.parseInt(HavePickUpNum.getText().toString()) > Integer
                        .parseInt(showList.get(0).getNum())
                        || Integer.parseInt(HavePickUpNum.getText().toString()) < 0) {
                    MyToast.showDialog(BatchPickupOtherActivity.this,
                            "输入数量有误！");
                    HavePickUpNum.setText(num);
                    return;
                }

                if (IsComplete()) {

                    progressDialog = MyToast.showChooseDialog(
                            BatchPickupOtherActivity.this, "波次分拣完成",
                            new OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    // TODO Auto-generated method stub
                                    try {
//                                        Submit(pick.getCode());
                                        progressDialog.dismiss();
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }


                int jianNum = Integer.parseInt(HavePickUpNum.getText()
                        .toString()) - Integer.parseInt(num);
                num = HavePickUpNum.getText().toString();
                int totalSum = Integer.parseInt(Sum.getText().toString())
                        - jianNum;
                if (totalSum >= 0) {
                    Sum.setText(String.valueOf(totalSum));
                }

                if (HavePickUpNum.getText().toString()
                        .equals(showList.get(0).getNum())) {
                    UpdateIsScan(showList.get(0).getGoodsCode());
                }

            }
        });

        btnResetSubmit = (Button) findViewById(R.id.btnResetSubmit);
        btnResetSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                progressDialog = MyToast.showChooseDialog(
                        BatchPickupOtherActivity.this, "是否重拣？",
                        new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                try {
                                    if (TextUtils.isEmpty(pickCode))
                                        GetDetail(pick.getCode());
                                    else
                                        GetDetail(pickCode);
                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
        });

        btnSubmitAgain = (Button) findViewById(R.id.btnSubmitAgain);
        btnSubmitAgain.setText("提交");
        btnSubmitAgain.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                progressDialog = MyToast.showChooseDialog(
                        BatchPickupOtherActivity.this, "是否提交？",
                        new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                try {
                                    if (TextUtils.isEmpty(pickCode))
                                        Submit(pick.getCode());
                                    else
                                        Submit(pickCode);
                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
        });

    }


    private boolean IsComplete() {
        boolean flag = true;
        for (int i = 0; i < model.Details.size(); i++) {
            if (!model.Details.get(i).isIsScan()) {

                flag = false;
            }
        }

        return flag;
    }

    private void Scanner() {

        if ((showList.get(0).getGoodsBarCode() == null || showList.get(0)
                .getGoodsBarCode().isEmpty())
                || (GoodsposCode.getText() == null || GoodsposCode.getText()
                .toString().isEmpty())) {
            MyToast.showDialog(BatchPickupOtherActivity.this, "条码为空！");
            return;

        }
        if (showList.size() == 0) {
            MyToast.showDialog(BatchPickupOtherActivity.this, "没有商品！");
            return;
        }
        if (IsComplete()) {
            progressDialog = MyToast.showChooseDialog(
                    BatchPickupOtherActivity.this, "波次分拣完成",
                    new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            try {
//                                Submit(pick.getCode());
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                // TODO: handle exception
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
        if (showList.get(0).getGoodsBarCode()
                .equals(GoodsposCode.getText().toString())) {

            HavePickUpNum.setText(String.valueOf((Integer
                    .parseInt(HavePickUpNum.getText().toString()) + 1)));
            num = String.valueOf((Integer.parseInt(num) + 1));
            Sum.setText(String.valueOf(Integer.parseInt(Sum.getText()
                    .toString()) - 1));

            if (HavePickUpNum.getText().toString()
                    .equals(showList.get(0).getNum())) {
                UpdateIsScan(showList.get(0).getGoodsCode());

            }

        } else {
            MyToast.showDialog(BatchPickupOtherActivity.this, "该商品已完成或不存在！");
        }

    }

    private void UpdateIsScan(String GoodsCode) {

        boolean flag = true;
        boolean showListIsAdd = false;
        boolean IsScanIsUpdate = false;

        List<String> liType = new ArrayList<String>();

        for (int i = 0; i < model.Details.size(); i++) {

            if (model.Details.get(i).GoodsCode.equals(GoodsCode) && !model.Details.get(i).isIsScan() && !IsScanIsUpdate) {

                model.Details.get(i).setIsScan(true);

                IsScanIsUpdate = true;
            }

            if (!model.Details.get(i).isIsScan()) {
                flag = false;
            }

            if (!model.Details.get(i).isIsScan() && !showListIsAdd) {

                showList.set(0, model.Details.get(i));
                showListIsAdd = true;
            }

            if (!model.Details.get(i).isIsScan()) {
                if (!liType.contains(model.Details.get(i).getGoodsCustomerCode())) {
                    liType.add(model.Details.get(i).getGoodsCustomerCode());
                }
            }
        }

        pickUpNum.setText(showList.get(0).getNum());
        InitGoodsPos();
        TypeSum.setText(String.valueOf(liType.size()));

        if (flag) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BatchPickupOtherActivity.this);
            builder.setTitle("波次分拣完成!");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
//            progressDialog = MyToast.showChooseDialog(
//                    BatchPickupOtherActivity.this, "波次分拣完成",
//                    new OnClickListener() {
//
//                        @Override
//                        public void onClick(View arg0) {
//                            // TODO Auto-generated method stub
//                            try {
////                                Submit(pick.getCode());
//                                progressDialog.dismiss();
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                                progressDialog.dismiss();
//                            }
//                        }
//                    });
        }
        HavePickUpNum.setText("0");
        num = "0";
        GoodsposCode.setText("");
        pickUpadapter.clear();
        pickUpadapter.appendList(showList);
    }

    private void Submit(String pickCode) {

//        if (!hasBind) {
//            Toast.makeText(BatchPickupOtherActivity.this, "请先绑定包裹号", Toast.LENGTH_LONG).show();
//            return;
//        }
        boolean isComplete = true;
        for (int i = 0; i < model.Details.size(); i++) {
            if (!model.Details.get(i).isIsScan()) {
                isComplete = false;
            }

        }
        if (!isComplete) {
            MyToast.showDialog(BatchPickupOtherActivity.this,
                    "还有" + Sum.getText() + "货品\n" + TypeSum.getText()
                            + "种类\n作业没有完成！");
            return;
        }


        //如果是转仓分拣
        if (isOther) {

            if (mAdapter.getList().size() == 0) {
                Toast.makeText(BatchPickupOtherActivity.this, "请先绑定包裹号", Toast.LENGTH_LONG).show();
                return;
            }

            postStr = "";
            for (int i = 0; i < mAdapter.getList().size(); i++) {
                postStr = postStr + "," + strList.get(i);
            }
            postStr = postStr.substring(1, postStr.length());
            startProgressDialog("Loading...");
            String url = Constants.url_SubmitWavePickupConfirm
                    + "?wavepickconfirmCode=" + pickCode + "&packageCode=" + postStr + "&userName=" + preferences.getString("name", "");
            JSONStringer jsonStr = new JSONStringer();
            RequestQueue mRequestQueue = Volley
                    .newRequestQueue(BatchPickupOtherActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
                    jsonStr.toString(), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        com.alibaba.fastjson.JSONObject jsonObject = JSON
                                .parseObject(response.toString());
                        boolean flag = jsonObject.getBoolean("IsSuccess");
                        if (flag) {
                            MyToast.showDialog(
                                    BatchPickupOtherActivity.this, "提交成功!");
                            Intent intent = new Intent(BatchPickupOtherActivity.this, PickUpOtherActivity.class);
                            intent.putExtra("isOther", true);
                            startActivity(intent);
                            finish();

                        } else {
                            String Message = jsonObject
                                    .getString("ErrorMessage");

                            MyToast.showDialog(
                                    BatchPickupOtherActivity.this, "提交失败!"
                                            + Message);
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
        } else {


            startProgressDialog("Loading...");
            String url = Constants.url_SubmitWavePickupConfirm
                    + "?wavepickconfirmCode=" + pickCode + "&userCode="
                    + preferences.getString("code", "") + "&userName=" + preferences.getString("name", "");

            JSONStringer jsonStr = new JSONStringer();

            RequestQueue mRequestQueue = Volley
                    .newRequestQueue(BatchPickupOtherActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
                    jsonStr.toString(), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        com.alibaba.fastjson.JSONObject jsonObject = JSON
                                .parseObject(response.toString());
                        boolean flag = jsonObject.getBoolean("IsSuccess");
                        if (flag) {
                            MyToast.showDialog(
                                    BatchPickupOtherActivity.this, "提交成功!");
                            Intent intent = new Intent(
                                    BatchPickupOtherActivity.this,
                                    BatchPickupConfirmActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            String Message = jsonObject
                                    .getString("ErrorMessage");

                            MyToast.showDialog(
                                    BatchPickupOtherActivity.this, "提交失败!"
                                            + Message);
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
    }

    private void InitGoodsPos() {
        txtGoodsPosName.setText(showList.get(0).getGoodsPosName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.batch_pickup_detail, menu);
        return true;
    }

    private void GetDetail(String pickCode) {
        startProgressDialog("Loading...");
        String url = Constants.url_WavePickupConfirmDetail
                + "?wavepickconfirmCode=" + pickCode + "&userCode="
                + preferences.getString("code", "");

        JSONStringer jsonStr = new JSONStringer();

        RequestQueue mRequestQueue = Volley
                .newRequestQueue(BatchPickupOtherActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Method.GET, url,
                jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    com.alibaba.fastjson.JSONObject jsonObject = JSON
                            .parseObject(response.toString());
                    model = JSON.parseObject(response.toString(),
                            WavePickupDetailModel.class);
                    Sum.setText(model.getTotalNum());
                    TypeSum.setText(model.getTotalGoodsCount());

                    JSONArray json_array = (JSONArray) jsonObject
                            .get("Details");
                    model.Details = JSONArray.parseArray(
                            json_array.toString(),
                            PickDetailModel.class);
                    if (model.Details.size() > 0) {
                        showList.add(model.Details.get(0));
                        InitGoodsPos();
                        pickUpNum
                                .setText(model.Details.get(0).getNum());
                    }
                    if (pickUpadapter == null) {
                        pickUpadapter = new WavePickUpDetailAdapter(
                                BatchPickupOtherActivity.this, true);
                        pick_list.setAdapter(pickUpadapter);
                        pickUpadapter.appendList(showList);
                    } else {
                        pickUpadapter.clear();
                        pickUpadapter.appendList(showList);
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


    @Override
    public void deleteList(String str) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BatchPickupOtherActivity.this, PickUpOtherActivity.class);
        intent.putExtra("isOther", true);
        startActivity(intent);
        startActivity(intent);
        finish();
    }
}
