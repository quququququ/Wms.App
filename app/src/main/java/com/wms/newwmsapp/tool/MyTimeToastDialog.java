package com.wms.newwmsapp.tool;

import com.wms.newwmsapp.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyTimeToastDialog extends Dialog{

	private static MyTimeToastDialog myToastDialog = null;
	public MyTimeToastDialog progressDialog;

	public MyTimeToastDialog(Context context) {
		super(context);
	}

	public MyTimeToastDialog(Context context, int theme) {
		super(context, theme);
	}

	public static MyTimeToastDialog createDialog(Context context) {
		myToastDialog = new MyTimeToastDialog(context, R.style.CustomProgressDialog);
		myToastDialog.setContentView(R.layout.dialog_showtimedialog);
		myToastDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		TextView btn_cancel = (TextView) myToastDialog.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myToastDialog.dismiss();
			}
		});

		return myToastDialog;
	}

	public MyTimeToastDialog setTitle(String strTitle) {
		TextView title = (TextView) myToastDialog.findViewById(R.id.title);

		if (title != null) {
			title.setText(strTitle);
		}
		
		return myToastDialog;
	}

	public MyTimeToastDialog setOnClickListener(android.view.View.OnClickListener listener) {
		TextView btn_ok = (TextView) myToastDialog.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(listener);

		return myToastDialog;
	}
	
	public void setTime(String strTime, Activity activity){
		Button time = (Button) myToastDialog.findViewById(R.id.time);
		new TimeDatePicker().selectTime(time, activity, activity);
		time.setText(strTime);
	}
	
	public String getTime(){
		Button time = (Button) myToastDialog.findViewById(R.id.time);
		return time.getText().toString();
	}
}
