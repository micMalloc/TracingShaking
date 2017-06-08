package com.example.ativ.tracingshaking;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

/**
 * Created by ATIV on 2017-06-01.
 */
public class DataManager {
    public String name, phNum;

    public DataManager (Context context) {
        super();
        setData(context);
    }

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

    public String getData () {
        return ("sh#" + name + "#" + phNum);
    }
}
