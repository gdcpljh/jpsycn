package com.jpsycn.jixiao.ui;

import java.io.IOException;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jpsycn.jixiao.R;
import com.jpsycn.jixiao.utils.SysUtils;

public class AddActivity extends Activity {

	public ProgressDialog progressDialog;
	private String content;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		preferences=getSharedPreferences("userinfo",Context.MODE_PRIVATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit, menu);
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
		TextView contentView = (TextView) findViewById(R.id.bbs_content);
		content = contentView.getText().toString();
		
		XXAsyncTask task=new XXAsyncTask();
		task.execute();
	}
	
	private class XXAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {

				Map<String, String> cookies = SysUtils.getCookies(preferences.getString("username", ""), preferences.getString("password", ""));
				String id = getIntent().getStringExtra("id");
				SysUtils.addReply(cookies, "999710", id, content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(AddActivity.this);
			progressDialog.setMessage("正在提交,请稍候...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			AddActivity.this.finish();
		}
	}
}
