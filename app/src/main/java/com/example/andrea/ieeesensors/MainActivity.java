package com.example.andrea.ieeesensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import java.util.List;
import java.util.Vector;

// implements SensorEventListener is to use the OnChangeEvents linked to the sensors
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private List<Sensor> listAllSensors;// in this list all the sensors
    private Vector<String> allTheSensorsToPrint = new Vector<String>();// in this list all the string to print (each string related to a sensor)
    private ViewGroup linearLayout;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (ViewGroup) findViewById(R.id.mainlayout);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        listAllSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL); // in this list all the sensors

        //maybe the following 4 rows are useless..
        for( int i = 0; i < 50 ; i++ ) //in this moment i wanna 50 rows to write something (I don't know what)
        {
            allTheSensorsToPrint.add("-");// I don't like empty strings
        }

        for (Sensor e : listAllSensors)// for all the existing sensors, taken as "e"
        {
            //register this sensor, so if it changes onChangeSensor is called
            mSensorManager.registerListener(this, e, mSensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {//when some (at least 1) sensor changes
        synchronized (this) {
            try// sometimes it crashes..
            {
                String v = "";
                for(int i = 0; i < event.values.length; i++)// for all the values of the sensor (sometimes sensors have 3 value, sometimes only 1)
                {
                    v+=event.values[i] + " ; ";//print them separated by semicolon
                }
                //create a string to show the name and the value of the sensor
                String toPrint = "I'm : " + event.sensor.getName() + " ==> " + String.valueOf(v) + "  ; ";

                //put this string in the vector, each sensor in a different row (based on the sensor's type)
                allTheSensorsToPrint.set(event.sensor.getType(),  toPrint );

                if(((LinearLayout) linearLayout).getChildCount() > 0)//clean the screen.. toggle all existing buttons
                    ((LinearLayout) linearLayout).removeAllViews();

                for( int i = 0; i < allTheSensorsToPrint.size() ; i++ )// every time something changes reprint all the sensors' line
                {
                    if( !allTheSensorsToPrint.get( i ).equalsIgnoreCase( "-" ) )// if the result makes sense... empty are not interesting
                    {// create a new button, having written the row related to this sensor
                        Button bt = new Button(this);
                        bt.setText( allTheSensorsToPrint.get( i ) + " \n " );
                        bt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayout.addView(bt);
                    }
                }
            }
            catch (Exception e)
            {
                //let's curse
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        for (Sensor e : listAllSensors)
        {
            mSensorManager.registerListener(this, e, mSensorManager.SENSOR_DELAY_NORMAL);
        }
        //mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
