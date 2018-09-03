package com.wms.newwmsapp.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;
import org.json.JSONStringer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.R.menu;
import com.wms.newwmsapp.adapter.WavePickUpDetailAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.PickDetailModel;
import com.wms.newwmsapp.model.WavePickupDetailModel;
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
import com.zltd.industry.ScannerManager.IScannerStatusListener;

import android.R.drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SeedingDetailActivity extends BaseActivity implements OnClickListener,SpeechSynthesizerListener{
	private ImageView back;
	private String Code;
	
	private WavePickupDetailModel model = new WavePickupDetailModel();
	private WavePickUpDetailAdapter pickUpadapter;
	private ListView pick_list;
	private List<PickDetailModel> showList = new ArrayList<PickDetailModel>();
	private TextView Sum;
	private TextView TypeSum;
	private TextView OrderSum;
	private TextView SortNo;
	private TextView GoodsposCode;
	private LinearLayout sure_lin;
	private Button btnSubmit;
	private Button btnResetSubmit;
	
	private MyChooseToastDialog progressDialog;
	private SoundUtils mSoundUtils;
	private ScannerManager mScannerManager;
	private TextToSpeech mSpeech;
	private SpeechSynthesizer speechSynthesizer;
	/* (non-Javadoc)
	 * @see com.wms.newwmsapp.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seeding_detail);
		
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
		Sum = (TextView) findViewById(R.id.Sum);
		TypeSum = (TextView) findViewById(R.id.TypeSum);
		OrderSum= (TextView) findViewById(R.id.OrderSum);
		pick_list = (ListView) findViewById(R.id.pick_list);
		SortNo=(TextView) findViewById(R.id.txtSortNo);
		GoodsposCode=(TextView) findViewById(R.id.GoodsposCode);
		sure_lin=(LinearLayout) findViewById(R.id.sure_lin);
		btnResetSubmit=(Button) findViewById(R.id.btnResetSubmit);
		btnSubmit=(Button) findViewById(R.id.btnSubmit);
		Intent intent = getIntent();
		Code=intent.getStringExtra("Code");
		GetDetail(Code);
		
//		// 初始化扫描
//		initSound();
		
		
		//扫码enter键
//		GoodsposCode.setOnKeyListener(new View.OnKeyListener() {   
//          @Override  
//          public boolean onKey(View v, int keyCode, KeyEvent event) {  
//              //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次  
//              if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_UP == event.getAction()) {  
//                  //处理事件  
//               Scanner(); 
//               
//               GoodsposCode.setFocusable(true);
//               GoodsposCode.setFocusableInTouchMode(true);
//               GoodsposCode.requestFocus();
//               GoodsposCode.setText("");
//               
//               return true;
//              }  
//             
//            return true;   
//          }
//      });
		
		
		
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
		
		
		
		  btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Submit(Code);
			}
		});
		  
		  btnResetSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GetDetail(Code);
			}
		});
	}
	
	private void Scanner()
	{
		if(GoodsposCode.getText()==null||GoodsposCode.getText().toString().isEmpty())
		{
			
			MyToast.showDialog(SeedingDetailActivity.this, "请输入条码！");
			return;
		}
		if (showList.size() == 0) {
			MyToast.showDialog(SeedingDetailActivity.this, "没有商品！");
			return;

		}
		
		if(IsComplete())
		{
			progressDialog = MyToast.showChooseDialog(
					SeedingDetailActivity.this, "播种完成,是否提交该单据！",
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							try {
								Submit(Code);
								progressDialog.dismiss();
							} catch (Exception e) {
								// TODO: handle exception
								progressDialog.dismiss();
							}
						}
					});
			
		}else
		{
			boolean flag=false;
			boolean tflag=false;
			for(int i=0;i<model.Details.size();i++)
			{
				
				if(model.Details.get(i).getGoodsBarCode()
						.equals(GoodsposCode.getText().toString())
						)
				{
					flag=true;
					
					if(!model.Details.get(i).isIsScan())
					{
						showList.set(0,model.Details.get(i));
						tflag=false;
						 speechSynthesizer.speak(String.valueOf(showList.get(0).getSortNo()));
							SortNo.setText(String.valueOf(showList.get(0).getSortNo()));
							model.Details.get(i).setScanNum(model.Details.get(i).getScanNum()+1);
							if(String.valueOf(model.Details.get(i).getScanNum()).equals(model.Details.get(i).getNum()))
							{
								UpdateIsScan(model.Details.get(i).getGoodsCustomerCode(),model.Details.get(i).getOutStockDeliveryCode());
								break;
							}
							break;
					}
					
				} if(model.Details.get(i).getGoodsBarCode()
						.equals(GoodsposCode.getText().toString())
						&&model.Details.get(i).isIsScan())
				{
					
					tflag=true;
				}
				
			}

			if(!flag)
			{
				MyToast.showDialog(SeedingDetailActivity.this, "该货品不存在！");
				return;
			}
			if(tflag)
			{
				MyToast.showDialog(SeedingDetailActivity.this, "该货品已完成！");
				return;
				
			}
			Sum.setText(String.valueOf((Integer.parseInt(Sum.getText().toString())-1)));
			
			pickUpadapter.clear();
			pickUpadapter.appendList(showList);
			
			
		}
		
	}
	
	private boolean IsComplete()
	{
		boolean flag=true;
		for(int i=0;i<model.Details.size();i++)
		{
			if(!model.Details.get(i).isIsScan())
			{
				flag=false;
				break;
				
			}
		}
		
		return flag;
	}
	private void UpdateIsScan(String GoodsCode,String DeliveryCode) {
		boolean flag = true;
		List<String> liType=new ArrayList<String>();
		List<String> liOrder=new ArrayList<String>();
		for (int i = 0; i < model.Details.size(); i++) {
			if (model.Details.get(i).getGoodsCustomerCode().equals(GoodsCode)
					&&model.Details.get(i).getOutStockDeliveryCode().equals(DeliveryCode)) {
				
				model.Details.get(i).setIsScan(true);
			}
			if (!model.Details.get(i).isIsScan()) {
				flag = false;
			}
			if(!model.Details.get(i).isIsScan())
			{
				showList.set(0, model.Details.get(i));
				
			}
			if(!model.Details.get(i).isIsScan())
			{
				if(!liType.contains(model.Details.get(i).getGoodsCustomerCode()))
				{
					liType.add(model.Details.get(i).getGoodsCustomerCode());
				}
				if(!liOrder.contains(model.Details.get(i).getOutStockDeliveryCode()))
				{
					liOrder.add(model.Details.get(i).getOutStockDeliveryCode());
				}
				
			}

		}

		TypeSum.setText(String.valueOf(liType.size()));
		OrderSum.setText(String.valueOf(liOrder.size()));
		
		if (flag) {
			progressDialog = MyToast.showChooseDialog(
					SeedingDetailActivity.this, "播种完成,是否提交该单据！",
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							try {
								Submit(Code);
								progressDialog.dismiss();
							} catch (Exception e) {
								// TODO: handle exception
								progressDialog.dismiss();
							}
						}
					});
		}
		
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
			MyToast.showDialog(SeedingDetailActivity.this,
					"还有" + Sum.getText() + "货品\n" + TypeSum.getText()
							+ "种类\n作业没有完成！");
			return;
		}
		startProgressDialog("Loading...");
		String url = Constants.url_WavePickConfirmSubmit
				+ "?wavepickconfirmCode=" + pickCode + "&userCode="
				+ preferences.getString("code", "");

		JSONStringer jsonStr = new JSONStringer();

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(SeedingDetailActivity.this);
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
										SeedingDetailActivity.this, "提交成功!");
								Intent intent=new Intent(SeedingDetailActivity.this,SeedingActivity.class);
								startActivity(intent);
								finish();
							
							} else {
								String Message = jsonObject
										.getString("ErrorMessage");

								MyToast.showDialog(
										SeedingDetailActivity.this, "提交失败!"
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seeding_detail, menu);
		return true;
	}

	private void GetDetail(String pickCode) {
		startProgressDialog("Loading...");
		String url = Constants.url_GetWavePickConfirm
				+ "?StockCode="+preferences.getString("StockCode", "")+"&wavepickconfirmCode=" + pickCode + "&userCode="
				+ preferences.getString("code", "");

		JSONStringer jsonStr = new JSONStringer();

		RequestQueue mRequestQueue = Volley
				.newRequestQueue(SeedingDetailActivity.this);
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
							OrderSum.setText(model.getTotalOrderCount());

							com.alibaba.fastjson.JSONArray json_array = (com.alibaba.fastjson.JSONArray) jsonObject
									.get("Details");
							model.Details = JSONArray.parseArray(
									json_array.toString(),
									PickDetailModel.class);
							
							if (model.Details.size() > 0) {
								showList.add(model.Details.get(0));
								SortNo.setText(String.valueOf(model.Details.get(0).getSortNo()));
							}
							if (pickUpadapter == null) {
								pickUpadapter = new WavePickUpDetailAdapter(
										SeedingDetailActivity.this, true);
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
//
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
//			mScannerManager.addScannerStatusListener(mIScannerStatusListener);
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
//						GoodsposCode.setText(s);
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
//		finish();
//	}


	
	 private void setParams() {
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_AMR);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_LANGUAGE, SpeechSynthesizer.LANGUAGE_ZH);
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_NUM_PRON, "0");
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_ENG_PRON, "0");
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PUNC, "0");
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_BACKGROUND, "0");
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_STYLE, "0");
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TERRITORY, "0");
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
	public void onNewDataArrive(SpeechSynthesizer arg0, byte[] arg1,
			boolean arg2) {
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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	

  
}
