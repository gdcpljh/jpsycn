package com.jpsycn.jixiao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BBSDetailAdapter extends BaseAdapter {

	private List<String> list;
	private LayoutInflater mInflater;

	public BBSDetailAdapter(Context context, List<String> list) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public String getItem(int position) {
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
