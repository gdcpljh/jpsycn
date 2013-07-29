package com.jpsycn.jixiao;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;

import com.jpsycn.jixiao.R;
import com.jpsycn.jixiao.adapter.XXAdapter;
import com.jpsycn.jixiao.util.SysUtils;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * 目标体系
 * 
 * @author feicien
 * 
 */
public class BBSListActivity extends ListActivity {

	private static final String TAG = "BBSListActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbs_list);
		
		MyAsyncTask task=new MyAsyncTask();
		task.execute();
		

	}

	private class MyAsyncTask extends
			AsyncTask<Void, Void, SortedMap<Integer, String>> {

		
		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(BBSListActivity.this, "请求数据",
					"正在请求数据,请稍候.....");

		}

		@Override
		protected SortedMap<Integer, String> doInBackground(Void... params) {
			try {
				Map<String, String> cookies = SysUtils.login("", "");
				
				SortedMap<Integer,String> bbsList = SysUtils.getBBSList(cookies, "999710");
				
				return bbsList;
			} catch (IOException e) {
				Log.e(TAG, "获取目标体系异常：", e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(SortedMap<Integer, String> result) {
			super.onPostExecute(result);

			dialog.dismiss();

			XXAdapter adapter=new XXAdapter(BBSListActivity.this, result);

			setListAdapter(adapter);
		}

	}
}
