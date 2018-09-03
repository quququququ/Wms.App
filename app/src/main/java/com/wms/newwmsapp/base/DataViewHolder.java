package com.wms.newwmsapp.base;

import android.annotation.SuppressLint;
import android.util.SparseArray;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

//数据适配器视图缓存
@SuppressLint("UseSparseArrays")
public class DataViewHolder {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SparseArray<View> mapView = new SparseArray();
	private Map<String, Object> mapData = new HashMap<String, Object>();

	private View mConvertView;

	public View getConvertView() {
		return mConvertView;
	}

	public void setConvertView(View convertView) {
		this.mConvertView = convertView;
	}

	public void setView(int key, View v) {
		this.mapView.put(key, v);
	}

	@SuppressWarnings("unchecked")
	public <T> T getView(int key) {
		return (T) this.mapView.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getView(Class<T> clazz, int key) {
		return (T) this.mapView.get(key);
	}

	public void setData(String key, Object value) {
		mapData.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(String key) {
		return (T) mapData.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(Class<T> clazz, String key) {
		return (T) mapData.get(key);
	}
}
