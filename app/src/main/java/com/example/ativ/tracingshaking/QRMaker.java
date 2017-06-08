package com.example.ativ.tracingshaking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * Created by ATIV on 2017-06-01.
 */
public class QRMaker extends  Activity{

    private Bitmap bitmap = null;

    @Override
    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_customer);
        popQR();
    }

    public String getData () {
        String data;
        String name, phNum;
        SharedPreferences pref = getSharedPreferences("Shake", Activity.MODE_PRIVATE);

        name = pref.getString("name", null); phNum = pref.getString("phone", null);
        if (name == null || phNum == null)
            data = null;
        else
            data = name + "#" + phNum;

        return data;
    }

    public void popQR () {
        String data;
        MultiFormatWriter gen = new MultiFormatWriter();

        data = getData();

        try {
            final int WIDTH = 400;
            final int HEIGHT = 400;

            data = new String(data.getBytes("UTF-8"), "ISO-8859-1");
            BitMatrix byteMap = gen.encode(data, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
            bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);

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
