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
import android.widget.Toast;

import com.jpsycn.jixiao.R;
import com.jpsycn.jixiao.utils.SysUtils;

public class LoginActivity extends Activity {

	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		TextView usernameView = (TextView) findViewById(R.id.username);
		username = usernameView.getText().toString();

		TextView passwordView = (TextView) findViewById(R.id.password);
		password = passwordView.getText().toString();

		LoginAsyncTask task = new LoginAsyncTask();

		task.execute(username, password);

	}

	private class LoginAsyncTask extends
			AsyncTask<String, Void, Map<String, String>> {

		private ProgressDialog progressDialog;

		@Override
		protected Map<String, String> doInBackground(String... params) {
			try {

				Map<String, String> cookies = SysUtils.getCookies(params[0],
						params[1]);
				return cookies;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(LoginActivity.this, "用户登陆",
					"正在请求数据,请稍候...", true, false);
			progressDialog.setMessage("正在登陆,请稍候...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			if (null != progressDialog) {
				progressDialog.dismiss();
			}

			if (result != null && result.size() > 1) {
				SharedPreferences preferences = getSharedPreferences(
						"userinfo", Context.MODE_PRIVATE);
				preferences.edit().putString("username", username)
						.putString("password", password).commit();
				finish();
			}else{
				Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
