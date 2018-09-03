package com.wms.newwmsapp.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.view.PullToRefreshListView;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.InstockAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.InstockModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.TimeDatePicker;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

@SuppressLint("InflateParams")
public class InstockListActivity extends BaseActivity {

	private ImageView back;
	private DrawerLayout mDrawerLayout;
	private LinearLayout mFilterLayout;
	private List<InstockModel> instocks;
	private PullToRefreshListView instock_list;
	private InstockAdapter instockAdapter;
	private EditText Code, PurchaseNo; 
	private Button InstockDate, InstockEndDate;
	private Button button_check;
	private TextView add_tv;
	
	private String Code_check = "";
	private String PurchaseNo_check = "";
	private String InstockDate_check = "";
	private String InstockEndDate_check = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instocklist);

		back = (ImageView) findViewById(R.id.back);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mFilterLayout = (LinearLayout) findViewById(R.id.filter_layout);
		instock_list = (PullToRefreshListView) findViewById(R.id.instock_list);
		instock_list.setMode(Mode.BOTH);
		Code = (EditText) findViewById(R.id.Code);
		PurchaseNo = (EditText) findViewById(R.id.PurchaseNo);
		InstockDate = (Button) findViewById(R.id.InstockDate);
		InstockEndDate = (Button) findViewById(R.id.InstockEndDate);
		new TimeDatePicker().selectTime(InstockDate, InstockListActivity.this, InstockListActivity.this);
		new TimeDatePicker().selectTime(InstockEndDate, InstockListActivity.this, InstockListActivity.this);
		button_check = (Button) findViewById(R.id.button_check);
		add_tv = (TextView) findViewById(R.id.add_tv);

		getList(Code_check, PurchaseNo_check, InstockDate_check, InstockEndDate_check);

		//if (!mDrawerLayout.isDrawerOpen(mFilterLayout)) {
			//mDrawerLayout.openDrawer(mFilterLayout);
		//}
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		button_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Code_check = Code.getText().toString().trim();
				PurchaseNo_check = PurchaseNo.getText().toString().trim();
				InstockDate_check = InstockDate.getText().toString().trim();
				InstockEndDate_check = InstockEndDate.getText().toString().trim();
				getList(Code_check, PurchaseNo_check, InstockDate_check, InstockEndDate_check);
				if (mDrawerLayout.isDrawerOpen(mFilterLayout)) {
					mDrawerLayout.closeDrawer(mFilterLayout);
				}
			}
		});

		add_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InstockListActivity.this,InstockDetailActivity.class);
				startActivity(intent);
			}
		});
		
		instock_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>(){  
            @Override  
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {  
                // 下拉的时候数据重置  
            	getList(Code_check, PurchaseNo_check, InstockDate_check, InstockEndDate_check);
            }  
              
            @Override  
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {  
                // 上拉的时候添加选项  
            	new FinishRefresh().execute();  
            }  
		});  
	}

	
	private void getList(String Code, String PurchaseNo, String InstockDate, String InstockEndDate) {
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object().key("instockParams").object()
					.key("InstockCode").value(Code) //入库单号
					.key("PurchaseNo").value(PurchaseNo) //采购单号
					.key("InstockDate").value(InstockDate) //入库时间
					.key("InstockEndDate").value(InstockEndDate) //结束时间
					.key("StockCode").value(stockCode) //仓库号
					.endObject().endObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(InstockListActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetInstockCheckForm, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
						com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
						instocks = JSONArray.parseArray(jsonArray.toString(), InstockModel.class);
						
						if (instockAdapter == null) {
							instockAdapter = new InstockAdapter(getApplicationContext(), true);
							instock_list.setAdapter(instockAdapter);
							instockAdapter.appendList(instocks);
						} else {
							instockAdapter.clear();
							instockAdapter.appendList(instocks);
						}

						stopProgressDialog();
						new FinishRefresh().execute();  
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						stopProgressDialog();
						new FinishRefresh().execute();  
					}
				}, handler);
		mRequestQueue.add(request);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getList(Code_check, PurchaseNo_check, InstockDate_check, InstockEndDate_check);
	}
	
	private class FinishRefresh extends AsyncTask<Void, Void, Void>{  
		@Override  
		protected Void doInBackground(Void... params) {  
			return null;  
		}  
		   
		@Override  
		protected void onPostExecute(Void result){  
			instock_list.onRefreshComplete();  
		}  
	}  
}
