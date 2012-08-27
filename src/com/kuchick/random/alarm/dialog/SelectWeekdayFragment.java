package com.kuchick.random.alarm.dialog;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.kuchick.random.alarm.R;
import com.kuchick.random.alarm.activity.SetAlarmActivity;

public class SelectWeekdayFragment extends DialogFragment {
	
	private String repeatAlarmString = "";
	
	public static SelectWeekdayFragment newInstance(String repeatAlarmString) {
		SelectWeekdayFragment f = new SelectWeekdayFragment();
		Bundle args = new Bundle();
        args.putString("repeatAlarmString", repeatAlarmString);
        f.setArguments(args);
	    return f;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	getDialog().setTitle("Select weekdays");
        View v = inflater.inflate(R.layout.select_weekdays, container, false);
        repeatAlarmString = getArguments().getString("repeatAlarmString");
        
        //TODO : Try to use lists and indexes here and reduce the code
        //Violating DRY principle here
        final CheckBox sunCheck = (CheckBox)v.findViewById(R.id.chkSun);
        final CheckBox monCheck = (CheckBox)v.findViewById(R.id.chkMon);
        final CheckBox tueCheck = (CheckBox)v.findViewById(R.id.chkTue);
        final CheckBox wedCheck = (CheckBox)v.findViewById(R.id.chkWed);
        final CheckBox thuCheck = (CheckBox)v.findViewById(R.id.chkThu);
        final CheckBox friCheck = (CheckBox)v.findViewById(R.id.chkFri);
        final CheckBox satCheck = (CheckBox)v.findViewById(R.id.chkSat);
        
        if(repeatAlarmString != null 
        		&& !repeatAlarmString.isEmpty()
        		&& repeatAlarmString.length() == 7){
        	sunCheck.setChecked('1' == repeatAlarmString.charAt(0));
        	monCheck.setChecked('1' == repeatAlarmString.charAt(1));
        	tueCheck.setChecked('1' == repeatAlarmString.charAt(2));
        	wedCheck.setChecked('1' == repeatAlarmString.charAt(3));
        	thuCheck.setChecked('1' == repeatAlarmString.charAt(4));
        	friCheck.setChecked('1' == repeatAlarmString.charAt(5));
        	satCheck.setChecked('1' == repeatAlarmString.charAt(6));
        }
        
        // Watch for button clicks.
        Button button = (Button)v.findViewById(R.id.but_submit);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	String alarmDays = "";
            	alarmDays = updateAlarmDays(sunCheck, alarmDays);
            	alarmDays = updateAlarmDays(monCheck, alarmDays);
            	alarmDays = updateAlarmDays(tueCheck, alarmDays);
            	alarmDays = updateAlarmDays(wedCheck, alarmDays);
            	alarmDays = updateAlarmDays(thuCheck, alarmDays);
            	alarmDays = updateAlarmDays(friCheck, alarmDays);
            	alarmDays = updateAlarmDays(satCheck, alarmDays);
            	SharedPreferences prefs = 
            			((SetAlarmActivity)getActivity())
            			.getSharedPreferences(SetAlarmActivity.STORAGE_FILE_NAME, 0);
            	SharedPreferences.Editor editor = prefs.edit();
            	editor.putString(SetAlarmActivity.ALARM_DAYS, alarmDays);
            	editor.commit();
            	getDialog().dismiss();
            }

			private String updateAlarmDays(CheckBox checkBox, String alarmDays) {
				if(checkBox.isChecked()){ 
            		alarmDays = alarmDays + "1"; 
            	} else { 
            		alarmDays = alarmDays + "0"; 
            	}
				return alarmDays;
			}
        });
        
        button = (Button)v.findViewById(R.id.but_cancel);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	getDialog().dismiss();
            }
        });

        return v;
    }
	
}
