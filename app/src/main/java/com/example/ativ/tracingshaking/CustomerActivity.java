package com.example.ativ.tracingshaking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        MultiFormatWriter gen = new MultiFormatWriter();
        SharedPreferences pref = getSharedPreferences("Shake", Activity.MODE_PRIVATE);
        String name, phNum;

        name = pref.getString("name", null); phNum = pref.getString("phoneNum", null);
        String data =  "sh#" + name + "#" + phNum;

        try {
            final int WIDTH = 400;
            final int HEIGHT = 400;

            data = new String(data.getBytes("UTF-8"), "ISO-8859-1");
            BitMatrix byteMap = gen.encode(data, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
            Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < WIDTH; ++i)
                for (int j = 0; j < HEIGHT; ++j)
                    bitmap.setPixel(i, j, byteMap.get(i, j) ? Color.BLACK : Color.WHITE);

            ImageView viw = (ImageView) findViewById(R.id.qrView);
            viw.setImageBitmap(bitmap);
            viw.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
