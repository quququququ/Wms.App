package com.wms.newwmsapp.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.id;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.R.menu;
import com.wms.newwmsapp.adapter.InstockDetailAdapter;
import com.wms.newwmsapp.adapter.OutStockConfirmDetailAdapter;
import com.wms.newwmsapp.adapter.PickUpConfirmDetailAdapter;
import com.wms.newwmsapp.adapter.QualityAdapter;
import com.wms.newwmsapp.adapter.UnitAdapter;
import com.wms.newwmsapp.base.BaseScanActivity;
import com.wms.newwmsapp.model.InstockDetailModel;
import com.wms.newwmsapp.model.InstockModel;
import com.wms.newwmsapp.model.OutStockConfirmDetailModel;
import com.wms.newwmsapp.model.OutStockConfirmModel;
import com.wms.newwmsapp.model.PickUpConfirmDetailModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.JSONHelper;
import com.wms.newwmsapp.tool.JsonDateFormate;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.tool.SoundUtils;
import com.wms.newwmsapp.tool.TimeDatePicker;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;
import com.zltd.industry.ScannerManager;
import com.zltd.industry.ScannerManager.IScannerStatusListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint({ "InflateParams", "HandlerLeak" })
public class OutStockConfirmDetailActivity extends BaseScanActivity {
	public static Handler myHandler;
	private ImageView back;
	private LinearLayout operator_lin;
	private LinearLayout save_lin;
	private LinearLayout cancle_lin;
	private LinearLayout submit_lin;
	
	private List<OutStockConfirmDetailModel> details = new ArrayList<OutStockConfirmDetailModel>();
	private OutStockConfirmDetailAdapter adapter;
	private ListView detail_listview;
	private OutStockConfirmModel outStockConfirm;
	private OutStockConfirmDetailModel model;
	private TextView CustomerCode_et;
	

