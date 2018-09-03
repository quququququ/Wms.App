package com.wms.newwmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.wms.newwmsapp.R;
import com.wms.newwmsapp.adapter.HasDownToOtherAdapter;
import com.wms.newwmsapp.base.BaseActivity;
import com.wms.newwmsapp.model.DownToOtherModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/7/5.
 */

public class HasDownListActivity extends BaseActivity implements HasDownToOtherAdapter.DownToOtherListener {
    private List<DownToOtherModel.DataBean> downDataList = new ArrayList<DownToOtherModel.DataBean>();
    private HasDownToOtherAdapter mAdapter;
    private ListView lvDownList;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_has_down_list);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                finish(); //结束当前的activity的生命周期
            }
        });

        downDataList = (List<DownToOtherModel.DataBean>) getIntent().getSerializableExtra("postuser");
        lvDownList = (ListView) findViewById(R.id.lv_down_list);
        mAdapter = new HasDownToOtherAdapter(this);
        lvDownList.setAdapter(mAdapter);
        mAdapter.setOnIPostPackageNoListener(this);
        mAdapter.setData(downDataList);
    }

    @Override
    public void deletePost(int position) {
        downDataList.remove(position);
//        downDataList.get(position).setUpNo("0");
//        downDataList.get(position).setNum(downDataList.get(position).getOriginNo()+"");
        Intent intent = new Intent();
        intent.putExtra("deleteNum",(Serializable) downDataList);
        setResult(2, intent);
        finish(); //结束当前的activity的生命周期
    }

}
