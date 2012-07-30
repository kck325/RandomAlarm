package com.kuchick.random.alarm;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class SetAlarmActivity extends Activity {

    protected static final String EXTRA_MESSAGE = "com.kuchick.mainactivity.extramessage";

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
    	
    	Intent intent = new Intent();
    	intent.setAction(AlarmClock.ACTION_SET_ALARM);
    	intent.putExtra(AlarmClock.EXTRA_HOUR, hourPicker.getValue());
    	intent.putExtra(AlarmClock.EXTRA_MINUTES, randomMinute);
    	intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
    	startActivity(intent);
    }
}
