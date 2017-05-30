package com.example.ativ.tracingshaking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public String name, phNum;
    public Button goInfo;
    public Button popInfo;
    public Button initInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("Shake", Activity.MODE_PRIVATE);

        goInfo = (Button)findViewById(R.id.inform);
        popInfo = (Button)findViewById(R.id.popInform);
        initInfo = (Button)findViewById(R.id.initInform);

        name = pref.getString("name", null);
        phNum = pref.getString("phoneNum", null);

        if (name == null || phNum == null) {
            Intent i = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(i);
        }

        goInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });
        popInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(i);
            }
        });
        initInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Shake", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                /*editor.remove("name");
                editor.remove("phoneNum");
                editor.clear();*/
                editor.putString("name", null);
                editor.putString("phoneNum", null);
                editor.commit();

                String name, phNum;
                name = pref.getString("name", null);
                phNum = pref.getString("phoneNum", null);

                if (name == null || phNum == null) {
                    Intent i = new Intent(MainActivity.this, InfoActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
