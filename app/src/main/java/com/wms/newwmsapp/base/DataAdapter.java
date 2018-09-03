package com.wms.newwmsapp.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class DataAdapter<T> extends BaseAdapter {

	protected List<T> mDataList = new ArrayList<T>();
	protected LayoutInflater mInflater = null;
	protected Context mContext;
	protected boolean mIsCircle;
	protected DataViewHolder holder;

	public DataAdapter(Context context) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}

	public DataAdapter(Context context, boolean isCircle) {
		this.mContext = context;
		this.mIsCircle = isCircle;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	// 适配数据
	public abstract void renderData(int position, DataViewHolder vh);

	// 将适配的布局中所有要用到的视图的id，用一个整型数组返回
	public abstract int[] getFindViewByIDs();

	// 得到要适配的布局文件
	public abstract View getLayout(int position, DataViewHolder vh,
			ViewGroup parent);

	@SuppressWarnings("hiding")
	public final <T> T getView(Class<T> clazz, int key) {
		return (T) holder.getView(clazz, key);
	}

	public final View getResourceView(int id) {
		return mInflater.inflate(id, null);
	}

	public final View getResourceView(int id, ViewGroup parent) {
		return mInflater.inflate(id, parent, false);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataViewHolder vh;
		if (convertView == null) {
			vh = new DataViewHolder();
			convertView = getLayout(position, vh, parent);
			if (convertView == null) {
				return null;
			}

			int[] idAry = getFindViewByIDs();
			if (idAry == null) {
				idAry = new int[] {};
			}
			// 将id对应的视图保存到ViewHolder中的HashMap<K, V>中
			for (int id : idAry) {
				vh.setView(id, convertView.findViewById(id));
			}
			convertView.setTag(vh);
		} else {
			vh = (DataViewHolder) convertView.getTag();
		}
		holder = vh;
		holder.setConvertView(convertView);
		this.renderData(position, vh);
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 获取列表大小
	public int getSize() {
		return !mIsCircle ? mDataList.size() : Integer.MAX_VALUE;
	}

	// 获取列表
	public List<T> getDataList() {
		return mDataList;
	}

	// 获取列表
	public List<T> getItems() {
		return mDataList;
	}

	// 获取position位置的对象
	public T getItemT(int position) {
		return mDataList.get(position);
	}

	// 插入一条数据
	public void insert(T data) {
		mDataList.add(0, data);
		notifyDataSetChanged();
	}

	// 在某一位置插入一条数据
	public void insert(int index, T data) {
		mDataList.add(index, data);
		notifyDataSetChanged();
	}

	// 在某一位置插入数据列表
	public void insertList(int index, List<T> dataList) {
		insertList(index, dataList, true);
	}

	public void insertList(int index, List<T> dataList,
			boolean isNotifyDataSetChanged) {

		if (index < 0 || dataList == null || dataList.size() <= 0) {
			return;
		}

		if (dataList != null && dataList.size() > 0) {
			if (!mDataList.isEmpty()) {
				if (index >= 0 && index < getCount()) {
					mDataList.addAll(index, dataList);
				}
			} else {
				if (index == 0) {
					mDataList.addAll(index, dataList);
				}
			}

			if (isNotifyDataSetChanged) {
				notifyDataSetChanged();
			}

		}
	}

	// 插入一条数据
	public void append(T data) {
		mDataList.add(data);
		notifyDataSetChanged();
	}

	// 插入数据列表
	public void appendList(List<T> dataList) {
		appendList(dataList, true);
	}

	public void appendList(List<T> dataList, boolean notifyDataSetChanged) {
		mDataList.addAll(dataList);
		if (notifyDataSetChanged) {
			notifyDataSetChanged();
		}
	}

	// 重新设置数据列表
	public void setDataList(List<T> mDataList) {
		this.mDataList = mDataList;
		notifyDataSetChanged();
	}

	// 替换某一对象
	public void replace(T data) {
		int idx = mDataList.indexOf(data);
		replace(idx, data);
	}

	public void replace(int index, T data) {
		if (index < 0)
			return;
		if (index > mDataList.size() - 1)
			return;
		mDataList.set(index, data);
		notifyDataSetChanged();
	}

	// 移动数据源中position位置的数据
	public void removeItem(int position) {
		if (mDataList.size() <= 0)
			return;
		if (position < 0)
			return;
		if (position > mDataList.size() - 1)
			return;

		mDataList.remove(position);
		notifyDataSetChanged();
	}

	// 移除一个列表的数据
	public void removeItems(List<T> dataList) {
		removeItems(dataList, true);
	}

	public void removeItems(List<T> dataList, boolean isNeedNotifyDataSetChanged) {
		if (mDataList.size() <= 0)
			return;

		if (dataList == null || dataList.size() <= 0)
			return;

		mDataList.removeAll(dataList);

		if (isNeedNotifyDataSetChanged) {
			notifyDataSetChanged();
		}
	}

	// 清空适配的数据
	public void clear() {
		clear(true);
	}

	// 清空适配的数据
	public void clear(boolean isNeedNotifyDataSetChanged) {
		mDataList.clear();
		if (isNeedNotifyDataSetChanged) {
			notifyDataSetChanged();
		}
	}
}
