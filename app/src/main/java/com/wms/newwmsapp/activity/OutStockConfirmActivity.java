package com.wms.newwmsapp.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONStringer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase;
import com.handmark.pulltorefresh.library.view.PullToRefreshListView;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase.Mode;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.id;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.R.menu;
import com.wms.newwmsapp.adapter.OutStockConfirmAdapter;
import com.wms.newwmsapp.adapter.PickUpConkfirmAdapter;
import com.wms.newwmsapp.adapter.PickUpCustGoodsAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.ConditionFilterModel;
import com.wms.newwmsapp.model.ConditionModel;
import com.wms.newwmsapp.model.CustGoodModel;
import com.wms.newwmsapp.model.FilterModel;
import com.wms.newwmsapp.model.OutStockConfirmModel;
import com.wms.newwmsapp.model.PageFilterModel;
import com.wms.newwmsapp.model.PickUpConfirmModel;
import com.wms.newwmsapp.model.SortingMode;
import com.wms.newwmsapp.model.StockModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.JSONHelper;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.tool.TimeDatePicker;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class OutStockConfirmActivity extends BaseActivity {

	
	private Button button_check;
	private ImageView back;
	private DrawerLayout mDrawerLayout;
	private LinearLayout mFilterLayout;
	private EditText txtCode, txtPurchaseNo_outstockConfirm;
	private Spinner OutStockConfirmCustGoods_sp;
	private Button bTime, eTime;
	private PullToRefreshListView outstock_list;

	private int CurrentIndex = 1;
	private int PageSize = 20;
	private int CurrentIndex_Total;

	private FilterModel filter = new FilterModel();// 查询条件(总)
	private ConditionModel Condition = new ConditionModel();// 查询条件(排序+条件)
	private List<SortingMode> Sorting = new ArrayList<SortingMode>();// 条件查询(排序)
	private List<ConditionFilterModel> Filters = new ArrayList<ConditionFilterModel>();// 查询条件(条件)
	private PageFilterModel PageFilter = new PageFilterModel();// 查询条件(分页)
	
	private List<OutStockConfirmModel> outList;
	private OutStockConfirmAdapter outStockConfirmadapter;
	
	private String CustGoodsCode = "";
	private String bTime_check = "";
	private String eTime_check = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_out_stock_confirm);
		
		back = (ImageView) findViewById(R.id.back);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mFilterLayout = (LinearLayout) findViewById(R.id.filter_layout);
		button_check = (Button) findViewById(R.id.button_check);
		txtCode = (EditText) findViewById(R.id.Code);
		txtPurchaseNo_outstockConfirm=(EditText) findViewById(R.id.PurchaseNo);
		
		OutStockConfirmCustGoods_sp= (Spinner) findViewById(R.id.OutStockConfirmCustGoods_sp);
		bTime = (Button) findViewById(R.id.bTime);
		eTime = (Button) findViewById(R.id.eTime);
		new TimeDatePicker().selectTime(bTime, OutStockConfirmActivity.this,
				OutStockConfirmActivity.this);
		new TimeDatePicker().selectTime(eTime, OutStockConfirmActivity.this,
				OutStockConfirmActivity.this);
		
		outstock_list = (PullToRefreshListView) findViewById(R.id.outstockconfim_pulllist);
		outstock_list.setMode(Mode.BOTH);
		
		if (!mDrawerLayout.isDrawerOpen(mFilterLayout)) {
			mDrawerLayout.openDrawer(mFilterLayout);
		}
		PageFilter=new PageFilterModel();
		PageFilter.setCurrentIndex(CurrentIndex+"");
		PageFilter.setPageSize("20");
		Condition.setSorting(Sorting);
		Condition.setFilters(Filters);
		filter.setCondition(Condition);
		filter.setPageFilter(PageFilter);
		
		getCustGoods();
		

		getOutStockConfirmList(filter, txtCode.getText().toString(),
				txtPurchaseNo_outstockConfirm.getText().toString(), custGoodsCode, bTime
						.getText().toString().trim(), eTime.getText()
						.toString().trim());
		
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		outstock_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// 下拉的时候数据重置
				CurrentIndex = 1;
				PageFilter = new PageFilterModel();
				PageFilter.setCurrentIndex(CurrentIndex + "");
				PageFilter.setPageSize("20");
				filter.setPageFilter(PageFilter);
				getOutStockConfirmList(filter, txtCode.getText()
						.toString(), txtPurchaseNo_outstockConfirm.getText()
						.toString(), custGoodsCode, bTime.getText()
						.toString().trim(), eTime.getText().toString()
						.trim());
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// 上拉的时候添加选项
				if (CurrentIndex <= CurrentIndex_Total) {
					PageFilter = new PageFilterModel();
					PageFilter.setCurrentIndex(CurrentIndex + "");
					PageFilter.setPageSize("20");
					filter.setPageFilter(PageFilter);
					getMore(filter, txtCode.getText().toString(),
							txtPurchaseNo_outstockConfirm.getText().toString(),
							custGoodsCode, bTime.getText().toString()
									.trim(), eTime.getText().toString()
									.trim());
				} else {
					MyToast.showDialog(OutStockConfirmActivity.this,
							"已加载全部数据");
					new FinishRefresh().execute();
				}
			}
		});
		
		button_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Filters = new ArrayList<ConditionFilterModel>();
			
				filter= new FilterModel();
				Condition.setFilters(Filters);
				
				bTime_check = bTime.getText().toString().trim();
				eTime_check = eTime.getText().toString().trim();

				CurrentIndex = 1;
				PageFilter = new PageFilterModel();
				PageFilter.setCurrentIndex(CurrentIndex + "");
				PageFilter.setPageSize("20");

				filter.setPageFilter(PageFilter);
				filter.setCondition(Condition);
				getOutStockConfirmList(filter, txtCode.getText().toString(),
						txtPurchaseNo_outstockConfirm.getText().toString(), custGoodsCode, bTime
								.getText().toString().trim(), eTime.getText()
								.toString().trim());
				if (mDrawerLayout.isDrawerOpen(mFilterLayout)) {
					mDrawerLayout.closeDrawer(mFilterLayout);
				}
			}
		});
		
	}
	
	private void getMore(FilterModel filter, String Code, String PurchaseNO,
			String CustGoodsCode, String bTime_check, String eTime_check) {
		startProgressDialog("Loading...");

		ConditionFilterModel con = new ConditionFilterModel();
		con.setFilter("0");
		con.setName("StockCode");
		con.setValue(stockCode);
		filter.Condition.Filters.add(con);

		if (!TextUtils.isEmpty(Code)) {
			ConditionFilterModel con1 = new ConditionFilterModel();
			con1.setFilter("0");
			con1.setName("Code");
			con1.setValue(Code);
			filter.Condition.Filters.add(con1);

		}

		if (!TextUtils.isEmpty(CustGoodsCode)) {
			ConditionFilterModel con1 = new ConditionFilterModel();
			con1.setFilter("0");
			con1.setName("CustGoodsCode");
			con1.setValue(CustGoodsCode);
			filter.Condition.Filters.add(con1);

		}

		if (!TextUtils.isEmpty(PurchaseNO)) {
			ConditionFilterModel con1 = new ConditionFilterModel();
			con1.setFilter("0");
			con1.setName("PurchaseNO");
			con1.setValue(PurchaseNO);
			filter.Condition.Filters.add(con1);

		}

		JSONStringer jsonStr = new JSONStringer();
		try {
			jsonStr = JSONHelper.serialize(jsonStr, filter);
		} catch (Exception e) {

		}

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(OutStockConfirmActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetOutStockConfirm + "?bTime=" + bTime_check
						+ "&eTime=" + eTime_check, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							CurrentIndex++;

							com.alibaba.fastjson.JSONObject jsonObject = JSON
									.parseObject(response.toString());
							com.alibaba.fastjson.JSONArray json_array = (com.alibaba.fastjson.JSONArray) jsonObject
									.get("Rows");
							outList = JSONArray.parseArray(
									json_array.toString(),
									OutStockConfirmModel.class);

							outStockConfirmadapter.appendList(outList);
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
				}, handler);
		mRequestQueue.add(request);
	}
	
	private void getCustGoods() {
		String stocks_json = preferences.getString("stocks", "");
		com.alibaba.fastjson.JSONObject jsonObject = JSON
				.parseObject(stocks_json);
		com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject
				.get("StockList");
		final List<StockModel> stocks = JSONArray.parseArray(
				jsonArray.toString(), StockModel.class);

		final List<CustGoodModel> custList = new ArrayList<CustGoodModel>();
		for (StockModel model : stocks) {
			if (model.getStockCode().equals(stockCode)) {
				custList.addAll(model.getCustGoods());

			}
		}

		PickUpCustGoodsAdapter custGoodAdapter = new PickUpCustGoodsAdapter(
				OutStockConfirmActivity.this, true);
		OutStockConfirmCustGoods_sp.setAdapter(custGoodAdapter);
		custGoodAdapter.appendList(custList);
		OutStockConfirmCustGoods_sp
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						custGoodsCode = custList.get(arg2).getCustGoodsCode();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}
	
	
	private void getOutStockConfirmList(FilterModel filter, String Code,
			String PurchaseNO, String CustGoodsCode, String bTime_check,
			String eTime_check) {
		startProgressDialog("Loading...");

		ConditionFilterModel con = new ConditionFilterModel();
		con.setFilter("0");
		con.setName("StockCode");
		con.setValue(stockCode);
		filter.Condition.Filters.add(con);

		if (!TextUtils.isEmpty(Code)) {
			ConditionFilterModel con1 = new ConditionFilterModel();
			con1.setFilter("0");
			con1.setName("Code");
			con1.setValue(Code);
			filter.Condition.Filters.add(con1);

		}

		if (!TextUtils.isEmpty(CustGoodsCode)) {
			ConditionFilterModel con1 = new ConditionFilterModel();
			con1.setFilter("0");
			con1.setName("CustGoodsCode");
			con1.setValue(CustGoodsCode);
			filter.Condition.Filters.add(con1);

		}

		if (!TextUtils.isEmpty(PurchaseNO)) {
			ConditionFilterModel con1 = new ConditionFilterModel();
			con1.setFilter("0");
			con1.setName("PurchaseNO");
			con1.setValue(PurchaseNO);
			filter.Condition.Filters.add(con1);

		}

		JSONStringer jsonStr = new JSONStringer();
		try {
			jsonStr = JSONHelper.serialize(jsonStr, filter);
		} catch (Exception e) {

		}
		RequestQueue mRequestQueue = Volley
				.newRequestQueue(OutStockConfirmActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetOutStockConfirm + "?bTime=" + bTime_check
						+ "&eTime=" + eTime_check, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							CurrentIndex++;

							com.alibaba.fastjson.JSONObject jsonObject = JSON
									.parseObject(response.toString());
							int Total = jsonObject.getIntValue("Total");
							if (Total % PageSize == 0) {
								CurrentIndex_Total = Total / PageSize;
							} else {
								CurrentIndex_Total = Total / PageSize + 1;
							}
							com.alibaba.fastjson.JSONArray json_array = (com.alibaba.fastjson.JSONArray) jsonObject
									.get("Rows");
							outList = JSONArray.parseArray(
									json_array.toString(),
									OutStockConfirmModel.class);

							if ( outStockConfirmadapter== null) {
								outStockConfirmadapter = new OutStockConfirmAdapter(
										OutStockConfirmActivity.this, true);
								outstock_list.setAdapter(outStockConfirmadapter);
								outStockConfirmadapter.appendList(outList);
							} else {
								outStockConfirmadapter.clear();
								outStockConfirmadapter.appendList(outList);
							}
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
				}, handler);
		mRequestQueue.add(request);
	}

	
	private class FinishRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			outstock_list.onRefreshComplete();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.out_stock_confirm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
