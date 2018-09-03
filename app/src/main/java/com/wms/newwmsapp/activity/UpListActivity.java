package com.wms.newwmsapp.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONStringer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase;
import com.handmark.pulltorefresh.library.view.PullToRefreshListView;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase.Mode;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.InStockPutawayStatusAdapter;
import com.wms.newwmsapp.adapter.UpAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.ConditionFilterModel;
import com.wms.newwmsapp.model.ConditionModel;
import com.wms.newwmsapp.model.FilterModel;
import com.wms.newwmsapp.model.InStockPutawayStatus;
import com.wms.newwmsapp.model.PageFilterModel;
import com.wms.newwmsapp.model.SortingMode;
import com.wms.newwmsapp.model.UpModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.JSONHelper;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.tool.TimeDatePicker;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

public class UpListActivity extends BaseActivity {

	private ImageView back;
	private DrawerLayout mDrawerLayout;
	private LinearLayout mFilterLayout;
	private Button button_check;
	private EditText Code;
	private Spinner InStockPutawayStatus_sp;
	private Button bTime, eTime; 
	private PullToRefreshListView up_list;
	private List<UpModel> ups;
	private UpAdapter upAdapter;
	
	private int CurrentIndex = 1;
	private int PageSize = 20;
	private int CurrentIndex_Total;
	
