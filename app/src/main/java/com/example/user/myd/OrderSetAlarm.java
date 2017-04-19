package com.example.user.myd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class OrderSetAlarm extends AppCompatActivity {

    DatePicker dPicker;
    TimePicker tPicker;
    Button buttonSetAlarm, returnBack;
    TextView info;

    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_set_alarm);

        info = (TextView)findViewById(R.id.info);
        dPicker = (DatePicker)findViewById(R.id.pickerdate);
        tPicker = (TimePicker)findViewById(R.id.pickertime);

        Calendar now = Calendar.getInstance();

        dPicker.init(
                // Use the current time as the default values for the picker
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);


        tPicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        tPicker.setCurrentMinute(now.get(Calendar.MINUTE));

        buttonSetAlarm = (Button)findViewById(R.id.setalarm);
        returnBack = (Button)findViewById(R.id.goBack);
        buttonSetAlarm.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Calendar current = Calendar.getInstance();

                // store the values selected into a Calendar instance
                Calendar cal = Calendar.getInstance();
                cal.set(dPicker.getYear(),
                        dPicker.getMonth(),
                        dPicker.getDayOfMonth(),
                        tPicker.getCurrentHour(),
                        tPicker.getCurrentMinute(),
                        00);

                if(cal.compareTo(current) <= 0){
                    //if the date or time already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                }else{
                    setAlarm(cal);
                }

            }});

        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveOrdersScreen();
            }
        });

    }

    private void setAlarm(Calendar targetCal){

        //info.setText("\n" + "קבעת תזכורת ב: " + "\n" + targetCal.getTime());
        Toast.makeText(getBaseContext(),"\n" + "קבעת תזכורת ב: " + "\n" + targetCal.getTime(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

    private void moveOrdersScreen() {
        Intent i = new Intent(OrderSetAlarm.this, Orders.class);
        startActivity(i);
    }
}

