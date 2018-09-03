package com.wms.newwmsapp.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.id;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.R.menu;
import com.wms.newwmsapp.adapter.PickUpConfirmDetailAdapter;
import com.wms.newwmsapp.adapter.UpDetailAdapter;
import com.wms.newwmsapp.base.BaseScanActivity;
import com.wms.newwmsapp.model.GoodsAreaModel;
import com.wms.newwmsapp.model.GoodsPosModel;
import com.wms.newwmsapp.model.PickUpConfirmDetailModel;
import com.wms.newwmsapp.model.PickUpConfirmModel;
import com.wms.newwmsapp.model.UpDetailModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.JSONHelper;
import com.wms.newwmsapp.tool.JsonDateFormate;
import com.wms.newwmsapp.tool.ListViewScrollView;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.tool.SoundUtils;
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
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "HandlerLeak", "InflateParams" })
public class PickUpConfirmDetailActivity extends BaseScanActivity {
	public static Handler myHandler;
	private ImageView back;
	private ListViewScrollView detail_list;
	private EditText CustomerCode_et;
	private PickUpConfirmDetailAdapter adapter;
	private LinearLayout operator_lin;
	private LinearLayout save_lin, up_lin;

	private PickUpConfirmModel pick;
	private List<PickUpConfirmDetailModel> detail;
	private int goodsposPosition = -1;
	private String goodsPosName = "";
	private String loc_num = "";
	private SoundUtils mSoundUtils;
	private ScannerManager mScannerManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_up_confirm_detail);
		// 初始化扫描
		//initSound();
		back = (ImageView) findViewById(R.id.back);
		detail_list = (ListViewScrollView) findViewById(R.id.pickDetail_list);
		CustomerCode_et = (EditText) findViewById(R.id.CustomerCode_et);
		operator_lin = (LinearLayout) findViewById(R.id.operator_lin);
		save_lin = (LinearLayout) findViewById(R.id.save_lin);
		up_lin = (LinearLayout) findViewById(R.id.up_lin);

		operator_lin.setVisibility(View.VISIBLE);
		Intent intent = getIntent();
		pick = (PickUpConfirmModel) intent.getSerializableExtra("Pick");

		GetList(pick.Code);

		myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String loc_Code = (String) msg.obj;
					for (int i = 0; i < detail.size(); i++) {
						if (loc_Code.equals(detail.get(i).getCode())) {
							PickUpConfirmDetailModel model = detail.get(i);
							startDialog(model);
							break;
						}
					}
					break;
				case 2:
					@SuppressWarnings("unchecked")
					Map<String, String> map = (Map<String, String>) msg.obj;
					int chilren_position = Integer.parseInt(map
							.get("chilren_position"));
					String Code = map.get("Code");
					for (int i = 0; i < detail.size(); i++) {
						if (Code.equals(detail.get(i).getCode())) {
							detail.get(i).getList().remove(chilren_position);
							break;
						}
					}

					GetCheckList();
					break;
				}
			}
		};

		CustomerCode_et
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {

						if (actionId == EditorInfo.IME_ACTION_SEARCH
								|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
							GetCheckList();
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
				final List<PickUpConfirmDetailModel> savelist = new ArrayList<PickUpConfirmDetailModel>();
				int size = 0;
				for (int i = 0; i < detail.size(); i++) {
					List<PickUpConfirmDetailModel> model = detail.get(i)
							.getList();
					size += detail.get(i).getList().size();
					for (int r = 0; r < model.size(); r++) {
						PickUpConfirmDetailModel goodpos = model.get(r);
						if (goodpos.getLoc_Num() != 0) {
							savelist.add(goodpos);
						}
					}
				}

				if (size != savelist.size()) {
					LayoutInflater inflater = getLayoutInflater();
					View view = inflater.inflate(
							R.layout.dialog_showchoosedialog, null);
					final AlertDialog ad = new AlertDialog.Builder(
							PickUpConfirmDetailActivity.this).create();
					ad.setView(view);
					ad.show();

					TextView txtMessage = (TextView) view
							.findViewById(R.id.message);
					txtMessage.setText("有数量为0的货品，是否删除？");
					TextView btnOk = (TextView) view.findViewById(R.id.btn_ok);
					btnOk.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							for (int r = 0; r < savelist.size(); r++) {
								PickUpConfirmDetailModel goodpos = savelist
										.get(r);
								goodpos.setNum(goodpos.getLoc_Num());
							}

							Save(savelist);
							ad.dismiss();
						}
					});

					TextView btnCancle = (TextView) view
							.findViewById(R.id.btn_cancel);
					btnCancle.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							ad.dismiss();
						}
					});
				} else {
					Save(savelist);

				}

			}
		});

		up_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				goUp();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void goUp() {
		startProgressDialog("Loading...");
		JSONStringer jsonStr = new JSONStringer();
		try {
			jsonStr = new JSONStringer().object().key("code")
					.value(pick.getCode()).key("userName").value(name)
					.endObject();
		} catch (Exception e) {

		}

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(PickUpConfirmDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_SubmitOutStockPickConfirm, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							MyToast.showDialog(
									PickUpConfirmDetailActivity.this, "提交成功");
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

	private void Save(List<PickUpConfirmDetailModel> last_goodspos) {
		startProgressDialog("Loading...");

		JSONStringer jsonStr = new JSONStringer();
		String json_Str = null;
		try {
			jsonStr = JSONHelper.serialize(jsonStr, last_goodspos);
			jsonStr = new JSONStringer().object().key("list").value(jsonStr)
					.endObject();
			json_Str = JsonDateFormate.JsonFormate(jsonStr.toString());
		} catch (Exception e) {

		}

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(PickUpConfirmDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_OutStockPickConfirmUpdate + "?code="
						+ pick.getCode(), json_Str,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							MyToast.showDialog(
									PickUpConfirmDetailActivity.this, "保存成功");
							GetList(pick.getCode());
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

	private void GetList(String Code) {

		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object()
					.key("outStockPickConfirmCode").value(Code).endObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(PickUpConfirmDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetOutStockPickConfirmDetailByCode,
				jsonStr.toString(), new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						com.alibaba.fastjson.JSONObject jsonObject = JSON
								.parseObject(response.toString());
						com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject
								.get("Data");
						detail = JSONArray.parseArray(jsonArray.toString(),
								PickUpConfirmDetailModel.class);
						// 将时间格式全部进行修改
						for (int i = 0; i < detail.size(); i++) {
							PickUpConfirmDetailModel model = detail.get(i);
							model.setProductionDate(JsonDateFormate
									.dataFormate(model.getProductionDate()));
							for (int r = 0; r < model.getList().size(); r++) {
								PickUpConfirmDetailModel goodspos = model
										.getList().get(r);
								goodspos.setCreateDate(JsonDateFormate
										.dataFormate(goodspos.getCreateDate()));
								goodspos.setProductionDate(JsonDateFormate
										.dataFormate(goodspos
												.getProductionDate()));
								goodspos.setUpdateDate(JsonDateFormate
										.dataFormate(goodspos.getUpdateDate()));

							}
						}

						GetCheckList();
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

	private void GetCheckList() {
		List<PickUpConfirmDetailModel> new_details = new ArrayList<PickUpConfirmDetailModel>();
		if (TextUtils.isEmpty(CustomerCode_et.getText().toString())) {
			new_details.addAll(detail);
		} else {
			for (int i = 0; i < detail.size(); i++) {
				if (CustomerCode_et.getText().toString()
						.equals(detail.get(i).getCustomerCode())) {
					new_details.add(detail.get(i));
				}
			}
		}

		if (adapter == null) {
			adapter = new PickUpConfirmDetailAdapter(getApplicationContext(),
					true);
			detail_list.setAdapter(adapter);
			adapter.appendList(new_details);
		} else {
			adapter.clear();
			adapter.appendList(new_details);
		}

	}

	private void startDialog(final PickUpConfirmDetailModel model) {
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_updetail, null);
		final EditText CustomerName_et = (EditText) view
				.findViewById(R.id.CustomerName_et);
		Button CustomerName_btn = (Button) view
				.findViewById(R.id.CustomerName_btn);
		final EditText Loc_Num_et = (EditText) view
				.findViewById(R.id.Loc_Num_et);
		final Button Loc_Num_btn = (Button) view.findViewById(R.id.Loc_Num_btn);

		final AlertDialog ad = new AlertDialog.Builder(
				PickUpConfirmDetailActivity.this).create();
		ad.setView(view);
		ad.show();

		CustomerName_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				goodsposPosition = -1;
				goodsPosName = CustomerName_et.getText().toString();

				for (int i = 0; i < model.getList().size(); i++) {
					if (model.getList().get(i).getGoodsPosName()
							.equals(goodsPosName)
							&& !model.getList().get(i).IsFinish) {
						goodsposPosition = i;
						break;
					}
				}

				if (goodsposPosition != -1) {
					Loc_Num_et.setText(model.getList().get(goodsposPosition)
							.getLoc_Num()
							+ 1 + "");
					// 新增
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
				if (TextUtils.isEmpty(loc_num)) {
					loc_num = "0";
				}

				if (goodsposPosition != -1) {
					model.getList()
							.get(goodsposPosition)
							.setLoc_Num(
									Integer.parseInt(Loc_Num_et.getText()
											.toString()));
					if (model.getList().get(goodsposPosition).getLoc_Num() == model
							.getList().get(goodsposPosition).getNum()) {

						if (!ExistLast(model.getList().get(goodsposPosition)
								.getCustomerCode(),
								model.getList().get(goodsposPosition)
										.getGoodsPosCode())) {

							model.getList().get(goodsposPosition)
									.setIsFinish(true);
						}
					}
					GetCheckList();
					// 新增
				} else {
					if (TextUtils.isEmpty(goodsPosName)) {
						MyToast.showDialog(PickUpConfirmDetailActivity.this,
								"请先查询货位信息");
					} else {
						getPosMessage(goodsPosName, loc_num, model);
					}
				}
				ad.dismiss();
			}
		});

	}

	private boolean ExistLast(String CustomerCode, String PosCode) {
		boolean flag = true;
		for (int i = 0; i < detail.size(); i++) {
			if (detail.get(i).getCustomerCode().equals(CustomerCode)
					&& detail.get(i).getGoodsPosCode().equals(PosCode)
					&& !detail.get(i).IsFinish) {
				flag = false;

			}

		}
		return flag;
	}

	private void getPosMessage(String GoodsPosName, final String loc_num,
			final PickUpConfirmDetailModel model) {
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object().key("name")
					.value(GoodsPosName).key("stockCode").value(stockCode)
					.key("code").value("").endObject();
		} catch (Exception e) {

		}

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(PickUpConfirmDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_GetPosAndArea, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							com.alibaba.fastjson.JSONObject jsonObject = JSON
									.parseObject(response.toString());
							com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) jsonObject
									.get("Data");
							List<GoodsAreaModel> goodpos = (JSONArray
									.parseArray(jsonArray.toString(),
											GoodsAreaModel.class));
							if (goodpos.size() != 0) {
								PickUpConfirmDetailModel detailModel = new PickUpConfirmDetailModel();
								detailModel.setGoodsPosCode(goodpos.get(0)
										.getCode());
								detailModel.setGoodsPosName(goodpos.get(0)
										.getName());
								detailModel.setNum(0);
								detailModel.setLoc_Num(Integer
										.parseInt(loc_num));
								detailModel.setCode(UUID.randomUUID()
										.toString());
								detailModel.setOutStockInformCode(model
										.getOutStockInformCode());
								detailModel.setOutStockPickConfirmCode(model
										.getOutStockPickConfirmCode());
								detailModel.setGoodsCode(model.getGoodsCode());
								detailModel.setGoodsUnitCode(model
										.getGoodsUnitCode());
								detailModel.setOutStockPrice(model
										.getOutStockPrice());
								detailModel.setQualityCode(model
										.getQualityCode());
								detailModel.setProductId(model.getProductId());
								detailModel.setCreateBy(model.getCreateBy());
								detailModel.setCreateDate(model.getCreateDate());
								detailModel.setUpdateBy(model.getUpdateBy());
								detailModel.setUpdateDate(model.getUpdateDate());
								detailModel.setInStockGoodsCode(model
										.getInStockGoodsCode());
								detailModel.setCommon1(model.getCommon1());
								detailModel.setCommon2(model.getCommon2());
								detailModel.setCommon3(model.getCommon3());
								detailModel.setCommon4(model.getCommon4());
								detailModel.setCommon5(model.getCommon5());
								detailModel.setIsGift(model.getIsGift());
								detailModel.setCustomerCode(model
										.getCustomerCode());
								detailModel.setGoodsName(model.getGoodsName());
								detailModel.setStandUnitName(model
										.getStandUnitName());
								detailModel.setStandGrossWeight(model
										.getStandGrossWeight());
								detailModel.setStandNetWeight(model
										.getStandNetWeight());
								detailModel.setStandVolume(model
										.getStandVolume());
								detailModel.setShelfLife(model.getShelfLife());
								detailModel.setCustGoodsName(model
										.getCustGoodsName());
								detailModel.setUnit(model.getUnit());
								detailModel.setBarCode(model.getBarCode());
								detailModel.setUnitName(model.getUnitName());
								detailModel.setProductionDate(model
										.getProductionDate());
								detailModel.setBoxId(model.getBoxId());
								detailModel.setGoodsCode(model.getGoodsCode());
								detailModel.setGoodsUnitCode(model
										.getGoodsUnitCode());

								detailModel.setCommon1(model.getCommon1());
								model.getList().add(detailModel);

								GetCheckList();
							} else {
								MyToast.showDialog(
										PickUpConfirmDetailActivity.this,
										"该货位不存在");
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_up_confirm_detail, menu);
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
