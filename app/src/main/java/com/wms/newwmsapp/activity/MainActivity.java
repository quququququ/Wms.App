package com.wms.newwmsapp.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.CustGoodAdapter;
import com.wms.newwmsapp.adapter.StockCodeAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.CustGoodModel;
import com.wms.newwmsapp.model.StockModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyChooseToastDialog;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.tool.ShowMoreMenuPop;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.List;

@SuppressLint("InflateParams")
public class MainActivity extends BaseActivity {

	private long exitTimeMillis = System.currentTimeMillis();
	private DrawerLayout mDrawerLayout;
	private LinearLayout mFilterLayout;
	private LinearLayout instock_lin, up_lin,pickup_lin,outstockconfirm_lin,batchPickupconfirm_lin,seeding_lin,PDAPickupconfirm_lin,llZhuancang
	,seedingPickupconfirm_lin,StockInSearch_lin,StockCheckTask_lin,pickup_other,zhuancang_to_car;
	private LinearLayout kunei_action;
	private ListView stockCode_list;
	private GridView custGoodModel_grid;
	private StockCodeAdapter stockCodeAdapter;
	private CustGoodAdapter custGoodAdapter;
	private TextView stock_tv;
	private TextView person;
	private MyChooseToastDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mFilterLayout = (LinearLayout) findViewById(R.id.filter_layout);
		instock_lin = (LinearLayout) findViewById(R.id.instock_lin);
		up_lin = (LinearLayout) findViewById(R.id.up_lin);
		pickup_lin = (LinearLayout) findViewById(R.id.pickup_lin_main);
		StockInSearch_lin=(LinearLayout) findViewById(R.id.StockInSearch_lin);
		stockCode_list = (ListView) findViewById(R.id.stockCode_list);
		custGoodModel_grid = (GridView) findViewById(R.id.custGoodModel_grid);
		stock_tv = (TextView) findViewById(R.id.stock_tv);
		person = (TextView) findViewById(R.id.person);
		outstockconfirm_lin=(LinearLayout) findViewById(R.id.outstockconfirm_lin);
		batchPickupconfirm_lin=(LinearLayout) findViewById(R.id.batchPickupconfirm_lin);
		seeding_lin=(LinearLayout) findViewById(R.id.seeding_lin);
		PDAPickupconfirm_lin=(LinearLayout) findViewById(R.id.PDAPickupconfirm_lin);
		seedingPickupconfirm_lin=(LinearLayout) findViewById(R.id.seedingPickupconfirm_lin);
		StockCheckTask_lin = (LinearLayout) findViewById(R.id.StockCheckTaskByUserCode_lin);
		llZhuancang = (LinearLayout) findViewById(R.id.zhuancang_bozhong);
		pickup_other = (LinearLayout) findViewById(R.id.pickup_other);
		zhuancang_to_car = (LinearLayout) findViewById(R.id.zhuancang_to_car);
		kunei_action = (LinearLayout) findViewById(R.id.kunei_action);
		init();

		
		
		instock_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						InstockListActivity.class);
				startActivity(intent);
			}
		});

		up_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						UpListActivity.class);
				startActivity(intent);
			}
		});

		pickup_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						PickUpConfirmActivity.class);
				startActivity(intent);
			}
		});
		
		outstockconfirm_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
					OutStockConfirmActivity.class);
				startActivity(intent);
			}
		});
		
		batchPickupconfirm_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
					BatchPickupConfirmActivity.class);
				startActivity(intent);
			}
		});
		
		seeding_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
					SeedingActivity.class);
				startActivity(intent);
			}
		});
		
		//库内查询
		StockInSearch_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
					StockInSearchActivity.class);
				startActivity(intent);
			}
		});
		
		person.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startDialog();
			}
		});
		PDAPickupconfirm_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						PDAOutStockConfirmActivity.class);
				startActivity(intent);
			}
		});
		seedingPickupconfirm_lin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(MainActivity.this,SeedingPickupConfimActivity.class);
				startActivity(intent);
			}
			
		});
		StockCheckTask_lin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this,StockCheckTaskActivity.class);
				startActivity(intent);
			}
		});
		/**
		 * 转仓播种
		 */
		llZhuancang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this,SeedingOtherActivity.class);
				startActivity(intent);
			}
		});

		/**
		 * 转仓分拣
		 */
		pickup_other.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this,PickUpOtherActivity.class);
				intent.putExtra("isOther",true);
				startActivity(intent);
			}
		});

		/**
		 * 转仓装车
		 */
		zhuancang_to_car.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this,ZhuancangToCarActivity.class);
				startActivity(intent);
			}
		});

		/**
		 * 库内操作
		 */
		kunei_action.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ShowMoreMenuPop showMoreMenuPop = new ShowMoreMenuPop(MainActivity.this,getWindow());
				showMoreMenuPop.showUp();
