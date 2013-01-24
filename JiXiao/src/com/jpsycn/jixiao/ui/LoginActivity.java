package com.jpsycn.jixiao.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jpsycn.jixiao.R;

public class LoginActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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
		TextView usernameView = (TextView) findViewById(R.id.username);
		String username = usernameView.getText().toString();

		TextView passwordView = (TextView) findViewById(R.id.password);
		String password = passwordView.getText().toString();

		SharedPreferences preferences = getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		preferences.edit().putString("username", username)
				.putString("password", password).commit();
		finish();
	}
}
