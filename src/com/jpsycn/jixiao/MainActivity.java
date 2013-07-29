package com.jpsycn.jixiao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.jpsycn.jixiao.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		boolean login = checkLogin();

		if (!login) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setUpVeiw();

	}

	private void setUpVeiw() {
		((Button) findViewById(R.id.btn_1))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent=new Intent(MainActivity.this,NoteListActivity.class);
						startActivity(intent);
					}
				});

		((Button) findViewById(R.id.btn_2))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent=new Intent(MainActivity.this,NoteWriteActivity.class);
						startActivity(intent);

					}
				});

		((Button) findViewById(R.id.btn_3))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent=new Intent(MainActivity.this,BBSListActivity.class);
						startActivity(intent);
					}
				});

		((Button) findViewById(R.id.btn_4))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent=new Intent(MainActivity.this,NoticeActivity.class);
						startActivity(intent);
					}
				});

		((Button) findViewById(R.id.btn_5))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent=new Intent(MainActivity.this,LeaderNoteActivity.class);
						startActivity(intent);

					}
				});

	}

	private boolean checkLogin() {
		SharedPreferences preferences = getSharedPreferences("user_info",
				MODE_PRIVATE);
		String username = preferences.getString("username", "");
		String password = preferences.getString("password", "");

		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
