package com.jpsycn.jixiao;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class Test extends Activity {

	private static final String TAG = "Test";
	private CalendarPickerView calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sample_calendar_picker);

		Calendar a = Calendar.getInstance();
		a.add(Calendar.YEAR, -1);

		Calendar b = Calendar.getInstance();
		b.add(Calendar.DAY_OF_MONTH, 1);

		 calendar = (CalendarPickerView) findViewById(R.id.calendar_view);

		calendar.init(a.getTime(), b.getTime()).inMode(SelectionMode.SINGLE)
				.withSelectedDate(new Date());
		
		//Log.d(TAG, "Selected time in millis: " + calendar.getSelectedDate().getTime());
		
       // String toast = "Selected: " + calendar.getSelectedDate().getTime();
        
      //  Toast.makeText(Test.this, toast, LENGTH_SHORT).show();
		calendar.setOnDateSelectedListener(new OnDateSelectedListener() {
			
			@Override
			public void onDateSelected(Date date) {
				// TODO Auto-generated method stub
				 String toast = "Selected: " + calendar.getSelectedDate().getTime();
				Toast.makeText(Test.this, toast, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
