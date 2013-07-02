package com.jpsycn.jixiao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BBSListAdapter extends BaseAdapter {

	private SortedMap<Integer, String> map;
	private LayoutInflater mInflater;

	public BBSListAdapter(Context context, SortedMap<Integer, String> map) {
		this.map = map;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return map == null ? 0 : map.size();
	}

	@Override
	public String getItem(int position) {
		Set<Integer> set = map.keySet();
		List<Integer> list=new ArrayList<Integer>(set);
		//使id 倒叙，这样最新的 排在前面 
		Integer key= list.get(list.size()-position-1);
		return map.get(key);
	}

	@Override
	public long getItemId(int position) {
		Set<Integer> set = map.keySet();
		List<Integer> list=new ArrayList<Integer>(set);
		Integer key= list.get(list.size()-position-1);
		return key;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(
					android.R.layout.simple_list_item_2, null);
			holder = new ViewHolder();
			holder.text1 = (TextView) convertView
					.findViewById(android.R.id.text1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		String item = getItem(position);
		holder.text1.setText(item);

		return convertView;
	}

	static class ViewHolder {
		TextView text1;
	}

}
