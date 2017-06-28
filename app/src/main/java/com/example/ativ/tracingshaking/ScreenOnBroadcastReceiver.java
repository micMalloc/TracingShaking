package com.example.ativ.tracingshaking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ATIV on 2017-06-26.
 */
public class ScreenOnBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ShakeService.class);
        context.startService(i);
    }
}
