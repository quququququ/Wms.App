package com.wms.newwmsapp.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONStringer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.WavePickUpDetailAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.PickDetailModel;
import com.wms.newwmsapp.model.WavePickupDetailModel;
import com.wms.newwmsapp.model.WavePickupModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyChooseToastDialog;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.tool.SoundUtils;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;
import com.zltd.industry.ScannerManager;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SeedingPickupDetailActivity extends BaseActivity implements OnClickListener,SpeechSynthesizerListener{

	private ImageView back;
	private WavePickupModel pick;
	private WavePickupDetailModel model = new WavePickupDetailModel();
	private WavePickUpDetailAdapter pickUpadapter;
	private ListView pick_list;
	private List<PickDetailModel> showList = new ArrayList<PickDetailModel>();
	private TextView txtGoodsPosName;
	private TextView Sum;
	private TextView TypeSum;
	private TextView pickUpNum;
	private TextView HavePickUpNum;
	private TextView SortNo;
	private EditText GoodsposCode;
	private LinearLayout sure_lin;
	private MyChooseToastDialog progressDialog;
	private Button btnResetSubmit;
	private Button btnSubmitAgain;
	private String num = "0";

	private SoundUtils mSoundUtils;
	private ScannerManager mScannerManager;
	private SpeechSynthesizer speechSynthesizer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seeding_pickup_detail);
		
		speechSynthesizer = new SpeechSynthesizer(getApplicationContext(),
                "holder", this);
	        // 此处需要将setApiKey方法的两个参数替换为你在百度开发者中心注册应用所得到的apiKey和secretKey
	        speechSynthesizer.setApiKey("hjBNHz0LysuLfMK5AgfmRVFGnjOakyfK", "HW0YfbUrvcDdmL1hAjFUszZFkn3oIZSI");
	        speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	        setVolumeControlStream(AudioManager.STREAM_MUSIC);
	        setParams();

		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Intent intent = getIntent();

		pick = (WavePickupModel) intent.getSerializableExtra("Pick");

		pick_list = (ListView) findViewById(R.id.pick_list);

		GetDetail(pick.getCode());
		GoodsposCode = (EditText) findViewById(R.id.GoodsposCode);
		txtGoodsPosName = (TextView) findViewById(R.id.GoodsposName);

		Sum = (TextView) findViewById(R.id.Sum);
		TypeSum = (TextView) findViewById(R.id.TypeSum);
		pickUpNum = (TextView) findViewById(R.id.pickUpNum);
		HavePickUpNum = (TextView) findViewById(R.id.HavePickUpNum);
		sure_lin = (LinearLayout) findViewById(R.id.sure_lin);
		SortNo=(TextView) findViewById(R.id.txtSortNo);

		
		GoodsposCode.setOnEditorActionListener(new TextView.OnEditorActionListener() { 

			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)  {                          

				if (actionId==EditorInfo.IME_ACTION_SEARCH ||(event != null&& event.getAction()==KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					Scanner();
					
					 GoodsposCode.setFocusable(true);
						GoodsposCode.setFocusableInTouchMode(true);
						GoodsposCode.requestFocus();
						GoodsposCode.setText("");
					
					return true;             
				}               
				return true;           
			}       
		});
				

		sure_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Scanner();
			}
		});

		btnResetSubmit = (Button) findViewById(R.id.btnResetSubmit);
		btnResetSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progressDialog = MyToast.showChooseDialog(
						SeedingPickupDetailActivity.this, "是否重拣？",
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								try {
									GetDetail(pick.getCode());
									progressDialog.dismiss();
								} catch (Exception e) {
									// TODO: handle exception
									progressDialog.dismiss();
								}
							}
						});
			}
		});

		btnSubmitAgain = (Button) findViewById(R.id.btnSubmitAgain);
		btnSubmitAgain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progressDialog = MyToast.showChooseDialog(
						SeedingPickupDetailActivity.this, "是否重新提交？",
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								try {
									Submit(pick.getCode());
									progressDialog.dismiss();
								} catch (Exception e) {
									// TODO: handle exception
									progressDialog.dismiss();
								}
							}
						});
			}
		});
	}
	
	private boolean IsComplete() {
		boolean flag = true;
		for (int i = 0; i < model.Details.size(); i++) {
			if (!model.Details.get(i).isIsScan()) {

				flag = false;
			}
		}

		return flag;
	}

	private void Scanner() {

		if ((showList.get(0).getGoodsBarCode() == null || showList.get(0)
				.getGoodsBarCode().isEmpty())
				|| (GoodsposCode.getText() == null || GoodsposCode.getText()
						.toString().isEmpty())) {
			MyToast.showDialog(SeedingPickupDetailActivity.this, "条码为空！");
			return;

		}
		if (showList.size() == 0) {
			MyToast.showDialog(SeedingPickupDetailActivity.this, "没有商品！");
			return;
		}
		if (IsComplete()) {
			progressDialog = MyToast.showChooseDialog(
					SeedingPickupDetailActivity.this, "波次分拣完成,是否提交该单据！",
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							try {
								Submit(pick.getCode());
								progressDialog.dismiss();
							} catch (Exception e) {
								// TODO: handle exception
								progressDialog.dismiss();
							}
						}
					});
		}
		if (showList.get(0).getGoodsBarCode()
				.equals(GoodsposCode.getText().toString())) {

			speechSynthesizer.speak(String.valueOf(showList.get(0).getSortNo()));
			SortNo.setText(String.valueOf(showList.get(0).getSortNo()));
			HavePickUpNum.setText(String.valueOf((Integer
					.parseInt(HavePickUpNum.getText().toString()) + 1)));
			num = String.valueOf((Integer.parseInt(num) + 1));
			Sum.setText(String.valueOf(Integer.parseInt(Sum.getText()
					.toString()) - 1));

			if (HavePickUpNum.getText().toString()
					.equals(showList.get(0).getNum())) {
				UpdateIsScan(showList.get(0).getGoodsCode());

			}

		} else {
			MyToast.showDialog(SeedingPickupDetailActivity.this, "该商品已完成或不存在！");
		}

	}

	private void UpdateIsScan(String GoodsCode) {
		
		boolean flag = true;
		boolean showListIsAdd = false;
		boolean IsScanIsUpdate  = false;
		
		List<String> liType = new ArrayList<String>();
		
		for (int i = 0; i < model.Details.size(); i++) {
			
			if (model.Details.get(i).GoodsCode.equals(GoodsCode) && !model.Details.get(i).isIsScan() && !IsScanIsUpdate) {

				model.Details.get(i).setIsScan(true);
				
				IsScanIsUpdate = true;
			}
			
			if(!model.Details.get(i).isIsScan())
			{
				flag = false;
			}
					 
			if (!model.Details.get(i).isIsScan() && !showListIsAdd) {
				
				showList.set(0, model.Details.get(i));
				showListIsAdd = true;
			}
			
			if (!model.Details.get(i).isIsScan()) {
				if (!liType.contains(model.Details.get(i).getGoodsCustomerCode())) 
				{
					liType.add(model.Details.get(i).getGoodsCustomerCode());
				}
			}
		}
		
		pickUpNum.setText(showList.get(0).getNum());
		InitGoodsPos();
		TypeSum.setText(String.valueOf(liType.size()));

		if (flag) {
			progressDialog = MyToast.showChooseDialog(
					SeedingPickupDetailActivity.this, "波次分拣完成,是否提交该单据！",
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							try {
								Submit(pick.getCode());
								progressDialog.dismiss();
							} catch (Exception e) {
								// TODO: handle exception
								progressDialog.dismiss();
							}
						}
					});
		}
		HavePickUpNum.setText("0");
		num = "0";
		GoodsposCode.setText("");
		pickUpadapter.clear();
		pickUpadapter.appendList(showList);
	}

	private void Submit(String pickCode) {
		boolean isComplete = true;
		for (int i = 0; i < model.Details.size(); i++) {
			if (!model.Details.get(i).isIsScan()) {
				isComplete = false;
			}

		}
		if (!isComplete) {
			MyToast.showDialog(SeedingPickupDetailActivity.this,
					"还有" + Sum.getText() + "货品\n" + TypeSum.getText()
							+ "种类\n作业没有完成！");
			return;
		}
		startProgressDialog("Loading...");
		String url = Constants.url_SubmitWavePickupConfirm
				+ "?wavepickconfirmCode=" + pickCode +"&userName=" + preferences.getString("name", "");

		JSONStringer jsonStr = new JSONStringer();

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(SeedingPickupDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				jsonStr.toString(), new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {

							com.alibaba.fastjson.JSONObject jsonObject = JSON
									.parseObject(response.toString());
							boolean flag = jsonObject.getBoolean("IsSuccess");
							if (flag) {
								MyToast.showDialog(
										SeedingPickupDetailActivity.this, "提交成功!");
								Intent intent = new Intent(
										SeedingPickupDetailActivity.this,
										BatchPickupConfirmActivity.class);
								startActivity(intent);
								finish();

							} else {
								String Message = jsonObject
										.getString("ErrorMessage");

								MyToast.showDialog(
										SeedingPickupDetailActivity.this, "提交失败!"
												+ Message);
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
				}, handler);
		mRequestQueue.add(request);
	}

	private void InitGoodsPos() {
		txtGoodsPosName.setText(showList.get(0).getGoodsPosName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.batch_pickup_detail, menu);
		return true;
	}

	private void GetDetail(String pickCode) {
		startProgressDialog("Loading...");
		String url = Constants.url_GetWavePickDetailForSortNo
				+ "?wavepickconfirmCode=" + pickCode + "&userCode="
				+ preferences.getString("code", "");

		JSONStringer jsonStr = new JSONStringer();

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(SeedingPickupDetailActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url,
				jsonStr.toString(), new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {

							com.alibaba.fastjson.JSONObject jsonObject = JSON
									.parseObject(response.toString());
							model = JSON.parseObject(response.toString(),
									WavePickupDetailModel.class);
							Sum.setText(model.getTotalNum());
							TypeSum.setText(model.getTotalGoodsCount());

							com.alibaba.fastjson.JSONArray json_array = (com.alibaba.fastjson.JSONArray) jsonObject
									.get("Details");
							model.Details = JSONArray.parseArray(
									json_array.toString(),
									PickDetailModel.class);
							if (model.Details.size() > 0) {
								showList.add(model.Details.get(0));
								SortNo.setText(String.valueOf(model.Details.get(0).getSortNo()));
								InitGoodsPos();
								pickUpNum
										.setText(model.Details.get(0).getNum());
							}
							if (pickUpadapter == null) {
								pickUpadapter = new WavePickUpDetailAdapter(
										SeedingPickupDetailActivity.this, true);
								pick_list.setAdapter(pickUpadapter);
								pickUpadapter.appendList(showList);
							} else {
								pickUpadapter.clear();
								pickUpadapter.appendList(showList);
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
				}, handler);
		mRequestQueue.add(request);

	}

	 private void setParams() {
	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5");
	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_AMR);
	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);
//	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_LANGUAGE, SpeechSynthesizer.LANGUAGE_ZH);
//	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_NUM_PRON, "0");
//	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_ENG_PRON, "0");
//	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PUNC, "0");
//	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_BACKGROUND, "0");
//	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_STYLE, "0");
//	        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TERRITORY, "0");
	    }
	
	@Override
	public void onBufferProgressChanged(SpeechSynthesizer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancel(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(SpeechSynthesizer arg0, SpeechError arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewDataArrive(SpeechSynthesizer arg0, byte[] arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechFinish(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechPause(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechProgressChanged(SpeechSynthesizer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechResume(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechStart(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartWorking(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSynthesizeFinish(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
