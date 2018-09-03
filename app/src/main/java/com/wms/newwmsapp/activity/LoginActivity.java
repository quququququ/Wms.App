package com.wms.newwmsapp.activity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.LoginModel;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.MyChooseToastDialog;
import com.wms.newwmsapp.tool.MyToast;
import com.wms.newwmsapp.volley.Request.Method;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.Response;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.toolbox.JsonObjectRequest;
import com.wms.newwmsapp.volley.toolbox.Volley;

public class LoginActivity extends BaseActivity {

	private long exitTimeMillis = System.currentTimeMillis();
	private EditText userName, passWord;
	private CheckBox check_name;
	private Button button_login;
	private TextView versionName;
	private MyChooseToastDialog progressDialog;
	private LoginModel loginModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		userName = (EditText) findViewById(R.id.username);
		passWord = (EditText) findViewById(R.id.password);
		check_name = (CheckBox) findViewById(R.id.check_name);
		button_login = (Button) findViewById(R.id.button_login);
		versionName = (TextView) findViewById(R.id.versionName);
		
		String username_msg = preferences.getString("username", "");
		String password_msg = preferences.getString("password", "");
		if ((!TextUtils.isEmpty(username_msg))&& (!TextUtils.isEmpty(password_msg))) {
			userName.setText(username_msg);
			passWord.setText(password_msg);
		}
		versionName.setText("版本号 : "+getVersionName());
		
		getVersonCheck();

		button_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if ((!TextUtils.isEmpty(userName.getText().toString().trim())) && (!TextUtils.isEmpty(passWord.getText().toString().trim()))) {

					goLogin(userName.getText().toString().trim(), passWord.getText().toString().trim());
				} else {
					MyToast.showDialog(LoginActivity.this, "请填写完整信息");
				}
			}
		});
	}
	
	private void getVersonCheck(){
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object()
					.key("versionCode").value(getVersionCode())
					.endObject();
		} catch (Exception e) {
			
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(LoginActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_AppUpdate, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							boolean IsUpdate = response.getBoolean("IsUpdate");
							if(IsUpdate){
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
	
	private void startUpdateDialog(){
		progressDialog = MyToast.showChooseDialog(LoginActivity.this, "已检测到最新版本，是否需要更新?", new android.view.View.OnClickListener(){

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

	private void goLogin(final String username, String password) {
		startProgressDialog("Loading...");
		JSONStringer jsonStr = null;
		try {
			jsonStr = new JSONStringer().object().key("code").value(username).key("pwd").value(password).endObject();
		} catch (Exception e) {
			
		}

		RequestQueue mRequestQueue = Volley.newRequestQueue(LoginActivity.this);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				Constants.url_login, jsonStr.toString(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						loginModel = com.alibaba.fastjson.JSONObject.parseObject(response.toString(), LoginModel.class);
						if(loginModel.isIsSuccess()){
							if (check_name.isChecked()) {
								preferences.edit().putString("username",userName.getText().toString().trim()).commit();
								preferences.edit().putString("password",passWord.getText().toString().trim()).commit();
							} else {
								preferences.edit().putString("username", "").commit();
								preferences.edit().putString("password", "").commit();
							}
                            preferences.edit().putString("code",loginModel.getUserCode()).commit();
                            preferences.edit().putString("name",loginModel.getUserName()).commit();
                            preferences.edit().putString("stocks",response.toString()).commit();
                            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), loginModel.getErrorMessage(), Toast.LENGTH_LONG).show();
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - exitTimeMillis == 0 || currentTime - exitTimeMillis > 1500) {
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
