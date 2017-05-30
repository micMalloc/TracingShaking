package com.example.ativ.tracingshaking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    public Button saveButton;
    public EditText personName;
    public EditText phoneNumber;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        personName = (EditText)findViewById(R.id.inputName);
        phoneNumber = (EditText)findViewById(R.id.inputPN);
        saveButton = (Button)findViewById(R.id.saveBtn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name, phNum;

                SharedPreferences pref = getSharedPreferences("Shake", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("name", personName.getText().toString());
                editor.putString("phoneNum", phoneNumber.getText().toString());
                editor.commit();
                personName.setText(""); phoneNumber.setText("");

                name = pref.getString("name", null);
                phNum = pref.getString("phoneNum", null);
                Toast.makeText(InfoActivity.this, name + " " + phNum, Toast.LENGTH_SHORT).show();

                InfoActivity.this.finish();
            }
        });
    }
}
