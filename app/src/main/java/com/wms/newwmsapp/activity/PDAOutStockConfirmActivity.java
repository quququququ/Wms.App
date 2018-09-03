package com.wms.newwmsapp.activity;

import android.app.AlertDialog;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.view.PullToRefreshListView;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.PickUpCustGoodsAdapter;
import com.wms.newwmsapp.adapter.StockCheckAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.CustGoodModel;
import com.wms.newwmsapp.model.StockCheckDetailModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.JSONHelper;
import com.wms.newwmsapp.tool.MyChooseToastDialog;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class PDAOutStockConfirmActivity extends BaseActivity implements OnClickListener, SpeechSynthesizerListener {
    private Spinner OutStockConfirmCustGoods_sp;
    private int CurrentIndex = 1;
    private int PageSize = 1000000;
    private int CurrentIndex_Total;
    private PullToRefreshListView outstock_list;
    private PullToRefreshListView _outstock_list;
    private EditText txtCode;
    private StockCheckAdapter outStockConfirmadapter;
    private Button btnSearch;
    private EditText txtBarCode;
    private Button button_haveBarCode;
    private View view;
    private Button button_Commit;
    private boolean isOutAndCancel = false;
    private ImageView back;
    private MyChooseToastDialog progressDialog;
    private SpeechSynthesizer speechSynthesizer;

    private List<StockCheckDetailModel> outList = new ArrayList<StockCheckDetailModel>();
    private List<StockCheckDetailModel> outBarCodeList = new ArrayList<StockCheckDetailModel>();

    private String selectType;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdaout_stock_confirm);

        speechSynthesizer = new SpeechSynthesizer(getApplicationContext(), "holder", this);
        // 此处需要将setApiKey方法的两个参数替换为你在百度开发者中心注册应用所得到的apiKey和secretKey
        speechSynthesizer.setApiKey("hjBNHz0LysuLfMK5AgfmRVFGnjOakyfK", "HW0YfbUrvcDdmL1hAjFUszZFkn3oIZSI");
        speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setParams();

        txtCode = (EditText) findViewById(R.id.Code);
        btnSearch = (Button) findViewById(R.id.button_check);
        txtBarCode = (EditText) findViewById(R.id.BarCode);

        button_haveBarCode = (Button) findViewById(R.id.button_haveBarCode);
        button_Commit = (Button) findViewById(R.id.button_Commit);
        back = (ImageView) findViewById(R.id.back);

        final List<CustGoodModel> custList = new ArrayList<CustGoodModel>();

        CustGoodModel model = new CustGoodModel();
        model.setCustGoodsName("物流单号");
        custList.add(model);
        CustGoodModel model2 = new CustGoodModel();
        model2.setCustGoodsName("出库单号");
        custList.add(model2);

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        OutStockConfirmCustGoods_sp = (Spinner) findViewById(R.id.OutStockConfirmCustGoods_sp);
        PickUpCustGoodsAdapter custGoodAdapter = new PickUpCustGoodsAdapter(PDAOutStockConfirmActivity.this, true);

        OutStockConfirmCustGoods_sp.setAdapter(custGoodAdapter);
        custGoodAdapter.appendList(custList);
        OutStockConfirmCustGoods_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

                selectType = custList.get(arg2).getCustGoodsName();

                if (selectType == "物流单号") {
                    url = Constants.url_GetOutStockCheckByWaybillOrCode + "?stockCode=" + stockCode + "&waybillcode=";
                } else {
                    url = Constants.url_GetOutStockCheckByWaybillOrCode + "?stockCode=" + stockCode + "&code=";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        outstock_list = (PullToRefreshListView) findViewById(R.id.outstockconfim_pulllist);
        outstock_list.setMode(Mode.DISABLED);

        outstock_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉的时候数据重置
                CurrentIndex = 1;

                getList(txtCode.getText().toString());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉的时候添加选项
                if (CurrentIndex <= CurrentIndex_Total) {

                    getList(txtCode.getText().toString());
                } else {
                    MyToast.showDialog(PDAOutStockConfirmActivity.this, "已加载全部数据");
                    new FinishRefresh().execute();
                }
            }
        });

        txtCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (txtCode.getText() == null || txtCode.getText().toString().isEmpty()) {

                        MyToast.showDialog(PDAOutStockConfirmActivity.this, "单号为空！");
                        return false;
                    }
                    getList(txtCode.getText().toString());

                    txtBarCode.setFocusable(true);
                    txtBarCode.setFocusableInTouchMode(true);
                    txtBarCode.requestFocus();
                    txtBarCode.setText("");
                    return true;
                }

                return true;
            }
        });

        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                String _Code = txtCode.getText().toString();
                if (_Code.isEmpty()) {
                    MyToast.showDialog(PDAOutStockConfirmActivity.this, "请输入要查询的单号！");
                    return;

                }

                getList(_Code);
                txtBarCode.setFocusable(true);
                txtBarCode.setFocusableInTouchMode(true);
                txtBarCode.requestFocus();
                txtBarCode.setText("");
            }
        });

