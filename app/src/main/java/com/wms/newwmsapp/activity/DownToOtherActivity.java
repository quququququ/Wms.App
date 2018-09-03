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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.DownToOtherModel;
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

import java.io.Serializable;
import java.util.List;

/**
 * Created by cheng on 2018/7/4.
 */

public class DownToOtherActivity extends BaseActivity {
    private ImageView back;
    private Button btnSearch;
    private EditText etSearch;
    private DownToOtherModel model;
    private List<String> dataList;
    private String pickCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_to_other);

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
                    etSearch.setText("");
                    return true;
                }
                return true;
            }
        });

    }

    private void goNext() {
        pickCode = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(pickCode)) {
            MyToast.showDialog(DownToOtherActivity.this, "请输入货位号！");
            return;
        }

        startProgressDialog("Loading...");
        String url = Constants.url_GetStockTransferGetOutGoodsPos + "stockCode=" + stockCode + "&goodsPosName=" + pickCode;

        JSONStringer jsonStr = new JSONStringer();

        RequestQueue mRequestQueue = Volley.newRequestQueue(DownToOtherActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                jsonStr.toString(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
                    model = JSON.parseObject(response.toString(), DownToOtherModel.class);
                    if (model.isIsSuccess()) {
//                        for (int i = 0; i < model.getData().size(); i++) {
//                            dataList = new ArrayList<String>();
//                            dataList.add(model.getData().get(i).getBarCode());
//
//                        }
                        Intent intent = new Intent(DownToOtherActivity.this, PostDownToOtherActivity.class);
                        intent.putExtra("user", (Serializable) model);
//                        intent.putExtra(PostDownToOtherActivity.GOOD_POST_ARRAY,dataList.toString());
                        intent.putExtra(PostDownToOtherActivity.GOOD_POST_NAME, pickCode);
                        if (model.getData().size() > 0)
                            intent.putExtra(PostDownToOtherActivity.GOOD_POS_CODE, model.getData().get(0).getGoodsPosCode());
                        etSearch.setText("");
                        startActivity(intent);
                    } else {
                        MyToast.showDialog(DownToOtherActivity.this, model.getErrorMessage());
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
