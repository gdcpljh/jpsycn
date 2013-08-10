package com.jpsycn.jixiao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.jpsycn.jixiao.adapter.LeaderLogAdapter;
import com.jpsycn.jixiao.bean.LogBean;
import com.jpsycn.jixiao.util.SysUtils;

public class LeaderNoteActivity extends ListActivity {

	public static final String TAG = "LeaderNoteActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbs_list);
		
		MyAsyncTask task=new MyAsyncTask();
		task.execute("879","2013", "8");
		

	}
	private class MyAsyncTask extends
			AsyncTask<String, Void, List<LogBean>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(LeaderNoteActivity.this, "请求数据",
					"正在请求数据,请稍候.....");

		}

		@Override
		protected List<LogBean> doInBackground(String... params) {
			try {
				SharedPreferences preferences = getSharedPreferences(
						"user_info", Context.MODE_PRIVATE);
				String username = preferences.getString("username", "");
				String password = preferences.getString("password", "");
				
				Map<String, String> cookies = SysUtils
						.login(username, password);

				List<LogBean> list = SysUtils.getLeaderLog(cookies,params[0],params[1],params[2] );

				return list;
			} catch (IOException e) {
				Log.e(TAG, "获取领导日志异常：", e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<LogBean> result) {
			super.onPostExecute(result);

			dialog.dismiss();

			Log.d(TAG,result.toString());
			
			LeaderLogAdapter adapter = new LeaderLogAdapter(LeaderNoteActivity.this, result);

			setListAdapter(adapter);
		}

	}
}