/*		btnSure.setOnClickListener(new OnClickListener() {

			// @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!BarCode()) {
					MyToast.showDialog(PDAOutStockConfirmActivity.this, "货品不存在！");
					txtBarCode.setText("");

				} else {
					outStockConfirmadapter.clear();
					outstock_list.setAdapter(outStockConfirmadapter);
					outStockConfirmadapter.appendList(outList);
					new FinishRefresh().execute();
					txtBarCode.setText("");

				}
				if (outList.size() == 0 && outBarCodeList.size() != 0) {

					progressDialog = MyToast.showChooseDialog(PDAOutStockConfirmActivity.this, "是否确认出库完成？",
							new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									try {
										Commit();
										progressDialog.dismiss();
									} catch (Exception e) {
										progressDialog.dismiss();

									}

								}
							});
				}
			}
		});*/

        button_haveBarCode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startDialog();
            }
        });

        button_Commit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (outBarCodeList.size() == 0) {
                    MyToast.showDialog(PDAOutStockConfirmActivity.this, "请先扫码！");
                } else if (outList.size() != 0) {
                    MyToast.showDialog(PDAOutStockConfirmActivity.this, "请先扫码完成！");
                } else {
                    try {
                        Commit();

                        txtCode.setFocusable(true);
                        txtCode.setFocusableInTouchMode(true);
                        txtCode.requestFocus();
                        txtCode.setText("");

                    } catch (Exception e) {

                    }

//					progressDialog = MyToast.showChooseDialog(PDAOutStockConfirmActivity.this, "是否确认出库完成？",
//							new OnClickListener() {
//
//								@Override
//								public void onClick(View arg0) {
//									// TODO Auto-generated method stub
//									try {
//										Commit();
//										progressDialog.dismiss();
//
//										txtCode.setFocusable(true);
//										txtCode.setFocusableInTouchMode(true);
//										txtCode.requestFocus();
//										txtCode.setText("");
//
//									} catch (Exception e) {
//										progressDialog.dismiss();
//
//									}
//
//								}
//							});
                }
            }
        });

        // 扫码
        txtBarCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    if (!BarCode()) {
                        MyToast.showDialog(PDAOutStockConfirmActivity.this, "货品不存在！");
                        txtBarCode.requestFocus();
                        txtBarCode.setText("");
                    } else {
                        outStockConfirmadapter.clear();
                        outstock_list.setAdapter(outStockConfirmadapter);
                        outStockConfirmadapter.appendList(outList);

                        new FinishRefresh().execute();

                        txtBarCode.setFocusable(true);
                        txtBarCode.setFocusableInTouchMode(true);
                        txtBarCode.requestFocus();
                        txtBarCode.setText("");

                    }
                    if (outList.size() == 0 && outBarCodeList.size() != 0) {
                        try {
                            Commit();

                            txtCode.setFocusable(true);
                            txtCode.setFocusableInTouchMode(true);
                            txtCode.requestFocus();
                            txtCode.setText("");

                        } catch (Exception e) {

                        }


//						progressDialog = MyToast.showChooseDialog(PDAOutStockConfirmActivity.this, "是否确认出库完成？",
//								new OnClickListener() {
//
//									@Override
//									public void onClick(View arg0) {
//										// TODO Auto-generated method stub
//										try {
//											Commit();
//
//											txtCode.setFocusable(true);
//											txtCode.setFocusableInTouchMode(true);
//											txtCode.requestFocus();
//											txtCode.setText("");
//
//											progressDialog.dismiss();
//
//										} catch (Exception e) {
//											progressDialog.dismiss();
//
//										}
//
//									}
//								});
                    }

                    return true;
                }

                return true;


            }
        });
    }

    //提交
    private boolean Commit() {

        boolean flag = false;

        startProgressDialog("Loading...");

        JSONStringer jsonStr = new JSONStringer();
        String json_Str = null;
        try {
            jsonStr = JSONHelper.serialize(jsonStr, "");
            // jsonStr = new JSONStringer().object().key("list").value(jsonStr)
            // .endObject();
            // json_Str = JsonDateFormate.JsonFormate(jsonStr.toString());
        } catch (Exception e) {

        }

        RequestQueue mRequestQueue = Volley.newRequestQueue(PDAOutStockConfirmActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Method.POST,
                Constants.url_OutStockCheckAuditForRF + "?code=" + outBarCodeList.get(0).getOutStockInformCode()
                        + "&username=" + preferences.getString("name", "") + "&isOutAndCancel=" + isOutAndCancel,
                json_Str, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());

                    isOutAndCancel = jsonObject.getBooleanValue("HasWarning");

                    if (isOutAndCancel) {
                        try {
                            Commit();

                            txtCode.setFocusable(true);
                            txtCode.setFocusableInTouchMode(true);
                            txtCode.requestFocus();
                            txtCode.setText("");

                        } catch (Exception e) {

                        }

//                        progressDialog = MyToast.showChooseDialog(PDAOutStockConfirmActivity.this, "是否重新提交？",
//                                new OnClickListener() {
//
//                                    @Override
//                                    public void onClick(View arg0) {
//                                        // TODO Auto-generated method
//                                        // stub
//                                        try {
//                                            isOutAndCancel = true;
//                                            Commit();
//                                            progressDialog.dismiss();
//                                        } catch (Exception e) {
//                                            progressDialog.dismiss();
//                                        }
//                                    }
//                                });

                    } else {
                        isOutAndCancel = false;
                        outBarCodeList.clear();
                        outList.clear();
//                        MyToast.showDialog(PDAOutStockConfirmActivity.this, "出库完成！");
                        speechSynthesizer.speak(String.valueOf("出库完成"));
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

                outBarCodeList.clear();
                outList.clear();
                stopProgressDialog();

            }
        }, this.handler);
        mRequestQueue.add(request);

        return flag;
    }

    private void startDialog() {
        LayoutInflater inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.activity_out_stock_have_bar_code, null);
        _outstock_list = (PullToRefreshListView) view.findViewById(R.id.have_pulllist);
        _outstock_list.setMode(Mode.DISABLED);
        _outstock_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉的时候数据重置
                CurrentIndex = 1;

                getList(txtCode.getText().toString());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉的时候添加选项
                if (CurrentIndex <= CurrentIndex_Total) {

                    getList(txtCode.getText().toString());
                } else {
                    MyToast.showDialog(PDAOutStockConfirmActivity.this, "已加载全部数据");
                    new FinishRefresh().execute();
                }
            }
        });
        final AlertDialog ad = new AlertDialog.Builder(PDAOutStockConfirmActivity.this).create();
        outStockConfirmadapter = new StockCheckAdapter(PDAOutStockConfirmActivity.this, true);
        _outstock_list.setAdapter(outStockConfirmadapter);
        outStockConfirmadapter.appendList(outBarCodeList);
        ad.setView(view);
        ad.show();

    }

    public boolean BarCode() {
        boolean flag = false;
        String barCode = txtBarCode.getText().toString();

        for (int i = 0; i < outList.size(); i++) {
            if (outList.get(i).getBarCode().equals(barCode)) {
                flag = true;
                if (!ExistIsBarCode(outList.get(i).getBarCode())) {
                    StockCheckDetailModel model = new StockCheckDetailModel();
                    model.Code = outList.get(i).getCode();
                    model.BoxId = outList.get(i).getBoxId();
                    model.BarCode = outList.get(i).getBarCode();
                    model.Color = outList.get(i).getColor();
                    model.CreateBy = outList.get(i).getCreateBy();
                    model.CreateDate = outList.get(i).getCreateDate();
                    model.CustGoodsName = outList.get(i).getCustGoodsName();
                    model.CustomerCode = outList.get(i).getCustomerCode();
                    model.GoodsCategoryCode = outList.get(i).getGoodsCategoryCode();
                    model.GoodsCategoryMidCode = outList.get(i).getGoodsCategoryMidCode();
                    model.GoodsCategoryTopCode = outList.get(i).getGoodsCategoryTopCode();
                    model.GoodsCode = outList.get(i).getGoodsCode();
                    model.GoodsName = outList.get(i).getGoodsName();
                    model.GoodsPosCode = outList.get(i).getGoodsPosCode();
                    model.GoodsPosName = outList.get(i).getGoodsPosName();
                    model.GoodsRemark = outList.get(i).getGoodsRemark();
                    model.GoodsUnitCode = outList.get(i).getGoodsUnitCode();
                    model.InStockGoodsCode = outList.get(i).getInStockGoodsCode();
                    model.IsGift = outList.get(i).getIsGift();
                    model.Model = outList.get(i).getModel();
                    model.Num = "1";
                    model.OutStockCheckCode = outList.get(i).getOutStockCheckCode();
                    model.OutStockInformCode = outList.get(i).getOutStockInformCode();
                    model.OutStockPrice = outList.get(i).getOutStockPrice();
                    model.OutStockReallyPrice = outList.get(i).getOutStockReallyPrice();
                    model.ProductCode = outList.get(i).getProductCode();
                    model.ProductId = outList.get(i).getProductId();
                    model.ProductionDate = outList.get(i).getProductionDate();
                    model.QualityCode = outList.get(i).getQualityCode();
                    model.ShelfLife = outList.get(i).getShelfLife();
                    model.StandGrossWeight = outList.get(i).getStandGrossWeight();
                    model.StandNetWeight = outList.get(i).getStandNetWeight();
                    model.StandVolume = outList.get(i).getStandVolume();
                    model.Unit = outList.get(i).getUnit();
                    model.UnitName = outList.get(i).getUnitName();
                    model.UpdateBy = outList.get(i).getUpdateBy();
                    model.UpdateDate = outList.get(i).getUpdateDate();
                    outBarCodeList.add(model);
                }
                outList.get(i).setNum(String.valueOf(Integer.parseInt(outList.get(i).getNum()) - 1));
                if (outList.get(i).getNum().equals("0")) {
                    outList.remove(i);
                }
                if (flag) {

                    break;
                }
            }

        }

        return flag;
    }


    public boolean ExistIsBarCode(String barCode) {
        boolean flag = false;
        for (int i = 0; i < outBarCodeList.size(); i++) {
            if (outBarCodeList.get(i).getBarCode().equals(barCode)) {
                outBarCodeList.get(i).setNum(String.valueOf(Integer.parseInt(outBarCodeList.get(i).getNum()) + 1));
                flag = true;
                break;
            }

        }

        return flag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pdaout_stock_confirm, menu);
        return true;
    }

    private void getList(String Code) {
        startProgressDialog("Loading...");

        JSONStringer jsonStr = new JSONStringer();

        RequestQueue mRequestQueue = Volley.newRequestQueue(PDAOutStockConfirmActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Method.GET,
                url + Code,
                jsonStr.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    CurrentIndex++;

                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.toString());
                    int Total = jsonObject.getIntValue("Total");
                    if (Total % PageSize == 0) {
                        CurrentIndex_Total = Total / PageSize;
                    } else {
                        CurrentIndex_Total = Total / PageSize + 1;
                    }
                    com.alibaba.fastjson.JSONArray json_array = (com.alibaba.fastjson.JSONArray) jsonObject
                            .get("Details");
                    outList = JSONArray.parseArray(json_array.toString(), StockCheckDetailModel.class);
                    if (outStockConfirmadapter == null) {
                        outStockConfirmadapter = new StockCheckAdapter(PDAOutStockConfirmActivity.this, true);
                        outstock_list.setAdapter(outStockConfirmadapter);
                        outStockConfirmadapter.appendList(outList);
                    } else {
                        outStockConfirmadapter = new StockCheckAdapter(PDAOutStockConfirmActivity.this, true);
                        outstock_list.setAdapter(outStockConfirmadapter);
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

    private void setParams() {
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_AMR);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);
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
