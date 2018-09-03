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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.FinishToCarAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.BaseModel;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/25.
 */

public class FinishToCarActivity extends BaseActivity implements FinishToCarAdapter.ICashPaymentPwdListener{
    public static final String CAR_PLATE = "car.plate";
    private ImageView back;
    private TextView tvShowPlate;
    private LinearLayout sure_package;
    private Button btnRescanner, btnToSubmit;
    private BaseModel baseModel;
    private ListView lvPackageNum;
    private EditText etPackage;
    private List<String> strList = new ArrayList<String>();
    private FinishToCarAdapter mAdapter;
    private String postStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_to_car);
        btnRescanner = (Button) findViewById(R.id.btn_rescanner);
        btnToSubmit = (Button) findViewById(R.id.btn_submit);
        lvPackageNum = (ListView) findViewById(R.id.pick_list);
        tvShowPlate = (TextView) findViewById(R.id.tv_show_plate);
        tvShowPlate.setText(getIntent().getStringExtra(CAR_PLATE));
        etPackage = (EditText) findViewById(R.id.et_input_package);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        mAdapter = new FinishToCarAdapter(this);
        lvPackageNum.setAdapter(mAdapter);

        btnRescanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //重新扫，清空输入框
                postStr = "";
                mAdapter.clearList();
            }
        });

        btnToSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strList.size() == 0) {
                    Toast.makeText(FinishToCarActivity.this, "请绑定箱号", Toast.LENGTH_LONG).show();
                    return;
                }

                startProgressDialog("Loading...");
                JSONStringer jsonStr = null;
                postStr = "";
                for (int i = 0; i < mAdapter.getList().size(); i++) {
                    postStr = postStr + "," + strList.get(i);
                }

                postStr = postStr.substring(1, postStr.length());
                try {
                    jsonStr = new JSONStringer().object()
                            .key("stockCode").value(preferences.getString("StockCode", ""))
                            .key("userName").value(preferences.getString("name", ""))
                            .key("plate").value(getIntent().getStringExtra(CAR_PLATE))
                            .key("packageCodes").value(postStr)
                            .endObject();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final RequestQueue mRequestQueue = Volley.newRequestQueue(FinishToCarActivity.this);
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                        Constants.url_GetWaveOutStockPickBindPlate, jsonStr.toString(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                baseModel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), BaseModel.class);
                                if (baseModel.isIsSuccess()) {
                                    Intent intent = new Intent(FinishToCarActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    MyToast.showDialog(FinishToCarActivity.this, baseModel.getErrorMessage());
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
        });

        etPackage.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    strList.add(etPackage.getText().toString().trim());
                    mAdapter.setData(strList);
                    etPackage.setText("");
                    return true;
                }
                return true;
            }
        });
        sure_package = (LinearLayout) findViewById(R.id.sure_package);
        sure_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etPackage.getText().toString().trim())) {
                    Toast.makeText(FinishToCarActivity.this, "请绑定箱号", Toast.LENGTH_LONG).show();
                    return;
                }

                strList.add(etPackage.getText().toString().trim());
                mAdapter.setData(strList);
                etPackage.setText("");
            }
        });
    }

    @Override
    public void deleteList(String str) {

    }
}
