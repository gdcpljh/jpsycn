package com.jpsycn.jixiao;

import java.io.IOException;
import java.util.Map;

import com.jpsycn.jixiao.R;
import com.jpsycn.jixiao.util.SysUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends Activity {

	protected static final String TAG = "LoginActivity";
	private EditText usernameView;
	private EditText passwordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		usernameView = (EditText) findViewById(R.id.username);
		passwordView = (EditText) findViewById(R.id.password);
		Button login = (Button) findViewById(R.id.login);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = usernameView.getText().toString();
				String password = passwordView.getText().toString();

				boolean b = true;

				if (TextUtils.isEmpty(password)) {
					passwordView.requestFocus();
					passwordView.setError("请输入密码");
					b = false;
				}

				if (TextUtils.isEmpty(username)) {
					usernameView.requestFocus();
					usernameView.setError("请输入账号");
					b = false;
				}

				if (b) {
					MyAsyncTask task = new MyAsyncTask();
					task.execute(username, password);
				}

			}
		});

	}

	private class MyAsyncTask extends
			AsyncTask<String, Void, Map<String, String>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(LoginActivity.this, "正在登录",
					"正在登录,请稍候.....");

		}

		@Override
		protected Map<String, String> doInBackground(String... params) {
			try {
				Map<String, String> map = SysUtils.login(params[0], params[1]);
				Log.d(TAG, map.toString());
				return map;
			} catch (IOException e) {
				Log.e(TAG, "登录异常：", e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			super.onPostExecute(result);

			dialog.dismiss();

			if (result != null && result.size() > 2) {

				SharedPreferences preferences = getSharedPreferences(
						"user_info", Context.MODE_PRIVATE);
				preferences
						.edit()
						.putString("username",
								usernameView.getText().toString())
						.putString("password",
								passwordView.getText().toString()).commit();// api
				// 9
				// apply();

				finish();
			} else {
				Toast.makeText(LoginActivity.this, "用户名或密码不正确",
						Toast.LENGTH_SHORT).show();
			}

		}

	}

}
