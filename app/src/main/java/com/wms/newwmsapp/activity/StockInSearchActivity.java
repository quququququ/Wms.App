package com.wms.newwmsapp.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase;
import com.handmark.pulltorefresh.library.view.PullToRefreshListView;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase.Mode;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.R.menu;
import com.wms.newwmsapp.adapter.PickUpConkfirmAdapter;
import com.wms.newwmsapp.adapter.StockInSearchAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.ConditionFilterModel;
import com.wms.newwmsapp.model.FilterModel;
import com.wms.newwmsapp.model.PageFilterModel;
import com.wms.newwmsapp.model.PickUpConfirmModel;
import com.wms.newwmsapp.model.StockInSearchListModel;
import com.wms.newwmsapp.model.StockInSearchModel;
import com.wms.newwmsapp.model.StockModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.JSONHelper;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class StockInSearchActivity extends BaseActivity {

	private ImageView back;
	private Button button_check;
	private PullToRefreshListView pick_list;
	private TextView txtGoodsPosName;
	private StockInSearchAdapter pickUpadapter;
	private TextView Information;
	
	private List<StockInSearchModel> pickList=new ArrayList<StockInSearchModel>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_in_search);
		
		back = (ImageView) findViewById(R.id.back);
		txtGoodsPosName=(TextView)findViewById(R.id.txt_GoodsPosName);
		Information=(TextView)findViewById(R.id.Information);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		button_check = (Button) findViewById(R.id.button_check);
		button_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
				getPickUpConfirmList(stockCode,txtGoodsPosName.getText().toString());
			
			}
		});
		
		txtGoodsPosName.setOnEditorActionListener(new TextView.OnEditorActionListener() { 

			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)  {                          

				if (actionId==EditorInfo.IME_ACTION_SEARCH ||(event != null&& event.getAction()==KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					getPickUpConfirmList(stockCode,txtGoodsPosName.getText().toString());
					
					return true;             
				}               
				return true;           
			}       
		});
		
		pick_list = (PullToRefreshListView) findViewById(R.id.StockInSearchList_list);
		pick_list.setMode(Mode.BOTH);
		
	}
	private void getPickUpConfirmList(String StockCode,String GoodsPosName) {
		startProgressDialog("Loading...");

		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object()
					.key("searchlist").object()
						.key("StockCode").value(StockCode)
						.key("CustGoodsName").value("")
						.key("GoodsCode").value("")
						.key("BarCode").value("")
						.key("GoodsAreaName").value("")
						.key("GoodsPosName").value(GoodsPosName)
						.endObject()
					.endObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestQueue mRequestQueue = Volley
				.newRequestQueue(StockInSearchActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_StockinSearch, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
							StockInSearchModel model=com.alibaba.fastjson.JSONObject.parseObject(jsonObject.toString(),StockInSearchModel.class);
							
							com.alibaba.fastjson.JSONArray json_array = (com.alibaba.fastjson.JSONArray) jsonObject
									.get("Data");
							model.list = JSONArray.parseArray(
									json_array.toString(),
									StockInSearchListModel.class);
//							pickList.add(model);
							if (pickUpadapter == null) {
								pickUpadapter = new StockInSearchAdapter (
										StockInSearchActivity.this, true);
								pick_list.setAdapter(pickUpadapter);
								pickUpadapter.appendList(model.list);
							} else {
								pickUpadapter.clear();
								pickUpadapter.appendList(model.list);
							}
						
							Information.setText(" 汇总信息         商品种类:"+model.getGoodsTypeSum()+"         商品总数:"+model.getGoodsSum());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
				}, handler,true);
		mRequestQueue.add(request);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stock_in_search, menu);
		return true;
	}
	private class FinishRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pick_list.onRefreshComplete();
		}
	}

	
	public class FilterClass
	{
		
		public String StockCode;
		public String CustGoodsName;
		public String GoodsCode;
		public String GoodsName;
		public String GoodsAreaName;
		public String GoodsPosName;
	}
}
