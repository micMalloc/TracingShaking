package com.example.ativ.tracingshaking;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ATIV on 2017-06-01.
 */
public class DataManager {
    public String name, phNum;

    public void setData(Context context) {
        SharedPreferences pref = context.getSharedPreferences("Shake", Activity.MODE_PRIVATE);
        this.name = pref.getString("name",null);
        this.phNum = pref.getString("phoneNum",null);
    }

    public String getName() {
        return name;
    }

    public String getPhNum() {
        return phNum;
    }
}
