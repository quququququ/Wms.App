package com.wms.newwmsapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.BaseModel;
import com.wms.newwmsapp.model.SearchResultData;
import com.wms.newwmsapp.model.SeedingSuccessModel;
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


public class SeedingOtherDetailActivity extends BaseActivity implements SpeechSynthesizerListener {
    private ImageView back;
    private String seachCode;
    private EditText GoodsposCode;
    //    private ListView lvOtherDetail;
//    private OtherDetailAdapter mAdapter;
    private SearchResultData model;
    private Button btnResetSubmit;
    private BaseModel baseModel;
    private SeedingSuccessModel seedingSuccessModel;
    private Button btnSearchLast;
    private SpeechSynthesizer speechSynthesizer;
    private TextView SortNo;
    private Button btn_to_search;
    private TextView tvCode, tvName, tvColor, tvModel, tvSeedingCode, tvAllDan, tvAllNan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_seeding_other_detail);

        speechSynthesizer = new SpeechSynthesizer(getApplicationContext(),
                "holder", this);
        // 此处需要将setApiKey方法的两个参数替换为你在百度开发者中心注册应用所得到的apiKey和secretKey
        speechSynthesizer.setApiKey("hjBNHz0LysuLfMK5AgfmRVFGnjOakyfK", "HW0YfbUrvcDdmL1hAjFUszZFkn3oIZSI");
        speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setParams();

        seachCode = getIntent().getStringExtra("Code");

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        initView();

    }

    private void setParams() {
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_AMR);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);
    }

    private void initView() {
        tvAllDan = (TextView) findViewById(R.id.tv_all_dan);
        tvAllNan = (TextView) findViewById(R.id.tv_all_num);
        tvSeedingCode = (TextView) findViewById(R.id.tv_seeding_code);
        tvCode = (TextView) findViewById(R.id.GoodsCode); //编码
        tvName = (TextView) findViewById(R.id.GoodsCustomerCode); //名称
        tvColor = (TextView) findViewById(R.id.GoodsColor); //颜色
        tvModel = (TextView) findViewById(R.id.GoodsModel); //规格

        SortNo = (TextView) findViewById(R.id.txtSortNo);
        GoodsposCode = (EditText) findViewById(R.id.GoodsposCode);
//        lvOtherDetail = (ListView) findViewById(R.id.pick_list);
//        mAdapter = new OtherDetailAdapter(this);
//        lvOtherDetail.setAdapter(mAdapter);
        btnResetSubmit = (Button) findViewById(R.id.btnResetSubmit);
        btnSearchLast = (Button) findViewById(R.id.btn_search_last);
        btn_to_search = (Button) findViewById(R.id.btn_to_search);

        tvSeedingCode.setText("波次单号：  " + seachCode);
        tvAllDan.setText(getIntent().getStringExtra("AllOrderCount"));
        tvAllNan.setText(getIntent().getStringExtra("AllNum"));

        GoodsposCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsposCode.setFocusable(true);//设置输入框可聚集
                GoodsposCode.setFocusableInTouchMode(true);//设置触摸聚焦
                GoodsposCode.requestFocus();//请求焦点
                GoodsposCode.findFocus();//获取焦点
                InputMethodManager imm1 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm1.showSoftInput(GoodsposCode, InputMethodManager.SHOW_FORCED);// 显示输入法
            }
        });

        GoodsposCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    goSearch();
                    GoodsposCode.setText("");
                    return true;
                }
                return true;
            }
        });