	private SoundUtils mSoundUtils;
	private ScannerManager mScannerManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_out_stock_confirm_detail);
		

		// 初始化扫描
		//initSound();
		
		myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					final int position = (Integer) msg.obj;
				    model=details.get(position);
					startDialog(model,position);
					
					break;
				case 2:
					OutStockConfirmDetailModel detail=(OutStockConfirmDetailModel) msg.obj;
					details.remove(detail);
					if (adapter == null) {
						adapter = new OutStockConfirmDetailAdapter(getApplicationContext(), true);
						detail_listview.setAdapter(adapter);
						adapter.appendList(details);
					} else {
						adapter.clear();
						adapter.appendList(details);
					}
					break;
				}
			}
		};

		back = (ImageView) findViewById(R.id.back);
		detail_listview=(ListView) findViewById(R.id.up_list);
		operator_lin = (LinearLayout) findViewById(R.id.operator_lin);
		save_lin=(LinearLayout) findViewById(R.id.save_lin);
		cancle_lin=(LinearLayout) findViewById(R.id.cancle_lin);
		submit_lin=(LinearLayout) findViewById(R.id.submit_lin);
		CustomerCode_et=(TextView) findViewById(R.id.CustomerCode_et);
		
		operator_lin.setVisibility(View.VISIBLE);
		
		Intent intent = getIntent();
		if(intent!=null)
		{
			outStockConfirm=(OutStockConfirmModel)intent.getSerializableExtra("OutStock");
		}
		
		getList(outStockConfirm.getCode());
		
		CustomerCode_et.setOnEditorActionListener(new TextView.OnEditorActionListener() { 

			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)  {                          

				if (actionId==EditorInfo.IME_ACTION_SEARCH ||(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					GetCheckList();
				return true;             
				}               
				return false;           
			}       
		});
		
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		cancle_lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SubmitCancleOutStock(outStockConfirm.getCode(),name,true);
			}
		});
		
		save_lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Save(details);
			}
		});
		
		submit_lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SubmitCancleOutStock(outStockConfirm.getCode(),name,false);
			}
		});
		
	}

	private void GetCheckList() {
		List<OutStockConfirmDetailModel> new_details = new ArrayList<OutStockConfirmDetailModel>();
		if (TextUtils.isEmpty(CustomerCode_et.getText().toString())) {
			new_details.addAll(details);
		} else {
			for (int i = 0; i < details.size(); i++) {
				if (CustomerCode_et.getText().toString()
						.equals(details.get(i).getCustomerCode())) {
					new_details.add(details.get(i));
				}
			}
		}

		if (adapter == null) {
			adapter = new OutStockConfirmDetailAdapter(getApplicationContext(),
					true);
			detail_listview.setAdapter(adapter);
			adapter.appendList(new_details);
		} else {
			adapter.clear();
			adapter.appendList(new_details);
		}

	}
	
	
	private void SubmitCancleOutStock(String outStockConfirmCode,String userName,boolean isCancel)
	{
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object()
					.key("code").value(outStockConfirmCode)
					.key("userName").value(userName)
					.key("isCancel").value(isCancel)
					.endObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(OutStockConfirmDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_OutStockCheckAudit, jsonStr.toString(),
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						MyToast.showDialog(OutStockConfirmDetailActivity.this,"操作成功！");
						stopProgressDialog();
						Intent intent= new Intent();
						intent.setClass(OutStockConfirmDetailActivity.this, OutStockConfirmActivity.class);
						startActivity(intent);
						finish();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						stopProgressDialog();
					}
				}, handler);
		mRequestQueue.add(request);
		
	}
	private void startDialog(final OutStockConfirmDetailModel detail, final int position) {
	
		LayoutInflater inflater = getLayoutInflater();
		final View view = inflater.inflate(R.layout.dialog_showedittext, null);
		final AlertDialog ad = new AlertDialog.Builder(OutStockConfirmDetailActivity.this).create();
		ad.setView(view);
		ad.show();

	   final TextView Num = (TextView) view.findViewById(R.id.txt_Num);
		Num.setText(detail.getNum()+"");
		
		TextView button_ok = (TextView) view.findViewById(R.id.btn_ok);
		TextView button_cancle = (TextView) view.findViewById(R.id.btn_cancel);

		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(Integer.parseInt(Num.getText().toString())>detail.getLoc_Num()){
					MyToast.showDialog(OutStockConfirmDetailActivity.this, "不可大于指定收货数量");
					ad.dismiss();
				} else {
				    detail.setNum(Integer.parseInt(Num.getText().toString()));
					if (adapter == null) { 
						adapter = new OutStockConfirmDetailAdapter(getApplicationContext(), true);
						detail_listview.setAdapter(adapter);
						adapter.appendList(details);
						
					} else {
						adapter.clear();
						adapter.appendList(details);
					}
					
					ad.dismiss();
				}
			}
		});
		
		button_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			ad.dismiss();	
			}
		});
	}
	
	// 获取收货单对应的货品列表
		private void getList(String Code) {
			startProgressDialog("Loading...");
			JSONStringer jsonStr = null;
			try {
				jsonStr = new JSONStringer().object()
						.key("code").value(Code)
						.endObject();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			RequestQueue mRequestQueue = Volley.newRequestQueue(OutStockConfirmDetailActivity.this);
			JsonObjectRequest request = new JsonObjectRequest(Method.POST,
					Constants.url_GetOutStockCheckDetailByCode, jsonStr.toString(),
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
							com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
							details = JSONArray.parseArray(jsonArray.toString(), OutStockConfirmDetailModel.class);
							//将时间格式全部进行修改
							for(int i=0;i<details.size();i++){
								details.get(i).setProductionDate(JsonDateFormate.dataFormate(details.get(i).getProductionDate()));
								details.get(i).setCreateDate(JsonDateFormate.dataFormate(details.get(i).getCreateDate()));
								details.get(i).setUpdateDate(JsonDateFormate.dataFormate(details.get(i).getUpdateDate()));
								details.get(i).setLoc_Num(details.get(i).getNum());
							}

							if (adapter == null) {
								adapter = new OutStockConfirmDetailAdapter(getApplicationContext(), true);
								detail_listview.setAdapter(adapter);
								adapter.appendList(details);
							} else {
								adapter.clear();
								adapter.appendList(details);
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
	
		private void Save(List<OutStockConfirmDetailModel> detail) {
			startProgressDialog("Loading...");

			JSONStringer jsonStr = new JSONStringer();
			String json_Str = null;
			try {
				jsonStr = JSONHelper.serialize(jsonStr, detail);
				jsonStr = new JSONStringer().object().key("list").value(jsonStr)
						.endObject();
				json_Str = JsonDateFormate.JsonFormate(jsonStr.toString());
			} catch (Exception e) {

			}

			RequestQueue mRequestQueue = Volley
					.newRequestQueue(OutStockConfirmDetailActivity.this);
			JsonObjectRequest request = new JsonObjectRequest(Method.POST,
					Constants.url_OutStockCheckDetailSave+"?code="+outStockConfirm.getCode(), json_Str,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							try {
								MyToast.showDialog(
										OutStockConfirmDetailActivity.this, "保存成功");
								getList(outStockConfirm.getCode());
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
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.out_stock_confirm_detail, menu);
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
	
//	private void initSound() {
//		mSoundUtils = SoundUtils.getInstance();
//		mSoundUtils.init(this);
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		initScanner();
//	}

//	private void initScanner() {
//
//		try {
//			// 初始化扫描
//			mScannerManager = ScannerManager.getInstance();
//			mScannerManager.scannerEnable(true);// 扫描可用
//
//			mScannerManager.setScanMode(ScannerManager.SCAN_SINGLE_MODE);// 单扫
//			// mScannerManager.setScanMode(ScannerManager.SCAN_CONTINUOUS_MODE);//连扫
//
//			mScannerManager.setDataTransferType(ScannerManager.TRANSFER_BY_API);// API获取扫描数据
//			// mScannerManager.setDataTransferType(ScannerManager.TRANSFER_BY_EDITTEXT);//
//			// 填充文本编辑框
//			// mScannerManager.setDataTransferType(ScannerManager.TRANSFER_BY_KEY);//
//			// 转换为按键发送
//
//			// 注册监听
//			//mScannerManager.addScannerStatusListener(mIScannerStatusListener);
//		} catch (Exception e) {
//			Toast.makeText(getApplicationContext(), "程序只能在N2S机器上运行",
//					Toast.LENGTH_SHORT).show();
//		}
//	}

//	private IScannerStatusListener mIScannerStatusListener = new IScannerStatusListener() {
//
//		@Override
//		public void onScannerStatusChanage(int paramInt) {
//
//			System.out.println("onScannerStatusChanage-------" + paramInt);
//		}
//
//		@Override
//		public void onScannerResultChanage(final byte[] paramArrayOfByte) {
//			mSoundUtils.success();
//			handler.post(new Runnable() {
//
//				@Override
//				public void run() {
//					String s = null;
//					try {
//						s = new String(paramArrayOfByte, "UTF-8");
//					} catch (UnsupportedEncodingException ex) {
//						ex.printStackTrace();
//					}
//					if (s != null) {
//						CustomerCode_et.setText(s);
//						GetCheckList();
//					}
//				}
//			});
//		}
//	};
//
//	@Override
//	protected void onDestroy() {
//		// 移除监听
//		if (mScannerManager != null) {
//			mScannerManager
//					.removeScannerStatusListener(mIScannerStatusListener);
//		}
//		mScannerManager.scannerEnable(false);
//		mScannerManager = null;
//		super.onDestroy();
//
//	}
}
