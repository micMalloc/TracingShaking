package com.example.ativ.tracingshaking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by ATIV on 2017-06-09.
 */
public class FragmentActivity1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        new IntentIntegrator(getActivity()).setOrientationLocked(false).
                setBeepEnabled(false).
                setCaptureActivity(Test.class).
                initiateScan();
        //TODO 반드시 스캐너 조정 해보자!
        return inflater.inflate(R.layout.activity_fragment1, container, true);
    }
}
