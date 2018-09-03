package com.wms.newwmsapp.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.InstockDetailAdapter;
import com.wms.newwmsapp.adapter.QualityAdapter;
import com.wms.newwmsapp.adapter.UnitAdapter;
import com.wms.newwmsapp.base.BaseScanActivity;
import com.wms.newwmsapp.model.InstockDetailModel;
import com.wms.newwmsapp.model.InstockModel;
import com.wms.newwmsapp.model.QualityMode;
import com.wms.newwmsapp.model.UnitModel;
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

@SuppressLint({ "InflateParams", "HandlerLeak" })
public class InstockDetailActivity extends BaseScanActivity {

	public static Handler myHandler;
	private ImageView back;
	private InstockModel instock;// 收货单
	private List<InstockDetailModel> instockdetails = new ArrayList<InstockDetailModel>();// 收货单对应的货品list
	private List<QualityMode> qualitys;// 货品对应可选的品质列表
	private List<UnitModel> units;// 货品对应可选的单位列表
	private String QualityCode = "";// 选中的品质
	private String QualityName = "";// 选中的品质名称
	private String GoodsUnitCode = "";// 选中的单位
	private String UnitName = "";// 选中的单位名称
	private TextView Code;
	private EditText PurchaseNO, Remark;
	private Button InformDate;
	private ListView instockdetail_list;
	private InstockDetailAdapter instockDetailAdapter;
	private LinearLayout add_lin;
	private TextView ok_tv;
	private TextView ScanCutstomer;

	private SoundUtils mSoundUtils;
	private ScannerManager mScannerManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instockdetail);
		

		// 初始化扫描
		//initSound();
		
		myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					int position = (Integer) msg.obj;
					InstockDetailModel instockDetaill = instockdetails.get(position);
					String CustomerCode = instockDetaill.getCustomerCode();
					// 修改货品信息
					getQualityList(CustomerCode, instockDetaill, position);
					break;
				case 2:
					InstockDetailModel instockDetail=(InstockDetailModel) msg.obj;
					instockdetails.remove(instockDetail);
					if (instockDetailAdapter == null) {
						instockDetailAdapter = new InstockDetailAdapter(getApplicationContext(), true);
						instockdetail_list.setAdapter(instockDetailAdapter);
						instockDetailAdapter.appendList(instockdetails);
					} else {
						instockDetailAdapter.clear();
						instockDetailAdapter.appendList(instockdetails);
					}
					break;
				}
			}
		};

		back = (ImageView) findViewById(R.id.back);
		Code = (TextView) findViewById(R.id.Code);
	
		instockdetail_list = (ListView) findViewById(R.id.instockdetail_list);
		add_lin = (LinearLayout) findViewById(R.id.add_lin);
		ok_tv = (TextView) findViewById(R.id.ok_tv);
		ScanCutstomer=(TextView)findViewById(R.id.ScanCustomerCode);
		
		Intent intent = getIntent();
		if (intent != null) {
			instock = (InstockModel) intent.getSerializableExtra("instock");
			if (instock != null) {
				init(instock);
			}
			else
			{
				instock = new InstockModel();
			}
		}
		
		//扫码enter键
		ScanCutstomer.setOnKeyListener(new View.OnKeyListener() {   
          @Override  
          public boolean onKey(View v, int keyCode, KeyEvent event) {  
              //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次  
              if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {  
                  //处理事件  
               Scanner(); 
               ScanCutstomer.setText("");
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

		add_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startCustomerCodeDialog();
			}
		});

		ok_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LayoutInflater  inflater=getLayoutInflater();
				View view = inflater.inflate(R.layout.dialog_instcokdetailsubmit,null);
				final AlertDialog ad= new AlertDialog.Builder(InstockDetailActivity.this).create();
				ad.setView(view);
				ad.show();
				
				InformDate = (Button) view.findViewById(R.id.InformDate);
				PurchaseNO = (EditText) view.findViewById(R.id.PurchaseNO);
				Remark = (EditText) view.findViewById(R.id.Remark);
				
				new TimeDatePicker().selectTime(InformDate, InstockDetailActivity.this, InstockDetailActivity.this);
				PurchaseNO.setText(instock.getPurchaseNO());
				InformDate.setText(JsonDateFormate.getDate());
				Remark.setText(instock.getRemark());
				
				Button btn=(Button) view.findViewById(R.id.InstockSubmit_ok);
				
				btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						boolean flag = true;
						if(instockdetails.size()!=0){
							for(int i=0;i<instockdetails.size();i++){
								InstockDetailModel instockdetail = instockdetails.get(i);
								if(instockdetail.getLoc_Num()== 0){
									flag = false;
									MyToast.showDialog(InstockDetailActivity.this, "存在收货数量为0的明细行");
									break;
								}
							}
						} else {
							flag = false;
							MyToast.showDialog(InstockDetailActivity.this, "明细表不能为空");
						}
						
						if(flag){
							if(instock != null){
								commit();
							} else if(!TextUtils.isEmpty(custGoodsCode)){
								commit();
							} else {
								MyToast.showDialog(InstockDetailActivity.this, "此为无指示入库,请选择货主");
							}
						}
					}
				});
				
				
			}
		});

		// 扫码返回结果
