package com.jpsycn.jixiao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import com.jpsycn.jixiao.bean.LogBean;
import com.jpsycn.jixiao.util.DateUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LeaderLogAdapter extends BaseAdapter {

	private List<LogBean> list;
	private LayoutInflater mInflater;

	public LeaderLogAdapter(Context context, List<LogBean> list) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public LogBean getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(
					android.R.layout.simple_list_item_2, null);
			holder = new ViewHolder();
			holder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
			holder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		LogBean bean = getItem(position);
		holder.text1.setText(DateUtil.formatToString(bean.getDate()));
		holder.text2.setText(bean.getContent());

		return convertView;
	}

	static class ViewHolder {
		TextView text1;
		TextView text2;
	}

}
