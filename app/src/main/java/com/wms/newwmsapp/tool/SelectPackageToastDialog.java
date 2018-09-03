package com.wms.newwmsapp.tool;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.wms.newwmsapp.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SelectPackageToastDialog extends Dialog {

    private static SelectPackageToastDialog myToastDialog = null;
    private static EditText input_package;
    public SelectPackageToastDialog progressDialog;
    private static List<String> postStr = new ArrayList<String>();

    public SelectPackageToastDialog(Context context) {
        super(context);
    }

    public SelectPackageToastDialog(Context context, int theme) {
        super(context, theme);
    }

    public static SelectPackageToastDialog createDialog(final Context context) {
        postStr.clear();
        myToastDialog = new SelectPackageToastDialog(context, R.style.CustomProgressDialog);
        myToastDialog.setContentView(R.layout.pop_select_package);
        myToastDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        myToastDialog.setCancelable(false);
        TextView btn_cancel = (TextView) myToastDialog.findViewById(R.id.btn_cancel);
        input_package = (EditText) myToastDialog.findViewById(R.id.input_package);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.send(postStr);
                    myToastDialog.dismiss();
                }
            }
        });

        TextView btn_ok = (TextView) myToastDialog.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (null != mListener) {
                    mListener.send(postStr);
                    myToastDialog.dismiss();
                }
            }
        });
        input_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_package.setFocusable(true);//设置输入框可聚集
                input_package.setFocusableInTouchMode(true);//设置触摸聚焦
                input_package.requestFocus();//请求焦点
                input_package.findFocus();//获取焦点
                InputMethodManager imm1 = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                imm1.showSoftInput(input_package, InputMethodManager.SHOW_FORCED);// 显示输入法
            }
        });
        input_package.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    postStr.add(input_package.getText().toString().trim());
                    input_package.setText("");
                    input_package.setHint("继续扫描包裹");
                    if(mListener!=null){
                        mListener.updateList(postStr);
                    }
                    return true;
                }
                return true;
            }
        });

        return myToastDialog;
    }

    public SelectPackageToastDialog setMessage(String strMessage) {
        TextView message = (TextView) myToastDialog.findViewById(R.id.message);

        if (message != null) {
            message.setText(strMessage);
        }

        return myToastDialog;
    }

    public List<String> getStr() {
        return postStr;
    }

    private static IPostPackageNoListener mListener;

    public void setOnIPostPackageNoListener(IPostPackageNoListener mListener) {
        this.mListener = mListener;
    }


    public interface IPostPackageNoListener {
        void updateList(List<String> strList);
        void send(List<String> strList);
    }
}