//		mReceiver = new BroadcastReceiver() {
//			@Override
//			public void onReceive(Context context, Intent intent) {
//				// 此处获取扫描结果信息
//				String scanResult = intent.getStringExtra("EXTRA_SCAN_DATA");
//				//若扫描后在明细列表中存在-自动加1
//				boolean isAdd = true;
//				for(int i=0;i<instockdetails.size();i++){
//					if(scanResult.equals(instockdetails.get(i).getCustomerCode())){
//						if((instockdetails.get(i).getNum()!=-1)&&(instockdetails.get(i).getLoc_Num()>=instockdetails.get(i).getNum())){
//							MyToast.showDialog(InstockDetailActivity.this, "不可大于指定收货数量");
//						} else {
//							instockdetails.get(i).setLoc_Num(instockdetails.get(i).getLoc_Num()+1);
//							if (instockDetailAdapter == null) {
//								instockDetailAdapter = new InstockDetailAdapter(getApplicationContext(), true);
//								instockdetail_list.setAdapter(instockDetailAdapter);
//								instockDetailAdapter.appendList(instockdetails);
//							} else {
//								instockDetailAdapter.clear();
//								instockDetailAdapter.appendList(instockdetails);
//							}
//						}
//						isAdd=false;
//						break;
//					}
//				}
//				//若不存在，则新增一条明细
//				if(isAdd){
//					if(instock!=null){
//						getInstockDetail(scanResult, instock.getCustGoodsCode());
//					} else if(!TextUtils.isEmpty(custGoodsCode)) {
//						getInstockDetail(scanResult, custGoodsCode);
//					} else {
//						MyToast.showDialog(InstockDetailActivity.this, "请选择货主");
//					}
//				}
//			}
//		};
	}

	private void init(InstockModel instock) {
		Code.setText(instock.getCode());
		getList(instock);
	}

	// 获取收货单对应的货品列表
	private void getList(InstockModel instock) {
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object()
					.key("instockParams").object()
						.key("InstockDate").value("")
						.key("PurchaseNo").value("")
						.key("StockCode").value(stockCode)
						.key("BarCode").value("")
						.key("InstockCode").value(instock.getCode())
						.endObject()
					.endObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(InstockDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetInstockDetail, jsonStr.toString(),
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
						com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
						instockdetails = JSONArray.parseArray(jsonArray.toString(), InstockDetailModel.class);
						//将时间格式全部进行修改
						for(int i=0;i<instockdetails.size();i++){
							instockdetails.get(i).setProductionDate(JsonDateFormate.dataFormate(instockdetails.get(i).getProductionDate()));
							instockdetails.get(i).setCreateDate(JsonDateFormate.dataFormate(instockdetails.get(i).getCreateDate()));
							instockdetails.get(i).setUpdateDate(JsonDateFormate.dataFormate(instockdetails.get(i).getUpdateDate()));
							instockdetails.get(i).setInformDate(JsonDateFormate.dataFormate(instockdetails.get(i).getInformDate()));
						}

						if (instockDetailAdapter == null) {
							instockDetailAdapter = new InstockDetailAdapter(getApplicationContext(), true);
							instockdetail_list.setAdapter(instockDetailAdapter);
							instockDetailAdapter.appendList(instockdetails);
						} else {
							instockDetailAdapter.clear();
							instockDetailAdapter.appendList(instockdetails);
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

	
	private void Scanner()
	{
		String CustomerCode=ScanCutstomer.getText().toString();
		boolean isAdd = true;
		for(int i=0;i<instockdetails.size();i++){
			if(CustomerCode.equals(instockdetails.get(i).getCustomerCode())){
				if((instockdetails.get(i).getNum()!=-1)&&(instockdetails.get(i).getLoc_Num()>=instockdetails.get(i).getNum())){
					MyToast.showDialog(InstockDetailActivity.this, "不可大于指定收货数量");
				} else {
					instockdetails.get(i).setLoc_Num(instockdetails.get(i).getLoc_Num()+1);
					if (instockDetailAdapter == null) {
						instockDetailAdapter = new InstockDetailAdapter(getApplicationContext(), true);
						instockdetail_list.setAdapter(instockDetailAdapter);
						instockDetailAdapter.appendList(instockdetails);
					} else {
						instockDetailAdapter.clear();
						instockDetailAdapter.appendList(instockdetails);
					}
				}
				isAdd=false;
				break;
			}
		}
		//若不存在，则新增一条明细
		if(isAdd){
			if(instock!=null){
				getInstockDetail(CustomerCode, instock.getCustGoodsCode());
			} else if(!TextUtils.isEmpty(custGoodsCode)) {
				getInstockDetail(CustomerCode, custGoodsCode);
			} else {
				MyToast.showDialog(InstockDetailActivity.this, "请选择货主");
			}
		}
	}
	
	private void startCustomerCodeDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_customercode, null);
		final AlertDialog ad = new AlertDialog.Builder(InstockDetailActivity.this).create();
		ad.setView(view);
		ad.show();

		final EditText CustomerCode_et = (EditText) view.findViewById(R.id.CustomerCode);
		Button button_ok = (Button) view.findViewById(R.id.button_ok);

		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String CustomerCode = CustomerCode_et.getText().toString().trim();
				
				if(TextUtils.isEmpty(CustomerCode))
				{
					MyToast.showDialog(InstockDetailActivity.this, "不能输入为空");
					return;
				}
				
				boolean isAdd = true;
				for(int i=0;i<instockdetails.size();i++){
					if(CustomerCode.equals(instockdetails.get(i).getCustomerCode())){
						if((instockdetails.get(i).getNum()!=-1)&&(instockdetails.get(i).getLoc_Num()>=instockdetails.get(i).getNum())){
							MyToast.showDialog(InstockDetailActivity.this, "不可大于指定收货数量");
						} else {
							instockdetails.get(i).setLoc_Num(instockdetails.get(i).getLoc_Num()+1);
							if (instockDetailAdapter == null) {
								instockDetailAdapter = new InstockDetailAdapter(getApplicationContext(), true);
								instockdetail_list.setAdapter(instockDetailAdapter);
								instockDetailAdapter.appendList(instockdetails);
							} else {
								instockDetailAdapter.clear();
								instockDetailAdapter.appendList(instockdetails);
							}
						}
						isAdd=false;
						break;
					}
				}
				//若不存在，则新增一条明细
				if(isAdd){
					if(instock!=null){
						getInstockDetail(CustomerCode, instock.getCustGoodsCode());
					} else if(!TextUtils.isEmpty(custGoodsCode)) {
						getInstockDetail(CustomerCode, custGoodsCode);
					} else {
						MyToast.showDialog(InstockDetailActivity.this, "请选择货主");
					}
				}
				
				ad.dismiss();
			}
		});
	}

	// 增加货品明细
	private void getInstockDetail(final String CustomerCode, String CustGoodsCode) {
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object()
					.key("instockParams").object()
						.key("Code").value(CustomerCode)
						.key("InstockDate").value("")
						.key("PurchaseNo").value("")
						.key("StockCode").value(stockCode)
						.key("BarCode").value("")
						.key("InstockCode").value("")
						.key("CustGoodsCode").value(CustGoodsCode)
					.endObject().endObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(InstockDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetGoodsDetailByCode, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
						com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
						InstockDetailModel instockdetail = (JSONArray.parseArray(jsonArray.toString(),InstockDetailModel.class)).get(0);
						//应收数量强行转成-1
						instockdetail.setNum(-1);
						//实收数量强行转成1
						instockdetail.setLoc_Num(1);
						//品质和单位默认
						instockdetail.setQualityCode("lp");//品质code
						instockdetail.setQualityName("良品");//品质名称
						instockdetail.setGoodsUnitCode(instockdetail.getGoodsUnitCode());//单位
						instockdetail.setUnitName(instockdetail.getUnitName());//单位名称
						//时间强行转码
						instockdetail.setProductionDate(JsonDateFormate.dataFormate(instockdetail.getProductionDate()));
						instockdetail.setCreateDate(JsonDateFormate.dataFormate(instockdetail.getCreateDate()));
						instockdetail.setUpdateDate(JsonDateFormate.dataFormate(instockdetail.getUpdateDate()));
						instockdetail.setInformDate(JsonDateFormate.dataFormate(instockdetail.getInformDate()));
						
						instockdetails.add(instockdetail);
						if (instockDetailAdapter == null) {
							instockDetailAdapter = new InstockDetailAdapter(getApplicationContext(), true);
							instockdetail_list.setAdapter(instockDetailAdapter);
							instockDetailAdapter.appendList(instockdetails);
						} else {
							instockDetailAdapter.clear();
							instockDetailAdapter.appendList(instockdetails);
						}
						
						//MyToast.showDialog(InstockDetailActivity.this, "请选择品质和单位信息");

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

	// 获取对应货品可选的品质列表
	private void getQualityList(final String CustomerCode, final InstockDetailModel instockdetail, final int position) {
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object()
					.key("instockParams").object()
						.key("Code").value(CustomerCode)
						.key("InstockDate").value("")
						.key("PurchaseNo").value("")
						.key("StockCode").value(stockCode)
						.key("BarCode").value("")
						.key("InstockCode").value("")
					.endObject().endObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(InstockDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetAllQuality, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
						com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
						qualitys = JSONArray.parseArray(jsonArray.toString(),QualityMode.class);

						// 获取对应货品可选的单位列表
						getUnitList(CustomerCode, instockdetail, position);

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

	// 获取对应货品可选的单位列表
	private void getUnitList(String CustomerCode, final InstockDetailModel instockdetail, final int position) {
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object()
					.key("instockParams").object()
						.key("Code").value(CustomerCode)
						.key("InstockDate").value("")
						.key("PurchaseNo").value("")
						.key("StockCode").value(stockCode)
						.key("BarCode").value("")
						.key("InstockCode").value("")
						.endObject()
					.endObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(InstockDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetAllUnityByGoodsCode, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
						com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
						units = JSONArray.parseArray(jsonArray.toString(),UnitModel.class);

						// 开始修改或者新建
						startDialog(instockdetail, position);

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

	private void startDialog(final InstockDetailModel instockdetail, final int position) {
		QualityCode = "";
		QualityName = "";
		GoodsUnitCode = "";
		UnitName = "";
		
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_instockdetail, null);
		final AlertDialog ad = new AlertDialog.Builder(InstockDetailActivity.this).create();
		ad.setView(view);
		ad.show();

		TextView CustomerCode = (TextView) view.findViewById(R.id.CustomerCode);
		TextView GoodsName = (TextView) view.findViewById(R.id.GoodsName);
		final Button ProductionDate = (Button) view.findViewById(R.id.ProductionDate);
		new TimeDatePicker().selectTime(ProductionDate, InstockDetailActivity.this, InstockDetailActivity.this);
		final EditText ProductId = (EditText) view.findViewById(R.id.ProductId);
		final EditText BoxId = (EditText) view.findViewById(R.id.BoxId);
		final EditText Loc_Num = (EditText) view.findViewById(R.id.Loc_Num);
		final EditText StandUnitName = (EditText) view.findViewById(R.id.StandUnitName);
		Spinner QualityName_tv = (Spinner) view.findViewById(R.id.QualityName);
		Spinner UnitName_tv = (Spinner) view.findViewById(R.id.UnitName);
		Button button_ok = (Button) view.findViewById(R.id.button_ok);

		CustomerCode.setText(instockdetail.getCustomerCode());
		GoodsName.setText(instockdetail.getGoodsName());
		ProductionDate.setText(instockdetail.getProductionDate());
		ProductId.setText(instockdetail.getProductId());
		BoxId.setText(instockdetail.getBoxId());
		Loc_Num.setText(instockdetail.getLoc_Num()+"");
		StandUnitName.setText(instockdetail.getStandUnitName());

		QualityAdapter qualityAdapter = new QualityAdapter(getApplicationContext(), true);
		QualityName_tv.setAdapter(qualityAdapter);
		qualityAdapter.appendList(qualitys);
		for (int i = 0; i < qualitys.size(); i++) {
			if (qualitys.get(i).getCode().equals(instockdetail.getQualityCode())) {
				QualityName_tv.setSelection(i);
				break;
			}
		}
		QualityName_tv.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				QualityCode = qualitys.get(arg2).getCode();
				QualityName = qualitys.get(arg2).getName();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		UnitAdapter unitAdapter = new UnitAdapter(getApplicationContext(), true);
		UnitName_tv.setAdapter(unitAdapter);
		unitAdapter.appendList(units);
		for (int j = 0; j < units.size(); j++) {
			if (units.get(j).getCode().equals(instockdetail.getGoodsUnitCode())) {
				UnitName_tv.setSelection(j);
				break;
			}
		}
		UnitName_tv.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				GoodsUnitCode = units.get(arg2).getCode();
				UnitName = units.get(arg2).getUnitName();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if((instockdetail.getNum()!=-1)&&(Integer.parseInt(Loc_Num.getText().toString())>instockdetail.getNum())){
					MyToast.showDialog(InstockDetailActivity.this, "不可大于指定收货数量");
				} else {
					instockdetail.setProductionDate(ProductionDate.getText().toString());
					instockdetail.setProductId(ProductId.getText().toString());
					instockdetail.setBoxId(BoxId.getText().toString());
					if(TextUtils.isEmpty(Loc_Num.getText().toString())){
						instockdetail.setLoc_Num(0);
					} else {
						instockdetail.setLoc_Num(Integer.parseInt(Loc_Num.getText().toString()));
					}
					instockdetail.setStandUnitName(StandUnitName.getText().toString());
					instockdetail.setQualityCode(QualityCode);
					instockdetail.setQualityName(QualityName);
					instockdetail.setGoodsUnitCode(GoodsUnitCode);
					instockdetail.setUnitName(UnitName);
					
					instockdetails.remove(position);
					instockdetails.add(instockdetail);
					if (instockDetailAdapter == null) {
						instockDetailAdapter = new InstockDetailAdapter(getApplicationContext(), true);
						instockdetail_list.setAdapter(instockDetailAdapter);
						instockDetailAdapter.appendList(instockdetails);
					} else {
						instockDetailAdapter.clear();
						instockDetailAdapter.appendList(instockdetails);
					}
					
					ad.dismiss();
				}
			}
		});
	}

	private void commit(){
		startProgressDialog("Loading...");
		
		for(int i=0;i<instockdetails.size();i++){
			instockdetails.get(i).setNum(instockdetails.get(i).getLoc_Num());
		}
		
		String Code = "";
		if(instock!=null){
			Code = instock.getCode();
		}
		
		JSONStringer jsonStr = new JSONStringer();
		String json_Str = null;
		try {
			jsonStr = JSONHelper.serialize(jsonStr, instockdetails);
			jsonStr = new JSONStringer().object()
					.key("InstockSave").object()
						.key("data").value(jsonStr)
						.key("instockCode").value(Code)
						.key("InstockDate").value(InformDate.getText().toString())
						.key("Remark").value(Remark.getText().toString())
						.key("StockCode").value(stockCode)
						.key("CustomerGoodsCode").value(custGoodsCode)
						.key("CustomerGoodsName").value(custGoodsName)
						.key("UserName").value(name)
						.key("PurchaseNo").value(PurchaseNO.getText().toString())
					.endObject().endObject();
			json_Str = JsonDateFormate.JsonFormate(jsonStr.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("走不走",json_Str+"");
		RequestQueue mRequestQueue = Volley.newRequestQueue(InstockDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_SaveAndCommitInStockCheck, json_Str,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						finish();
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
//						ScanCutstomer.setText(s);
//						Scanner();
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
