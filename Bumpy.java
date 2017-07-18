package com.psc.bumpy;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import java.util.Date;



public class Bumpy extends Activity implements  View.OnTouchListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Button mSave;
    private Button mSpeed;
    public DataBuffer data;
    GPS gps;
    private PowerManager.WakeLock wl;


    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            data.add(new Date().getTime(), sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            mSpeed.setText("Vitesse : " + (int) 3.6*gps.getSpeed());
        }
    };


    @Override
    public final void onCreate(Bundle savedInstanceState) {
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                | PowerManager.ON_AFTER_RELEASE, this.getClass().getName());
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        gps=new GPS(locationManager);
        data = new DataBuffer(gps);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        WebManager.context=this;
        mSave = (Button) findViewById(R.id.save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebManager.sendDatas(gps.getLongitude(),gps.getLatitude());
            }
        });
        mSpeed = (Button) findViewById(R.id.speed);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener, mAccelerometer);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wl != null) {
            wl.acquire();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wl != null) {
            wl.release();
        }
    }


}

/*
Réunion Ida 22/05/17

Bouton on/off
Bouton Envoyer les données indiquant l'état actuel du processus d'envoi

 */
