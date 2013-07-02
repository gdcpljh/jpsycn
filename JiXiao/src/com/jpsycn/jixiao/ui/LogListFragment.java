package com.jpsycn.jixiao.ui;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jpsycn.jixiao.R;
import com.jpsycn.jixiao.adapter.BBSListAdapter;
import com.jpsycn.jixiao.utils.LogUtils;
import com.jpsycn.jixiao.utils.SysUtils;

public class LogListFragment extends ListFragment{

	public static final String TAG = "HomeActivity";
	private BBSListAdapter mAdapter;
	private SortedMap<Integer, String> map;
	private SharedPreferences preferences;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		preferences = activity.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		String username = preferences.getString("username", "");
		String password = preferences.getString("password", "");
		if("".equals(username)||"".equals(password)){
			Intent intent=new Intent(activity,LoginActivity.class);
			startActivity(intent);
		}
		
//		setContentView(R.layout.activity_home);
		XXAsyncTask task = new XXAsyncTask();
		task.execute();
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bbs_list, null);
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.refresh, menu);
		inflater.inflate(R.menu.edit, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			XXAsyncTask task = new XXAsyncTask();
			task.execute();
			return true;
		case R.id.menu_edit:
			Intent intent=new Intent(getActivity(),LogActivity.class);
			startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		Intent intent = new Intent(getActivity(), BBSDetailActivity.class);
		intent.putExtra("detailId",
				String.valueOf(mAdapter.getItemId(position)));
		intent.putExtra("bbsId", "999710");
		startActivity(intent);
	}

	private class XXAsyncTask extends AsyncTask<Void, Void, Void> {

		private ProgressDialog progressDialog;
		
		@Override
		protected Void doInBackground(Void... params) {
			try {

				Map<String, String> cookies = SysUtils.getCookies(preferences.getString("username", ""), preferences.getString("password", ""));
				map = SysUtils.getLogList(cookies, "999710");

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("正在请求数据,请稍候...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			mAdapter = new BBSListAdapter(getActivity(), map);
			setListAdapter(mAdapter);
		}
	}
}
