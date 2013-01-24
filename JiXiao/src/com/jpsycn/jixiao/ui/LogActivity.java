package com.jpsycn.jixiao.ui;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jpsycn.jixiao.R;
import com.jpsycn.jixiao.utils.DateUtil;
import com.jpsycn.jixiao.utils.SysUtils;

public class LogActivity extends BaseActivity {

	public ProgressDialog progressDialog;
	private String content;
	private SharedPreferences preferences;
	private TextView contentView;
	private EditText logTimeView;
	
	// 获取一个日历对象
	Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
	// 获取注册的时间
	DatePickerDialog.OnDateSetListener start = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// 修改日历控件的年，月，日
			// 这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
			dateAndTime.set(Calendar.YEAR, year);
			dateAndTime.set(Calendar.MONTH, monthOfYear);
			dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			// 将页面TextView的显示更新为最新时间
			logTimeView.setText(DateUtil.format(dateAndTime));
		}
	};
	private String time;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		preferences=getSharedPreferences("userinfo",Context.MODE_PRIVATE);
		
		contentView = (TextView) findViewById(R.id.log_content);
		logTimeView = (EditText) findViewById(R.id.log_time);
		logTimeView.setText(DateUtil.format(new Date()));
		
		logTimeView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(LogActivity.this, start,
						dateAndTime.get(Calendar.YEAR),
						dateAndTime.get(Calendar.MONTH),
						dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.submit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_submit:
			
			submit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void submit() {
		
		content = contentView.getText().toString();
		time = logTimeView.getText().toString();
		XXAsyncTask task=new XXAsyncTask();
		task.execute();
	}
	
	private class XXAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {

				Map<String, String> cookies = SysUtils.getCookies(preferences.getString("username", ""), preferences.getString("password", ""));
				
				SysUtils.addLog(cookies,time,content);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(LogActivity.this);
			progressDialog.setMessage("正在提交,请稍候...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			LogActivity.this.finish();
		}
	}
}
