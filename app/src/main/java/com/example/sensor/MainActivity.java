package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private Sensor mSensorProximity;
    private Sensor mSensorPressure;
    private Sensor mSensorAmbient;
    private Sensor mSensorMagnetic;


    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorPressure;
    private TextView mTextSensorAmbient;
    private TextView mTextSensorMagnetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();
        for (Sensor currentSensor: sensorList){
            sensorText.append(currentSensor.getName())
                    .append(System.getProperty("line.separator"));
        }
        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorPressure = findViewById(R.id.label_pressure);
        mTextSensorMagnetic = findViewById(R.id.label_magnetic);
        mTextSensorAmbient = findViewById(R.id.label_ambient);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorAmbient = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        String sensor_error = "No Sensor";

        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null){
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorPressure == null){
            mTextSensorPressure.setText(sensor_error);
        }
        if (mSensorAmbient == null){
            mTextSensorAmbient.setText(sensor_error);
        }
        if (mSensorMagnetic == null){
            mTextSensorMagnetic.setText(sensor_error);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorProximity != null){
            mSensorManager.registerListener(this,mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null){
            mSensorManager.registerListener(this,mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorPressure != null){
            mSensorManager.registerListener(this,mSensorPressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetic != null){
            mSensorManager.registerListener(this,mSensorMagnetic,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorAmbient != null){
            mSensorManager.registerListener(this,mSensorAmbient,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];

        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(
                        String.format("Light sensor : %1$.2f", currentValue)

                );
                changeBackgroundColor(currentValue);
                break;

            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(
                        String.format("Proximity sensor : %1$.2f", currentValue)
                );
                changeBackgroundColor(currentValue);
                break;

            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(
                        String.format("Pressure sensor : %1$.2f", currentValue)
                );
                break;
        }
    }

    private void changeBackgroundColor(float currentValue){
        LinearLayout layout = findViewById(R.id.layout_linear);
        if(currentValue < 5.00 ) layout.setBackgroundColor(Color.BLUE);
        else if(currentValue == 5.00 ) layout.setBackgroundColor(Color.RED);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}