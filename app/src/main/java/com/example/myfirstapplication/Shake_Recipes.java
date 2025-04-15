package com.example.myfirstapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
public class Shake_Recipes extends AppCompatActivity implements SensorEventListener
{

    private SensorManager mSensorManager;
    Sensor mShake;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shake_recipes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mShake = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            Log.d("SensroAcc", "The sensor is there!!!!!");
        }
        else {
            Log.d("SensroAcc", "Damn nooooooooo Daniel");
        }
    }

    @Override
    public final void onAccuracyChanged (Sensor mShake, int accuracy)
    {
        
    }

    @Override
    public final void onSensorChanged(SensorEvent event)
    {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float acceleration = (float) Math.sqrt(x * x + y * y + z * z);
        if (acceleration > 20)
        {
            Log.d("Shaken", "You shook it!");
        }
    }

    @Override
    protected void onResume()
    {

        super.onResume();
        mSensorManager.registerListener(this, mShake, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}