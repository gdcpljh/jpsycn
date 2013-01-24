package com.jpsycn.jixiao.ui;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jpsycn.jixiao.R;
import com.jpsycn.jixiao.adapter.XXAdapter;
import com.jpsycn.jixiao.utils.SysUtils;

public class HomeActivity extends BaseListActivity {

	private XXAdapter mAdapter;
	private SortedMap<Integer, String> map;
	private Map<String, String> cookies;
	private ProgressDialog progressDialog;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		preferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		String username = preferences.getString("username", "");
		String password = preferences.getString("password", "");
		if("".equals(username)||"".equals(password)){
			Intent intent=new Intent(this,LoginActivity.class);
			startActivity(intent);
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		XXAsyncTask task = new XXAsyncTask();
		task.execute();

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.refresh, menu);
		getSupportMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			XXAsyncTask task = new XXAsyncTask();
			task.execute();
			return true;
		case R.id.menu_edit:
			Intent intent=new Intent(this,LogActivity.class);
			startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra("detailId",
				String.valueOf(mAdapter.getItemId(position)));
		intent.putExtra("bbsId", "999710");
		startActivity(intent);
	}

	private class XXAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {

				cookies = SysUtils.getCookies(preferences.getString("username", ""), preferences.getString("password", ""));
				map = SysUtils.getBBSList(cookies, "999710");

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(HomeActivity.this);
			progressDialog.setMessage("正在请求数据,请稍候...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			mAdapter = new XXAdapter(HomeActivity.this, map);
			setListAdapter(mAdapter);
		}
	}
}
