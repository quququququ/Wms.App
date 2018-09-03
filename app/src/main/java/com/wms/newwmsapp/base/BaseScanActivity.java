package com.wms.newwmsapp.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class BaseScanActivity extends BaseActivity {

	protected BroadcastReceiver mReceiver;
	private IntentFilter mFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// 此处获取扫描结果信息
			}
		};

		mFilter = new IntentFilter("ACTION_BAR_SCAN");
		// 将广播的优先级调到最高1000
		mFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 注册广播来获取扫描结果
		this.registerReceiver(mReceiver, mFilter);
	}

	@Override
	protected void onPause() {
		// 注销获取扫描结果的广播
		this.unregisterReceiver(mReceiver);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		mReceiver = null;
		mFilter = null;
		super.onDestroy();
	}
}
