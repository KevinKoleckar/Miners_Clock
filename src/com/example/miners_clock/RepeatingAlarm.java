package com.example.miners_clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
 
public class RepeatingAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MyTimer myTimer = new MyTimer(context);
        myTimer.runAndUpdateTheWidget();
    }
}