//        lvOtherDetail.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        GoodsposCode.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
//                        InputMethodManager imm1 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                        if (imm1.isActive()) {
//                            imm1.hideSoftInputFromWindow(GoodsposCode.getWindowToken(), 0);// 隐藏输入法
//                        }
//
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        GoodsposCode.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
//                        InputMethodManager imm2 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                        if (imm2.isActive()) {
//                            imm2.hideSoftInputFromWindow(GoodsposCode.getWindowToken(), 0);// 隐藏输入法
//                        }
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        GoodsposCode.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
//                        InputMethodManager imm3 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                        if (imm3.isActive()) {
//                            imm3.hideSoftInputFromWindow(GoodsposCode.getWindowToken(), 0);// 隐藏输入法
//                        }
//
//                        break;
//                }
//                return false;
//            }
//        });

        btnResetSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SeedingOtherDetailActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否重播！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        JSONStringer jsonStr = new JSONStringer();
                        RequestQueue mRequestQueue = Volley.newRequestQueue(SeedingOtherDetailActivity.this);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                                Constants.url_ResetWavePickConfirmForSowing + "?code=" + seachCode, jsonStr.toString(),
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
                                        baseModel = JSON.parseObject(response.toString(), BaseModel.class);
                                        if (baseModel.isIsSuccess()) {
                                            MyToast.showDialog(SeedingOtherDetailActivity.this, "重播成功！");
                                        } else {
                                            MyToast.showDialog(SeedingOtherDetailActivity.this, "重播失败！");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }, SeedingOtherDetailActivity.this.handler);
                        mRequestQueue.add(request);
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
        });

        btnSearchLast.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeedingOtherDetailActivity.this, SeedingLastActivity.class);
                intent.putExtra("Code", seachCode);
                startActivity(intent);
            }
        });

        btn_to_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch();
                GoodsposCode.setText("");
            }
        });
    }

    private void goSearch() {
        if (TextUtils.isEmpty(GoodsposCode.getText().toString().trim())) {
            Toast.makeText(SeedingOtherDetailActivity.this, "请输入查询商品条码", Toast.LENGTH_LONG).show();
            return;
        }

        startProgressDialog("Loading...");
        String url = Constants.url_GetRunWavePickConfirmForSowing
                + "?code=" + seachCode + "&barCode=" + GoodsposCode.getText().toString().trim();

        JSONStringer jsonStr = new JSONStringer();

        RequestQueue mRequestQueue = Volley
                .newRequestQueue(SeedingOtherDetailActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    com.alibaba.fastjson.JSONObject jsonObject = JSON
                            .parseObject(response.toString());

                    model = JSON.parseObject(response.toString(),
                            SearchResultData.class);
                    if (model.isIsSuccess()) {
                        if ("已经播种完成！".equals(model.getErrorMessage())) {
                            if (!String.valueOf(model.getDetails().getSortNo()).equals("0")) {
                                speechSynthesizer.speak(String.valueOf(model.getDetails().getSortNo()));
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(SeedingOtherDetailActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage("播种完成是否提交");
                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    JSONStringer jsonStr = new JSONStringer();
                                    RequestQueue mRequestQueue = Volley.newRequestQueue(SeedingOtherDetailActivity.this);
                                    String url = Constants.url_WavePickConfirmSubmit
                                            + "?wavepickconfirmCode=" + seachCode + "&userCode="
                                            + preferences.getString("code", "");
                                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                                            url, jsonStr.toString(),
                                            new Response.Listener<JSONObject>() {

                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
                                                    baseModel = JSON.parseObject(response.toString(), BaseModel.class);
                                                    if (baseModel.isIsSuccess()) {

                                                        MyToast.showDialog(SeedingOtherDetailActivity.this, "提交成功！");

                                                        finish();
                                                    } else {
                                                        MyToast.showDialog(SeedingOtherDetailActivity.this, "提交失败！");
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }, SeedingOtherDetailActivity.this.handler);
                                    mRequestQueue.add(request);
                                }
                            });
                            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            tvCode.setText("商品编码：  " + model.getDetails().getGoodsCustomerCode());
                            tvName.setText("商品名称：  " + model.getDetails().getGoodsName());
                            tvColor.setText("商品颜色：  " + model.getDetails().getGoodsColor());
                            tvModel.setText("商品规格：  " + model.getDetails().getGoodsModel());
//                        mAdapter.setData(model.getDetails().);
                            if (!TextUtils.isEmpty(model.getDetails().getSortNo()) && !"0".equals(model.getDetails().getSortNo())) {
                                speechSynthesizer.speak(String.valueOf(model.getDetails().getSortNo()));
                                SortNo.setText(String.valueOf(model.getDetails().getSortNo()));
                            }
                        }
                    } else {
                        Toast.makeText(SeedingOtherDetailActivity.this, model.getErrorMessage(), Toast.LENGTH_LONG).show();
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
    public void onStartWorking(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechStart(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onNewDataArrive(SpeechSynthesizer speechSynthesizer, byte[] bytes, boolean b) {

    }

    @Override
    public void onBufferProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

    }

    @Override
    public void onSpeechProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

    }

    @Override
    public void onSpeechPause(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechResume(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onCancel(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSynthesizeFinish(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechFinish(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onError(SpeechSynthesizer speechSynthesizer, SpeechError speechError) {

    }

}
