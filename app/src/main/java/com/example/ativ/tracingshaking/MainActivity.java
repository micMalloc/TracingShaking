package com.example.ativ.tracingshaking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    public String name, phNum;
    public Button goInfo;
    public Button popInfo;
    public Button initInfo;
    public Button scanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("Shake", Activity.MODE_PRIVATE);

        goInfo = (Button)findViewById(R.id.inform);
        popInfo = (Button)findViewById(R.id.popInform);
        initInfo = (Button)findViewById(R.id.initInform);
        scanBtn = (Button)findViewById(R.id.scanner);

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

                editor.clear();
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
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        ContactManager cm = new ContactManager();

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            } else {
                if (result.getContents().startsWith("sh")) {
                    Intent i = new Intent(MainActivity.this, ContactManager.class);
                    String[] arr = result.getContents().split("#");

                    Toast.makeText(MainActivity.this, "Scanned: " + arr[1] + " " + arr[2], Toast.LENGTH_SHORT).show();

                    i.putExtra("name", arr[1]); i.putExtra("phoneNum", arr[2]);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}
