package com.wms.newwmsapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.WavePickUpDetailAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.PickDetailModel;
import com.wms.newwmsapp.model.WavePickupDetailModel;
import com.wms.newwmsapp.model.WavePickupModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.EncodingUtils;
import com.wms.newwmsapp.tool.MyChooseToastDialog;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.tool.PrintCodePop;
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

public class BatchPickupDetailActivity extends BaseActivity {

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
    public static BatchPickupDetailActivity pickupDetailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_pickup_detail);
        pickupDetailActivity = this;
        isOther = getIntent().getBooleanExtra("isOther", false);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        Intent intent = getIntent();

        pick = (WavePickupModel) intent.getSerializableExtra("Pick");

        pick_list = (ListView) findViewById(R.id.pick_list);

        GetDetail(pick.getCode());
        GoodsposCode = (EditText) findViewById(R.id.GoodsposCode);
        txtGoodsPosName = (TextView) findViewById(R.id.GoodsposName);

        Sum = (TextView) findViewById(R.id.Sum);
        TypeSum = (TextView) findViewById(R.id.TypeSum);
        pickUpNum = (TextView) findViewById(R.id.pickUpNum);
        HavePickUpNum = (TextView) findViewById(R.id.HavePickUpNum);
        sure_lin = (LinearLayout) findViewById(R.id.sure_lin);
        save_lin = (LinearLayout) findViewById(R.id.save_lin);


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
                    MyToast.showDialog(BatchPickupDetailActivity.this,
                            "输入数量有误！");
                    HavePickUpNum.setText(num);
                    return;
                }

                if (IsComplete()) {

                    progressDialog = MyToast.showChooseDialog(
                            BatchPickupDetailActivity.this, "波次分拣完成,是否提交该单据！",
                            new OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    // TODO Auto-generated method stub
                                    try {
                                        Submit(pick.getCode());
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
                        BatchPickupDetailActivity.this, "是否重拣？",
                        new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                try {
                                    GetDetail(pick.getCode());
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
        btnSubmitAgain.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                progressDialog = MyToast.showChooseDialog(
                        BatchPickupDetailActivity.this, "是否重新提交？",
                        new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                try {
                                    Submit(pick.getCode());
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
            MyToast.showDialog(BatchPickupDetailActivity.this, "条码为空！");
            return;

        }
        if (showList.size() == 0) {
            MyToast.showDialog(BatchPickupDetailActivity.this, "没有商品！");
            return;
        }
        if (IsComplete()) {
            progressDialog = MyToast.showChooseDialog(
                    BatchPickupDetailActivity.this, "波次分拣完成,是否提交该单据！",
                    new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            try {
                                Submit(pick.getCode());
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
            MyToast.showDialog(BatchPickupDetailActivity.this, "该商品已完成或不存在！");
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
            progressDialog = MyToast.showChooseDialog(
                    BatchPickupDetailActivity.this, "波次分拣完成,是否提交该单据！",
                    new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            try {
                                Submit(pick.getCode());
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                // TODO: handle exception
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
        HavePickUpNum.setText("0");
        num = "0";
        GoodsposCode.setText("");
        pickUpadapter.clear();
        pickUpadapter.appendList(showList);
    }

    private void Submit(final String pickCode) {
        boolean isComplete = true;
        for (int i = 0; i < model.Details.size(); i++) {
            if (!model.Details.get(i).isIsScan()) {
                isComplete = false;
            }

        }
        if (!isComplete) {
            MyToast.showDialog(BatchPickupDetailActivity.this,
                    "还有" + Sum.getText() + "货品\n" + TypeSum.getText()
                            + "种类\n作业没有完成！");
            return;
        }


        //如果是转仓分拣
        if (isOther) {
            startProgressDialog("Loading...");
            String url = Constants.url_SubmitWavePickupConfirm
                    + "?wavepickconfirmCode=" + pickCode + "&packageCode=" + postStr + "&userName=" + preferences.getString("name", "");
            JSONStringer jsonStr = new JSONStringer();

            RequestQueue mRequestQueue = Volley
                    .newRequestQueue(BatchPickupDetailActivity.this);
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
                                    BatchPickupDetailActivity.this, "提交成功!");
                            Intent intent = new Intent(
                                    BatchPickupDetailActivity.this,
                                    BatchPickupConfirmActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            String Message = jsonObject
                                    .getString("ErrorMessage");

                            MyToast.showDialog(
                                    BatchPickupDetailActivity.this, "提交失败!"
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
                    .newRequestQueue(BatchPickupDetailActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
                    jsonStr.toString(), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //加弹窗条形码

                        com.alibaba.fastjson.JSONObject jsonObject = JSON
                                .parseObject(response.toString());
                        boolean flag = jsonObject.getBoolean("IsSuccess");
                        if (flag) {
                            LayoutInflater inflater = getLayoutInflater();
                            View view = inflater.inflate(R.layout.dialog_showchoosedialog, null);
                            final AlertDialog ad = new AlertDialog.Builder(BatchPickupDetailActivity.this).create();
                            ad.setView(view);
                            ad.show();

                            TextView txtMessage = (TextView) view.findViewById(R.id.message);
                            txtMessage.setText("分拣提交成功，是否打印面单？");
                            TextView btnOk = (TextView) view.findViewById(R.id.btn_ok);
                            btnOk.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    // TODO Auto-generated method stub
                                    ad.dismiss();
                                    WindowManager wm = (WindowManager) BatchPickupDetailActivity.this.getSystemService(Context.WINDOW_SERVICE);
                                    int width = wm.getDefaultDisplay().getWidth();
                                    PrintCodePop showMoreMenuPop = new PrintCodePop(BatchPickupDetailActivity.this, BatchPickupDetailActivity.this.getWindow(), EncodingUtils.createBarcode(pickCode, width, 200, false), "1");
                                    showMoreMenuPop.showUp();

                                }
                            });

                            TextView btnCancle = (TextView) view.findViewById(R.id.btn_cancel);
                            btnCancle.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    // TODO Auto-generated method stub
                                    ad.dismiss();
                                    Intent intent = new Intent(BatchPickupDetailActivity.this, BatchPickupConfirmActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

//                            MyToast.showDialog(
//                                    BatchPickupOtherActivity.this, "提交成功!");


                        } else {
                            String Message = jsonObject
                                    .getString("ErrorMessage");

                            MyToast.showDialog(
                                    BatchPickupDetailActivity.this, "提交失败!"
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
                .newRequestQueue(BatchPickupDetailActivity.this);
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

                    com.alibaba.fastjson.JSONArray json_array = (com.alibaba.fastjson.JSONArray) jsonObject
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
                                BatchPickupDetailActivity.this, true);
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
}
