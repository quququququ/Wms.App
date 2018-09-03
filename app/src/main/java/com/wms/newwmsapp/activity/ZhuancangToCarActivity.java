package com.wms.newwmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.tool.MyToast;

public class ZhuancangToCarActivity extends BaseActivity {
    private ImageView back;
    private Button btnSearch;
    private EditText txtWavePickupConfirmCode;
    private TextView tvTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_seeding);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        tvTitleName = (TextView) findViewById(R.id.tv_title_name);
        tvTitleName.setText("转仓装车");
        txtWavePickupConfirmCode = (EditText) findViewById(R.id.WavePickupConfirmCode);
        txtWavePickupConfirmCode.setHint("请扫描或者输入车牌号");

        txtWavePickupConfirmCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    goNext();
                    return true;
                }
                return true;
            }
        });

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                goNext();
            }
        });

    }

    private void goNext() {
        String pickCode = txtWavePickupConfirmCode.getText().toString().trim();
        if (TextUtils.isEmpty(pickCode)) {
            MyToast.showDialog(ZhuancangToCarActivity.this, "请扫描或者输入车牌号！");
            return;

        }

        Intent intent = new Intent(ZhuancangToCarActivity.this,FinishToCarActivity.class);
        intent.putExtra(FinishToCarActivity.CAR_PLATE,pickCode);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seeding, menu);
        return true;
    }

}
