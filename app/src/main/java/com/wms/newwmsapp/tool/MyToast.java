package com.wms.newwmsapp.tool;

import android.app.Activity;

public class MyToast {

	public static void showDialog(Activity activity, String message_str){
		MyToastDialog progressDialog=MyToastDialog.createDialog(activity);
		progressDialog.setMessage(message_str);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
	
	public static MyChooseToastDialog showChooseDialog(Activity activity, String message_str, android.view.View.OnClickListener listener) {
		MyChooseToastDialog progressDialog = MyChooseToastDialog.createDialog(activity);
		progressDialog.setMessage(message_str);
		progressDialog.setOnClickListener(listener);
		progressDialog.setCancelable(false);
		progressDialog.show();
		
		return progressDialog;
	}


	
	public static MyTimeToastDialog showTimeDialog(Activity activity, String title_str, String time_str, android.view.View.OnClickListener listener) {
		MyTimeToastDialog progressDialog = MyTimeToastDialog.createDialog(activity);
		progressDialog.setTitle(title_str);
		progressDialog.setTime(time_str, activity);
		progressDialog.setOnClickListener(listener);
		progressDialog.setCancelable(false);
		progressDialog.show();
		
		return progressDialog;
	}
}
