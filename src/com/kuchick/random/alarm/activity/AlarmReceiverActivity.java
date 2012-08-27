package com.kuchick.random.alarm.activity;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.AlarmClock;

public class AlarmReceiverActivity extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences preferences = context.getSharedPreferences(SetAlarmActivity.STORAGE_FILE_NAME, 0);
		String alarmDays = preferences.getString(SetAlarmActivity.ALARM_DAYS, "");
		int hour = preferences.getInt("hour", -1);
		int minute = preferences.getInt("minute", -1);
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if(alarmDays.length() >= dayOfWeek && hour >= 0 && minute >= 0){
			if((char)dayOfWeek == alarmDays.charAt(dayOfWeek)){
				Intent alarmIntent = new Intent();
				alarmIntent.setAction(AlarmClock.ACTION_SET_ALARM);
				alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, hour);
				alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, minute);
				alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
		    	context.startActivity(alarmIntent);
			}
		}
	}

}
