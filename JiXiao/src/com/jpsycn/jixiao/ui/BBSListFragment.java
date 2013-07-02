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
import com.jpsycn.jixiao.utils.SysUtils;

public class BBSListFragment extends ListFragment {

	public static final String TAG = "HomeActivity";
	private BBSListAdapter mAdapter;
	// private SortedMap<Integer, String> map;
	private SharedPreferences preferences;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		preferences = activity.getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		String username = preferences.getString("username", "");
		String password = preferences.getString("password", "");

		if ("".equals(username) || "".equals(password)) {
			Intent intent = new Intent(activity, LoginActivity.class);
			startActivity(intent);
		}

		BBSListAsyncTask task = new BBSListAsyncTask();
		task.execute(username, password, "999710");
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
			BBSListAsyncTask task = new BBSListAsyncTask();
			task.execute();
			return true;
		case R.id.menu_edit:
			Intent intent = new Intent(getActivity(), LogActivity.class);
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

	private class BBSListAsyncTask extends
			AsyncTask<String, Void, SortedMap<Integer, String>> {

		private ProgressDialog progressDialog;

		@Override
		protected SortedMap<Integer, String> doInBackground(String... params) {
			try {

				Map<String, String> cookies = SysUtils.getCookies(params[0],
						params[1]);
				return SysUtils.getBBSList(cookies, params[2]);

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
		protected void onPostExecute(SortedMap<Integer, String> result) {
			if (null != progressDialog) {
				progressDialog.dismiss();
			}
			mAdapter = new BBSListAdapter(getActivity(), result);
			setListAdapter(mAdapter);
		}
	}
}
