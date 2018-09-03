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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.UpToOtherModel;
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

/**
 * Created by cheng on 2018/7/10.
 */

public class UpToOtherActivity extends BaseActivity {
    private ImageView back;
    private Button btnSearch;
    private EditText etSearch;
    private String pickCode;
    private UpToOtherModel model;
    private TextView tvTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_to_other);

        initView();

    }

    private void initView() {
        tvTitleName = (TextView) findViewById(R.id.tv_title_name);
        tvTitleName.setText("移库上架");

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        etSearch = (EditText) findViewById(R.id.et_down_to_other);

        btnSearch = (Button) findViewById(R.id.btn_down_to_other);
        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                goNext();
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    goNext();
                    return true;
                }
                return true;
            }
        });
    }

    private void goNext() {
        pickCode = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(pickCode)) {
            MyToast.showDialog(UpToOtherActivity.this, "请输入货位号！");
            return;
        }

        startProgressDialog("Loading...");
        String url = Constants.url_GetStockTransferGetPutGoodsPos + "stockCode=" + stockCode + "&goodsPosName=" + pickCode;
        JSONStringer jsonStr = new JSONStringer();
//        Log.i("走不走",url.toString());
        RequestQueue mRequestQueue = Volley.newRequestQueue(UpToOtherActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
                    model = JSON.parseObject(response.toString(), UpToOtherModel.class);
                    if(model.isIsSuccess()){
                        Intent intent = new Intent(UpToOtherActivity.this,UpToOtherDetailActivity.class);
                        intent.putExtra(UpToOtherDetailActivity.GOOD_POST_CODE,model.getGoodsPosCode());
                        intent.putExtra(UpToOtherDetailActivity.GOOD_POST_NAME,model.getGoodsPosName());
                        startActivity(intent);
                    }else{
                        MyToast.showDialog(UpToOtherActivity.this, model.getErrorMessage());
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
