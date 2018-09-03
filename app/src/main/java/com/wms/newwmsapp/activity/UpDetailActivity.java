package com.wms.newwmsapp.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.UpDetailAdapter;
import com.wms.newwmsapp.base.BaseScanActivity;
import com.wms.newwmsapp.model.GoodsAreaModel;
import com.wms.newwmsapp.model.GoodsPosModel;
import com.wms.newwmsapp.model.PickUpConfirmDetailModel;
import com.wms.newwmsapp.model.UpDetailModel;
import com.wms.newwmsapp.model.UpModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.JSONHelper;
import com.wms.newwmsapp.tool.JsonDateFormate;
import com.wms.newwmsapp.tool.ListViewScrollView;
import com.wms.newwmsapp.tool.MyTimeToastDialog;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

@SuppressLint({ "HandlerLeak", "InflateParams" })
public class UpDetailActivity extends BaseScanActivity {

	public static Handler myHandler;
	private ImageView back;
	private EditText CustomerCode_et;
	private LinearLayout operator_lin;
	private ListViewScrollView  up_list;
	private UpModel up;
	private List<UpDetailModel> updetails;
	private UpDetailAdapter upDetailAdapter2;
	
	private int goodsposPosition = -1;
	private String goodsPosName = "";
	private String loc_num = "";
	
	private LinearLayout save_lin, up_lin; 
	private MyTimeToastDialog progressDialog;
	
