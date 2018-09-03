package com.wms.newwmsapp.activity;

import org.json.JSONObject;
import org.json.JSONStringer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.R.menu;
import com.wms.newwmsapp.adapter.WavePickUpDetailAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.PickDetailModel;
import com.wms.newwmsapp.model.WavePickupDetailModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SeedingActivity extends  BaseActivity {
	private ImageView back;
	private Button btnSearch;
	private TextView txtWavePickupConfirmCode;
	private WavePickupDetailModel model = new WavePickupDetailModel();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seeding);
		
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		txtWavePickupConfirmCode=(TextView) findViewById(R.id.WavePickupConfirmCode);
		
		btnSearch=(Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String pickCode=txtWavePickupConfirmCode.getText().toString();
				if(pickCode.isEmpty())
				{
					MyToast.showDialog(SeedingActivity.this, "请输入波次确认单号！");
					return;
					
				}
				GetDetail(pickCode);
			}
		});

	}

	private void GetDetail(String pickCode) {
		startProgressDialog("Loading...");
		String url = Constants.url_GetWavePickConfirm
				+ "?StockCode="+preferences.getString("StockCode", "")+"&wavepickconfirmCode=" + pickCode + "&userCode="
				+ preferences.getString("code", "");

		JSONStringer jsonStr = new JSONStringer();

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(SeedingActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url,
				jsonStr.toString(), new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {

							com.alibaba.fastjson.JSONObject jsonObject = JSON
									.parseObject(response.toString());
							model = JSON.parseObject(response.toString(),
									WavePickupDetailModel.class);
							
						Intent intent=new Intent(SeedingActivity.this,SeedingDetailActivity.class);
						intent.putExtra("Code", model.getCode());
						startActivity(intent);
						
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seeding, menu);
		return true;
	}

}
