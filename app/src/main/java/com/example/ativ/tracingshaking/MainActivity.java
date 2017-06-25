package com.example.ativ.tracingshaking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public DataManager dm = null;
    public String name, phNum;
    public Button goInfo;
    public Button popInfo;
    public Button initInfo;
    public Button scanBtn;
    public ImageView imageView;
    public ToggleButton serviceBtn;
    public TextView serviceTag;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        dm = new DataManager(this);

        goInfo = (Button)findViewById(R.id.inform);
        popInfo = (Button)findViewById(R.id.popInform);
        initInfo = (Button)findViewById(R.id.initInform);
        scanBtn = (Button)findViewById(R.id.scanner);
        imageView = (ImageView)findViewById(R.id.qrView);
        serviceBtn = (ToggleButton)findViewById(R.id.serviceBtn);
        serviceTag = (TextView)findViewById(R.id.textView);
        serviceTag.setText("Service Tag");

        name = dm.getName(); phNum = dm.getPhNum();

        if (name == null || phNum == null) {
            Intent i = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(i);
        }

        goInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this
                        , "HI", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });
        popInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Start Service", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, ShakeService.class));
                /*Intent i = new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(i);*/
            }
        });
        initInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Shake", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.clear();
                editor.commit();

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
                //new IntentIntegrator(MainActivity.this).initiateScan();
                /*new IntentIntegrator(MainActivity.this).setOrientationLocked(false).setBeepEnabled(false)
                        .initiateScan();*/
                new IntentIntegrator(MainActivity.this).setOrientationLocked(false).
                        setBeepEnabled(false).
                        setCaptureActivity(Test.class).
                        initiateScan();
            }
        });
        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceBtn.isChecked()) {
                    /* Start Service */
                    Toast.makeText(MainActivity.this, "Start Service", Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "Start Service");
                    startService(new Intent(MainActivity.this, ShakeService.class));
                } else {
                    /* Stop Service */
                    Toast.makeText(MainActivity.this, "Stop Service", Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "Stop Service");
                    stopService(new Intent(MainActivity.this, ShakeService.class));
                }
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

    @Override
    protected void onStart() {
        super.onStart();
        if (accelerometerSensor != null) {
            sensorManager.
                    registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
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
                    startActivity(i);
                }

                lastX = sensorEvent.values[SensorManager.DATA_X];
                lastY = sensorEvent.values[SensorManager.DATA_Y];
                lastZ = sensorEvent.values[SensorManager.DATA_Z];
            }
        }
    }
}