	private boolean flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updetail);
		
		myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String loc_Code = (String) msg.obj;
					for(int i=0;i<updetails.size();i++){
						if(loc_Code.equals(updetails.get(i).getCode())){
							UpDetailModel updetail = updetails.get(i);
							startDialog(updetail);
							break;
						}
					}
					break;
				case 2:
					@SuppressWarnings("unchecked")
					Map<String,String> map = (Map<String, String>) msg.obj;
					int chilren_position = Integer.parseInt(map.get("chilren_position"));
					String Code = map.get("Code");
					for(int i=0;i<updetails.size();i++){
						if(Code.equals(updetails.get(i).getCode())){
							updetails.get(i).getList().remove(chilren_position);
							break;
						}
					}
					
					getCheckList();
					break;
				}
			}
		};
		
		back = (ImageView) findViewById(R.id.back);
		CustomerCode_et = (EditText) findViewById(R.id.CustomerCode_et);
		operator_lin = (LinearLayout) findViewById(R.id.operator_lin);
		up_list = (ListViewScrollView ) findViewById(R.id.up_list);
		save_lin = (LinearLayout ) findViewById(R.id.save_lin);
		up_lin = (LinearLayout ) findViewById(R.id.up_lin);
		
		Intent intent = getIntent();
		up = (UpModel) intent.getSerializableExtra("up");
		
		flag = false;
		//起草状态
		if(up.getInStockPutawayStatusCode().equals("1")){
			operator_lin.setVisibility(View.VISIBLE);
		}
		getList(up.getCode());
		
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		CustomerCode_et.setOnEditorActionListener(new TextView.OnEditorActionListener() { 

			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)  {                          

				if (actionId==EditorInfo.IME_ACTION_SEARCH ||(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					getCheckList();
					return true;             
				}               
				return false;           
			}       
		});
		
		save_lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				boolean isOk = true;
				final List<GoodsPosModel> last_goodspos = new ArrayList<GoodsPosModel>();
				int size = 0;
				for(int i=0;i<updetails.size();i++){
					int Num = updetails.get(i).getNum();
					int Loc_Num = 0;
					size += updetails.get(i).getList().size();
					List<GoodsPosModel> goodspos = updetails.get(i).getList();
					for(int r=0;r<goodspos.size();r++){
						GoodsPosModel goodpos = goodspos.get(r);
						Loc_Num+=goodpos.getLoc_Num();
						if(goodpos.getLoc_Num()!=0){
							last_goodspos.add(goodpos);
						}
					}
					if(Num!=Loc_Num){
						MyToast.showDialog(UpDetailActivity.this, "货品编码:"+updetails.get(i).getCustomerCode()+",上架数量不符合");
						isOk = false;
					}
				}
				
				if(isOk){
					if (size != last_goodspos.size()) 
					{
						
						LayoutInflater inflater = getLayoutInflater();
						View view = inflater.inflate(R.layout.dialog_showchoosedialog, null);
						final AlertDialog ad = new AlertDialog.Builder(UpDetailActivity.this).create();
						ad.setView(view);
						ad.show();
						
						TextView txtMessage=(TextView)view.findViewById(R.id.message);
						txtMessage.setText("有数量为0的货品，是否删除？");
						TextView  btnOk=(TextView )view.findViewById(R.id.btn_ok);
						btnOk.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								for(int r=0;r<last_goodspos.size();r++){
									GoodsPosModel goodpos = last_goodspos.get(r);
									goodpos.setNum(goodpos.getLoc_Num());
								}
							    save(last_goodspos);
							    ad.dismiss();
							}
						});
						
						TextView  btnCancle=(TextView )view.findViewById(R.id.btn_cancel);
						btnCancle.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								ad.dismiss();
							}
						});
					}
					
						
					
				}
			}
		});
		
		up_lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progressDialog = MyToast.showTimeDialog(UpDetailActivity.this, "请选择上架时间", JsonDateFormate.getDate(), new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String PutawayDate = progressDialog.getTime();
						goUp(PutawayDate);
					}
				});
			}
		});
	}
	
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

		RequestQueue mRequestQueue = Volley.newRequestQueue(UpDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetInStockPutAwayDetail, jsonStr.toString(),
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
						com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
						updetails = JSONArray.parseArray(jsonArray.toString(), UpDetailModel.class);
						// 将时间格式全部进行修改
						for (int i = 0; i < updetails.size(); i++) {
							UpDetailModel updetail = updetails.get(i);
							updetail.setProductionDate(JsonDateFormate.dataFormate(updetail.getProductionDate()));
							for(int r=0 ; r < updetail.getList().size(); r++){
								GoodsPosModel goodspos = updetail.getList().get(r);
								goodspos.setCreateDate(JsonDateFormate.dataFormate(goodspos.getCreateDate()));
								goodspos.setInstockDate(JsonDateFormate.dataFormate(goodspos.getInstockDate()));
								goodspos.setProductionDate(JsonDateFormate.dataFormate(goodspos.getProductionDate()));
								goodspos.setUpdateDate(JsonDateFormate.dataFormate(goodspos.getUpdateDate()));
								goodspos.setParentCode(updetail.getCode());
							}
						}

						getCheckList();
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
	
	private void startDialog(final UpDetailModel updetail){
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_updetail, null);
		final EditText CustomerName_et = (EditText)view.findViewById(R.id.CustomerName_et);
		Button CustomerName_btn = (Button)view.findViewById(R.id.CustomerName_btn);
		final EditText Loc_Num_et = (EditText)view.findViewById(R.id.Loc_Num_et);
		final Button Loc_Num_btn = (Button)view.findViewById(R.id.Loc_Num_btn);
		
		final AlertDialog ad = new AlertDialog.Builder(UpDetailActivity.this).create();
		ad.setView(view);
		ad.show();
		
		CustomerName_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				goodsposPosition = -1;
				goodsPosName = CustomerName_et.getText().toString();
				
				for(int i=0;i<updetail.getList().size();i++){
					if(updetail.getList().get(i).getGoodsPosName().equals(goodsPosName)&& !updetail.getList().get(i).IsFinish){
						goodsposPosition = i;
						break;
					}
				}
				
				if(goodsposPosition != -1){
					Loc_Num_et.setText(updetail.getList().get(goodsposPosition).getLoc_Num()+1+"");
				//新增
				} else {
					Loc_Num_et.setText("0");
				}
			}
		});
		
		Loc_Num_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loc_num = Loc_Num_et.getText().toString();
				if(TextUtils.isEmpty(loc_num)){
					loc_num = "0";
				}
				
				if(goodsposPosition != -1){
					updetail.getList().get(goodsposPosition).setLoc_Num(Integer.parseInt(Loc_Num_et.getText().toString()));
					if (updetail.getList().get(goodsposPosition).getLoc_Num() == updetail
							.getList().get(goodsposPosition).getNum()) {

						if (!ExistLast(updetail.getList().get(goodsposPosition)
								.getCustomerCode(),
								updetail.getList().get(goodsposPosition)
										.getGoodsPosCode())) {

							updetail.getList().get(goodsposPosition)
									.setIsFinish(true);
						}
					}
					
					getCheckList();
				//新增
				} else {
					if(TextUtils.isEmpty(goodsPosName)){
						MyToast.showDialog(UpDetailActivity.this, "请先查询货位信息");
					} else {
						getPosMessage(goodsPosName, loc_num, updetail);
					}
				}
				ad.dismiss();
			}
		});
	}
	
	private boolean ExistLast(String CustomerCode, String PosCode) {
		boolean flag = true;
		for (int i = 0; i < updetails.size(); i++) {
			if (updetails.get(i).getCustomerCode().equals(CustomerCode)
					&& updetails.get(i).getGoodsPosCode().equals(PosCode)
					&& !updetails.get(i).IsFinish) {
				flag = false;

			}

		}
		return flag;
	}
	private void getPosMessage(String GoodsPosName, final String loc_num, final UpDetailModel updetail){
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object()
					.key("name").value(GoodsPosName)
					.key("stockCode").value(stockCode)
					.key("code").value("")
					.endObject();
		} catch (Exception e) {
			
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(UpDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetPosAndArea, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
							com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject.get("Data");
							List<GoodsAreaModel> goodpos = (JSONArray.parseArray(jsonArray.toString(),GoodsAreaModel.class));
							if(goodpos.size()!=0){
								GoodsPosModel goodspos = new GoodsPosModel();
								goodspos.setGoodsPosCode(goodpos.get(0).getCode());
								goodspos.setGoodsPosName(goodpos.get(0).getName());
								goodspos.setNum(0);
								goodspos.setLoc_Num(Integer.parseInt(loc_num));
								goodspos.setCode(UUID.randomUUID().toString());
								goodspos.setParentCode(updetail.getCode());
								goodspos.setCustomerCode(updetail.getCustomerCode());
								goodspos.setGoodsName(updetail.getGoodsName());
								goodspos.setProductionDate(updetail.getProductionDate());
								goodspos.setBoxId(updetail.getBoxId());
								goodspos.setProductId(updetail.getProductId());
								goodspos.setColumnNo(updetail.getColumnNo());
								goodspos.setCommon2(updetail.getCommon2());
								goodspos.setCommon3(updetail.getCommon3());
								goodspos.setCommon4(updetail.getCommon4());
								goodspos.setCommon5(updetail.getCommon5());
								goodspos.setContianNum(updetail.getContianNum());
								goodspos.setCreateBy(updetail.getCreateBy());
								goodspos.setCustGoodsCode(updetail.getCustGoodsCode());
								goodspos.setFloorNo(updetail.getFloorNo());
								goodspos.setGangwayNo(updetail.getGangwayNo());
								goodspos.setGoodsAreaName(goodpos.get(0).getGoodsAreaName());
								goodspos.setGoodsCode(updetail.getGoodsCode());
								goodspos.setGoodsUnitCode(updetail.getGoodsUnitCode());
								goodspos.setInStockCheckDetailCode(updetail.getInStockCheckDetailCode());
								goodspos.setInStockInformCode(updetail.getInStockInformCode());
								goodspos.setInStockNum(updetail.getInStockNum());
								goodspos.setInStockPutawayCode(updetail.getInStockPutawayCode());
								goodspos.setInstockDate(updetail.getInstockDate());
								goodspos.setPurchaseNo(updetail.getPurchaseNo());
								goodspos.setStockCode(updetail.getStockCode());
								goodspos.setTotalNum(updetail.getTotalNum());
								goodspos.setUnit(updetail.getUnit());
								goodspos.setUnitName(updetail.getUnitName());
								goodspos.setUpdateBy(updetail.getUpdateBy());
								goodspos.setUpdateDate(updetail.getUpdateDate());
								goodspos.setInStockPrice(updetail.getInStockPrice());
								goodspos.setShelflife(updetail.getShelflife());
								goodspos.setQualityCode(updetail.getQualityCode());
								goodspos.setQualityName(updetail.getQualityName());
								goodspos.setIsNoOut(updetail.getIsNoOut());
								goodspos.setInStockCheckCode(updetail.getInStockCheckCode());
								goodspos.setStandGrossWeight(updetail.getStandGrossWeight());
								goodspos.setStandNetWeight(updetail.getStandNetWeight());
								goodspos.setStandVolume(updetail.getStandVolume());
								goodspos.setCommon1(updetail.getCommon1());
								updetail.getList().add(goodspos);

								getCheckList();
							} else {
								MyToast.showDialog(UpDetailActivity.this, "该货位不存在");
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
	
	private void getCheckList(){
		
		if(TextUtils.isEmpty(CustomerCode_et.getText().toString())){
			
			if(!flag) {
				if (upDetailAdapter2 == null) {
					upDetailAdapter2 = new UpDetailAdapter(getApplicationContext(), true, up.getInStockPutawayStatusCode());
					up_list.setAdapter(upDetailAdapter2);
					upDetailAdapter2.appendList(updetails);
				}
				flag = true;
			}
			return;
		}
		
		for(int i=0;i<updetails.size();i++){
			if(CustomerCode_et.getText().toString().equals(updetails.get(i).getCustomerCode())){
				
				up_list.getChildAt(i).setBackgroundColor(Color.rgb(20, 20, 20));
			} else {
				up_list.getChildAt(i).setBackgroundColor(Color.rgb(223, 223, 221));
			}
		}
		
		CustomerCode_et.setText("");
	}
	
	private void save(List<GoodsPosModel> last_goodspos){
		startProgressDialog("Loading...");
		
		JSONStringer jsonStr = new JSONStringer();
		String json_Str = null;
		try {
			jsonStr = JSONHelper.serialize(jsonStr, last_goodspos);
			jsonStr = new JSONStringer().object()
					.key("list").value(jsonStr)
					.endObject();
			json_Str = JsonDateFormate.JsonFormate(jsonStr.toString());
		} catch (Exception e) {
			
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(UpDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_SaveInStockPutAway+"?code="+up.getCode(), json_Str,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							MyToast.showDialog(UpDetailActivity.this, "保存成功");
							getList(up.getCode());
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
	
	private void goUp(String PutawayDate){
		startProgressDialog("Loading...");
		JSONStringer jsonStr = new JSONStringer();
		try {
			jsonStr = new JSONStringer().object()
					.key("inStockPutawayCode").value(up.getCode())
					.key("userName").value(code)
					.key("putawaydate").value(PutawayDate)
					.endObject();
		} catch (Exception e) {
			
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(UpDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_InStockPutawayConfirm, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							MyToast.showDialog(UpDetailActivity.this, "提交成功");
							finish();
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
}
