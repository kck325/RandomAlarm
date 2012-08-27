package com.kuchick.random.alarm.activity;

import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.kuchick.random.alarm.R;
import com.kuchick.random.alarm.dialog.SelectWeekdayFragment;

public class SetAlarmActivity extends Activity {

    protected static final String EXTRA_MESSAGE = "com.kuchick.mainactivity.extramessage";
    public static final String STORAGE_FILE_NAME = "weekDayPrefs";
    public static final String ALARM_DAYS = "alarmDays";
    private static final int RANDOM_APP_ID = 111111;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        setContentView(R.layout.activity_main);
        NumberPicker hourPicker = (NumberPicker) findViewById(R.id.hourPicker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        
        NumberPicker minutePicker = (NumberPicker) findViewById(R.id.minutePicker);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiverActivity.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, RANDOM_APP_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
        
    }
	
	public void showSelectWeekdays(View view){
		SharedPreferences appSharedSettings = getSharedPreferences(STORAGE_FILE_NAME, 0);
		SelectWeekdayFragment selectWeekdayFragment = SelectWeekdayFragment
								.newInstance(appSharedSettings.getString(ALARM_DAYS, ""));
		selectWeekdayFragment.show(getFragmentManager(), "dialog");
	}
    
    public void setAlarm(View view) {
    	
    	EditText variance = (EditText) findViewById(R.id.variance);
    	int varianceVal = 0;
    	try{
    		varianceVal = Integer.parseInt(variance.getText().toString());
    	}catch(NumberFormatException nfe){
    		Context context = getApplicationContext();
	    	CharSequence text = "Please enter a valid value for variance";
	    	int duration = Toast.LENGTH_SHORT;
	
	    	Toast toast = Toast.makeText(context, text, duration);
	    	toast.show();
	    	return;
    	}
    	
    	int randomMinute;
    	Random rand = new Random();    	    	
    	NumberPicker hourPicker = (NumberPicker) findViewById(R.id.hourPicker);
    	NumberPicker minutePicker = (NumberPicker) findViewById(R.id.minutePicker);
    	if(rand.nextInt(1) == 1){
    		randomMinute = minutePicker.getValue() + rand.nextInt(varianceVal + 1);
    	}else{
    		randomMinute = minutePicker.getValue() - rand.nextInt(varianceVal + 1);
    	}
    	
    	SharedPreferences appSharedSettings = getSharedPreferences(STORAGE_FILE_NAME, 0);
    	SharedPreferences.Editor editor = appSharedSettings.edit();
    	editor.putInt("variance", varianceVal);
    	editor.putInt("hour", hourPicker.getValue());
    	editor.putInt("minute", minutePicker.getValue());
    	editor.commit();
    	
    	Intent intent = new Intent();
    	intent.setAction(AlarmClock.ACTION_SET_ALARM);
    	intent.putExtra(AlarmClock.EXTRA_HOUR, hourPicker.getValue());
    	intent.putExtra(AlarmClock.EXTRA_MINUTES, randomMinute);
    	intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
    	startActivity(intent);
    }
}
