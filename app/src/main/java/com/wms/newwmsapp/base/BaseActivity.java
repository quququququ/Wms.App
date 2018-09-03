package com.wms.newwmsapp.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.wms.newwmsapp.tool.Constants;
import com.wms.newwmsapp.tool.CustomProgressDialog;
import com.wms.newwmsapp.tool.MyToast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

@SuppressLint("HandlerLeak")
public class BaseActivity extends Activity {

	protected SharedPreferences preferences;
	public static String code, name;//身份校验
	public static String stockCode, custGoodsCode, custGoodsName;//身份选择的仓库和货主信息
	protected Handler handler;
	private CustomProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		preferences = getSharedPreferences("application", Context.MODE_PRIVATE);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String message = (String) msg.obj;
					MyToast.showDialog(BaseActivity.this, message);
					break;
				}
			}
		};
	}
	
	protected int getVersionCode(){
		try {  
	        PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);  
	        return pi.versionCode;  
	    } catch (NameNotFoundException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	        return 0;  
	    }  
	}
	
	protected String getVersionName()
    {  
        try {  
        	PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);  
            return pi.versionName;  
        } catch (NameNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return "";  
        }  
    } 
	
	//下载到制定文件夹下
	public class downloadApkThread extends Thread {
		private String mSavePath = Environment.getExternalStorageDirectory()+"/WMS_DOWNLOAD";//安装路径
		private String filename = "WMSApp.apk";//文件名字
		private String mDownPath = Constants.URL_DOWNLOAD;//下载地址

		@Override
		public void run() {
			try {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					//创建文件夹
					File file = new File(mSavePath);
					if (!file.exists()) {
						file.mkdir();
					}
					//创建本地文件(将下载文件加载到此文件下)
					File apkFile = new File(mSavePath, filename);
					//连接服务器
					URL url = new URL(mDownPath);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					FileOutputStream fos = new FileOutputStream(apkFile);
					byte buf[] = new byte[1024];
					do {
						int numread = is.read(buf);
						if (numread <= 0) {
							Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
							Uri uri = Uri.fromFile(apkFile);
							intent.setData(uri);
							sendBroadcast(intent);
							
							installApk(BaseActivity.this, mSavePath, filename);
							break;
						}
						fos.write(buf, 0, numread);
					} while (true);
					fos.close();
					is.close();
				} else {
					MyToast.showDialog(BaseActivity.this, "请检查SD卡是否插好");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	//安装apk
	private void installApk(Context mContext, String mSavePath, String name) {
		File apkfile = new File(mSavePath, name);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
	
	protected void startProgressDialog(String str){
		if(progressDialog==null){
			progressDialog=CustomProgressDialog.createDialog(BaseActivity.this);
			progressDialog.setMessage(str);
			progressDialog.setCancelable(false);
		}
		progressDialog.show();
	}
	
	protected void stopProgressDialog(){
		if(progressDialog!=null){
			progressDialog.dismiss();
			progressDialog=null;
		}
	}
}