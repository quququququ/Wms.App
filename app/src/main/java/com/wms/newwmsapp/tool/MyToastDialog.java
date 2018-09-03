package com.wms.newwmsapp.tool;

import com.wms.newwmsapp.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class MyToastDialog extends Dialog {
	private static MyToastDialog myToastDialog = null;
	public MyToastDialog progressDialog;

	public MyToastDialog(Context context) {
		super(context);
	}

	public MyToastDialog(Context context, int theme) {
		super(context, theme);
	}

	public static MyToastDialog createDialog(Context context) {
		myToastDialog = new MyToastDialog(context, R.style.CustomProgressDialog);
		myToastDialog.setContentView(R.layout.dialog_showdialog);
		myToastDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		TextView btn = (TextView) myToastDialog.findViewById(R.id.btn);
		btn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myToastDialog.dismiss();
			}
		});

		return myToastDialog;
	}

	public MyToastDialog setMessage(String strMessage) {
		TextView message = (TextView) myToastDialog.findViewById(R.id.message);

		if (message != null) {
			message.setText(strMessage);
		}

		return myToastDialog;
	}
}
