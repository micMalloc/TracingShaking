package com.example.ativ.tracingshaking;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ATIV on 2017-06-26.
 */
public class ShakeService extends Service implements SensorEventListener {

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager = null;
    private Sensor accelerometerSensor = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service", "Start Service");
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return super.onStartCommand(intent, flags, startId);
    }*/

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (accelerometerSensor != null) {
            sensorManager.
                    registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Service", "Start Service");
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);

            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = sensorEvent.values[SensorManager.DATA_X];
                y = sensorEvent.values[SensorManager.DATA_Y];
                z = sensorEvent.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Intent i = new Intent(this, CustomerActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                }

                lastX = sensorEvent.values[SensorManager.DATA_X];
                lastY = sensorEvent.values[SensorManager.DATA_Y];
                lastZ = sensorEvent.values[SensorManager.DATA_Z];
            }
        }
    }
}
