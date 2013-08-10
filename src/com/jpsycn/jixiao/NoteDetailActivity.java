package com.jpsycn.jixiao;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jpsycn.jixiao.bean.LogBean;
import com.jpsycn.jixiao.util.SysUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NoteDetailActivity extends Activity {

	private static final String TAG = "NoteDetailActivity";
	//private Date date;
	private int year;
	private int month;
	private int day;
	private Date date;
	private TextView noteDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_detail);

		Bundle extras = getIntent().getExtras();
		date = (Date) extras.getSerializable("date");
		
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH)+1;
		day = c.get(Calendar.DAY_OF_MONTH);
		
		// Cursor cursor = getContentResolver().query(uri, null, null, null,
		// null);

		noteDetail = (TextView) findViewById(R.id.note_detail);
		MyAsyncTask task=new MyAsyncTask();
		task.execute();
	}

	private class MyAsyncTask extends
			AsyncTask<Void, Void, List<LogBean>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(NoteDetailActivity.this, "正在登录",
					"正在登录,请稍候.....");

		}

		@Override
		protected List<LogBean> doInBackground(Void... params) {
			try {
				SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
				String username = preferences.getString("username", "");
				String password = preferences.getString("password","");
				
				Map<String, String> cookies = SysUtils
						.login(username, password);
				
				List<LogBean> beanList = SysUtils.getLogBeanList(cookies, year, month);
			
				return beanList;
			} catch (IOException e) {
				Log.e(TAG, "登录异常：", e);
			}
			return null;

		}

		@Override
		protected void onPostExecute(List<LogBean> result) {
			super.onPostExecute(result);
			dialog.dismiss();

			for(LogBean b:result){
				if(b.getDate().equals(date)){
					noteDetail.setText(b.getContent()+" "+b.getStatus());
				}
			}
		}

	}
}