	private FilterModel filter = new FilterModel();//查询条件(总)
	private ConditionModel Condition = new ConditionModel();//查询条件(排序+条件)
	private List<SortingMode> Sorting = new ArrayList<SortingMode>();//条件查询(排序)
	private List<ConditionFilterModel> Filters = new ArrayList<ConditionFilterModel>();//查询条件(条件)
	private PageFilterModel PageFilter = new PageFilterModel();//查询条件(分页)
	private String InStockPutawayStatusCode = "";
	private String bTime_check = "";
	private String eTime_check = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uplist);
		
		back = (ImageView) findViewById(R.id.back);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mFilterLayout = (LinearLayout) findViewById(R.id.filter_layout);
		button_check = (Button) findViewById(R.id.button_check);
		Code = (EditText) findViewById(R.id.Code);
		InStockPutawayStatus_sp = (Spinner) findViewById(R.id.InStockPutawayStatus_sp);
		bTime = (Button) findViewById(R.id.bTime);
		eTime = (Button) findViewById(R.id.eTime);
		new TimeDatePicker().selectTime(bTime, UpListActivity.this, UpListActivity.this);
		new TimeDatePicker().selectTime(eTime, UpListActivity.this, UpListActivity.this);
		up_list = (PullToRefreshListView) findViewById(R.id.up_list);
		up_list.setMode(Mode.BOTH);
		
		//if (!mDrawerLayout.isDrawerOpen(mFilterLayout)) {
			//mDrawerLayout.openDrawer(mFilterLayout);
		//}
		
		Condition.setSorting(Sorting);
		Condition.setFilters(Filters);
		filter.setCondition(Condition);
		filter.setPageFilter(PageFilter);
		
		//获取上架状态
		getInStockPutawayStatusCode();
		
		PageFilter=new PageFilterModel();
		PageFilter.setCurrentIndex(CurrentIndex+"");
		PageFilter.setPageSize("20");
		
		ConditionFilterModel loc_Filter = new ConditionFilterModel();
		loc_Filter.setFilter("0");
		loc_Filter.setName("StockCode");
		loc_Filter.setValue(stockCode);
		Filters.add(loc_Filter);
		Condition.setFilters(Filters);
		
		filter.setCondition(Condition);
		filter.setPageFilter(PageFilter);
		
		getUpList(filter, bTime_check, eTime_check);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		up_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>(){  
            @Override  
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {  
                // 下拉的时候数据重置  
            	CurrentIndex = 1;
            	PageFilter=new PageFilterModel();
        		PageFilter.setCurrentIndex(CurrentIndex+"");
        		PageFilter.setPageSize("20");
        		filter.setPageFilter(PageFilter);
        		getUpList(filter, bTime_check, eTime_check);
            }  
              
            @Override  
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {  
                // 上拉的时候添加选项  
            	if(CurrentIndex<=CurrentIndex_Total){
            		PageFilter=new PageFilterModel();
            		PageFilter.setCurrentIndex(CurrentIndex+"");
            		PageFilter.setPageSize("20");
            		filter.setPageFilter(PageFilter);
            		getMore(filter, bTime_check, eTime_check);
            	} else {
            		MyToast.showDialog(UpListActivity.this, "已加载全部数据");
            		new FinishRefresh().execute();  
            	} 
            }  
		});  
		
		button_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Filters = new ArrayList<ConditionFilterModel>();
				
				//仓库号
				ConditionFilterModel loc_Filter = new ConditionFilterModel();
				loc_Filter.setFilter("0");
				loc_Filter.setName("StockCode");
				loc_Filter.setValue(stockCode);
				Filters.add(loc_Filter);
				
				//入库上架单号不为空
				if(!TextUtils.isEmpty(Code.getText().toString())){
					ConditionFilterModel loc_Filter2 = new ConditionFilterModel();
					loc_Filter2.setFilter("5");
					loc_Filter2.setName("Code");
					loc_Filter2.setValue(Code.getText().toString());
					Filters.add(loc_Filter2);
				}
				//入库上架状态不为空
				if(!TextUtils.isEmpty(InStockPutawayStatusCode)){
					ConditionFilterModel loc_Filter3 = new ConditionFilterModel();
					loc_Filter3.setFilter("0");
					loc_Filter3.setName("InStockPutawayStatusCode");
					loc_Filter3.setValue(InStockPutawayStatusCode);
					Filters.add(loc_Filter3);
				}
				Condition.setFilters(Filters);
				
				bTime_check = bTime.getText().toString().trim();
				eTime_check = eTime.getText().toString().trim();
				
				CurrentIndex = 1;
            	PageFilter=new PageFilterModel();
        		PageFilter.setCurrentIndex(CurrentIndex+"");
        		PageFilter.setPageSize("20");
        		
        		filter.setPageFilter(PageFilter);
				filter.setCondition(Condition);
				getUpList(filter, bTime_check, eTime_check);
				if (mDrawerLayout.isDrawerOpen(mFilterLayout)) {
					mDrawerLayout.closeDrawer(mFilterLayout);
				}
			}
		});
	}
	
	private void getInStockPutawayStatusCode(){
		final List<InStockPutawayStatus> statuses = new ArrayList<InStockPutawayStatus>();
		
		InStockPutawayStatus status0 = new InStockPutawayStatus();
		status0.setInStockPutawayStatusCode("");
		status0.setInStockPutawayStatusName("全部");
		
		InStockPutawayStatus status1 = new InStockPutawayStatus();
		status1.setInStockPutawayStatusCode("1");
		status1.setInStockPutawayStatusName("起草");
		
		InStockPutawayStatus status2 = new InStockPutawayStatus();
		status2.setInStockPutawayStatusCode("2");
		status2.setInStockPutawayStatusName("已上架");
		
		InStockPutawayStatus status3 = new InStockPutawayStatus();
		status3.setInStockPutawayStatusCode("3");
		status3.setInStockPutawayStatusName("取消");
		
		statuses.add(status0);
		statuses.add(status1);
		statuses.add(status2);
		statuses.add(status3);
		
		InStockPutawayStatusAdapter inStockPutawayStatusAdapter = new InStockPutawayStatusAdapter(UpListActivity.this, true);
		InStockPutawayStatus_sp.setAdapter(inStockPutawayStatusAdapter);
		inStockPutawayStatusAdapter.appendList(statuses);
		InStockPutawayStatus_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				InStockPutawayStatusCode = statuses.get(arg2).getInStockPutawayStatusCode();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void getUpList(FilterModel filter, String bTime_check, String eTime_check){
		startProgressDialog("Loading...");
		
		JSONStringer jsonStr = new JSONStringer();
		try {
			jsonStr = JSONHelper.serialize(jsonStr, filter);
		} catch (Exception e) {
			
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(UpListActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetInStockPutaway+"?bTime="+bTime_check+"&eTime="+eTime_check, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							CurrentIndex++;
							
							com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
							int Total = jsonObject.getIntValue("Total");
							if(Total%PageSize==0){
								CurrentIndex_Total = Total/PageSize;
							} else {
								CurrentIndex_Total = Total/PageSize + 1;
							}
							com.alibaba.fastjson.JSONArray json_array = (com.alibaba.fastjson.JSONArray) jsonObject.get("Rows");
							ups = JSONArray.parseArray(json_array.toString(), UpModel.class);
							
							if(upAdapter == null){
								upAdapter = new UpAdapter(UpListActivity.this, true);
								up_list.setAdapter(upAdapter);
								upAdapter.appendList(ups);
							} else {
								upAdapter.clear();
								upAdapter.appendList(ups);
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
	
	private void getMore(FilterModel filter, String bTime_check, String eTime_check){
		startProgressDialog("Loading...");
		
		JSONStringer jsonStr = new JSONStringer();
		try {
			jsonStr = JSONHelper.serialize(jsonStr, filter);
		} catch (Exception e) {
			
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(UpListActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetInStockPutaway+"?bTime="+bTime_check+"&eTime="+eTime_check, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							CurrentIndex++;
							
							com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
							com.alibaba.fastjson.JSONArray json_array = (com.alibaba.fastjson.JSONArray) jsonObject.get("Rows");
							ups = JSONArray.parseArray(json_array.toString(), UpModel.class);
							
							upAdapter.appendList(ups);
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
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		CurrentIndex = 1;
    	PageFilter=new PageFilterModel();
		PageFilter.setCurrentIndex(CurrentIndex+"");
		PageFilter.setPageSize("20");
		filter.setPageFilter(PageFilter);
		getUpList(filter, bTime_check, eTime_check);
	}
	
	private class FinishRefresh extends AsyncTask<Void, Void, Void>{  
		@Override  
		protected Void doInBackground(Void... params) {  
			return null;  
		}  
		   
		@Override  
		protected void onPostExecute(Void result){  
			up_list.onRefreshComplete();  
		}  
	}  
}
