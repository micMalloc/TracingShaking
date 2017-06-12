package com.example.ativ.tracingshaking;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class FragmentActivity2  extends Fragment {

    private ImageView imageView = null;
    private DataManager dm = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment2, container, false);

        dm = new DataManager(getActivity());

        imageView = (ImageView)v.findViewById(R.id.qrView);
        imageView.setImageBitmap(new QRMaker().getBitmap(dm.getData()));
        imageView.invalidate();

        return v;
    }
}