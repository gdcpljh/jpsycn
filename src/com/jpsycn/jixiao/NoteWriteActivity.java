package com.jpsycn.jixiao;

import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jpsycn.jixiao.util.SysUtils;

public class NoteWriteActivity extends Activity {

	public static final String TAG = "NoteWriteActivity";
	private Button saveNote;
	private EditText content;
	private EditText date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_write);

		saveNote = (Button) findViewById(R.id.saveNote);
		content = (EditText) findViewById(R.id.content);
		date = (EditText) findViewById(R.id.datediaplay);
		date.setText("2013-08-08");

		saveNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyAsyncTask task = new MyAsyncTask();
				task.execute(date.getText().toString(), content.getText()
						.toString());
			}
		});

	}

	private class MyAsyncTask extends AsyncTask<String, Void, Boolean> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(NoteWriteActivity.this, "正在登录",
					"正在提交,请稍候.....");

		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
				String username = preferences.getString("username", "");
				String password = preferences.getString("password","");
				
				Map<String, String> cookies = SysUtils
						.login(username, password);
				SysUtils.addLog(cookies, params[0], params[1]);
				return true;
			} catch (Exception e) {
				Log.e(TAG, "登录异常：", e);
				return false;
			}

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if(result){
				finish();
			}else{
				Toast.makeText(NoteWriteActivity.this, "提交日志失败，请重试！", Toast.LENGTH_SHORT).show();
				
			}

		}

	}

}