//				showMoreMenuPop.showUp(kunei_action);
			}
		});
	}

	private void init() {
		code = preferences.getString("code", "");
		name = preferences.getString("name", "");
		if (TextUtils.isEmpty(code)) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		} else {
			String stocks_json = preferences.getString("stocks", "");
			com.alibaba.fastjson.JSONObject jsonObject = JSON
					.parseObject(stocks_json);
			com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject
					.get("StockList");
			final List<StockModel> stocks = JSONArray.parseArray(
					jsonArray.toString(), StockModel.class);

			
			if(preferences.getString("StockCode", "").isEmpty())
			{
				preferences.edit().putString("StockCode",stocks.get(0).getStockCode()).commit();
				stockCode=stocks.get(0).getStockCode();
			}else
			{
				
				stockCode=preferences.getString("StockCode", "");
			}
			stockCodeAdapter = new StockCodeAdapter(getApplicationContext(),
					true);
			stockCode_list.setAdapter(stockCodeAdapter);
			stockCodeAdapter.appendList(stocks);
			stockCode_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					stockCode = stocks.get(arg2).getStockCode();
					preferences.edit().putString("StockCode",stockCode).commit();
					custGoodsCode = "";
					custGoodsName = "";

					stock_tv.setText(stocks.get(arg2).getStockName());
					final List<CustGoodModel> custGoods = stocks.get(arg2)
							.getCustGoods();
					if (custGoodAdapter == null) {
						custGoodAdapter = new CustGoodAdapter(
								getApplicationContext(), true);
						custGoodModel_grid.setAdapter(custGoodAdapter);
						custGoodAdapter.appendList(custGoods);
					} else {
						custGoodAdapter.clear();
						custGoodAdapter.appendList(custGoods);
					}
					custGoodModel_grid
							.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									custGoodsCode = custGoods.get(arg2)
											.getCustGoodsCode();
									custGoodsName = custGoods.get(arg2)
											.getCustGoodsName();
								}
							});
				}
			});

			if (!mDrawerLayout.isDrawerOpen(mFilterLayout)) {
				mDrawerLayout.openDrawer(mFilterLayout);
			}

			getVersonCheck();
		}
	}

	private void getVersonCheck() {
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object().key("versionCode")
					.value(getVersionCode()).endObject();
		} catch (Exception e) {

		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_AppUpdate, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							boolean IsUpdate = response.getBoolean("IsUpdate");
							if (IsUpdate) {
								startUpdateDialog();
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
				}, this.handler);
		mRequestQueue.add(request);
	}

	private void startUpdateDialog() {
		progressDialog = MyToast.showChooseDialog(MainActivity.this,
				"已检测到最新版本，是否需要更新?", new android.view.View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						try {
							new downloadApkThread().start();
							progressDialog.dismiss();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private void startDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_person, null);
		final AlertDialog ad = new AlertDialog.Builder(MainActivity.this)
				.create();
		ad.setView(view);
		ad.show();

		TextView name_tv = (TextView) view.findViewById(R.id.name_tv);
		TextView out_tv = (TextView) view.findViewById(R.id.out_tv);
		TextView versionName = (TextView) view.findViewById(R.id.versionName);

		name_tv.setText(name);
		versionName.setText("版本号 : " + getVersionName());

		out_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				preferences.edit().putString("code", "").commit();
				preferences.edit().putString("name", "").commit();
				preferences.edit().putString("stocks", "").commit();

				ad.dismiss();
				Intent intent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - exitTimeMillis == 0
					|| currentTime - exitTimeMillis > 1500) {
				exitTimeMillis = System.currentTimeMillis();
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				return false;
			} else {
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
