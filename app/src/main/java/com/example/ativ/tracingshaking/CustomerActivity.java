package com.example.ativ.tracingshaking;

import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CustomerActivity extends AppCompatActivity {

    public ImageView imageView = null;
    private DataManager dm = null;
    private String data = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentActivity1 fragmentActivity1 = new FragmentActivity1();
        FragmentActivity2 fragmentActivity2 = new FragmentActivity2();

        fragmentTransaction.add(android.R.id.list_container, fragmentActivity1);
        fragmentTransaction.add(android.R.id.list_container, fragmentActivity2);
    }
}
