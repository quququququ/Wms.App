package com.wms.newwmsapp.activity;

import com.handmark.pulltorefresh.library.view.PullToRefreshBase;
import com.handmark.pulltorefresh.library.view.PullToRefreshListView;
import com.handmark.pulltorefresh.library.view.PullToRefreshBase.Mode;
import com.wms.newwmsapp.R;
import com.wms.newwmsapp.R.id;
import com.wms.newwmsapp.R.layout;
import com.wms.newwmsapp.R.menu;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.tool.MyToast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class OutStockHaveBarCodeActivity extends BaseActivity {

	private PullToRefreshListView outstock_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_out_stock_have_bar_code);
	
		
		outstock_list = (PullToRefreshListView) findViewById(R.id.outstockconfim_pulllist);
		outstock_list.setMode(Mode.BOTH);

		

		
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.out_stock_have_bar_code, menu);
		return true;
	}

}
