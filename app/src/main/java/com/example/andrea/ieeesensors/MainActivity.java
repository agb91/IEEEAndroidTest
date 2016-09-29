package com.example.andrea.ieeesensors;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Vector;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mLight;
    private TextView monitor;
    private List<Sensor> listAllSensors;
    private Vector<String> allTheSensorsToPrint = new Vector<String>();

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monitor = (TextView) findViewById(R.id.monitor);
        monitor.setText("");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        listAllSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for( int i = 0; i < 100 ; i++ )
        {
            allTheSensorsToPrint.add("-");
        }

        for (Sensor e : listAllSensors)
        {
            mSensorManager.registerListener(this, e, mSensorManager.SENSOR_DELAY_NORMAL);
        }

        //mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //mCompass = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        synchronized (this) {
            try
            {
                String v = "";
                for(int i = 0; i < event.values.length; i++)
                {
                    v+=event.values[i] + " ; ";
                }
                // Do something with this sensor value.
                //tv1 = (TextView) findViewById(R.id.text1);
                String toPrint = "I'm : " + event.sensor.getName() + " ==> " + String.valueOf(v) + "  ; ";

                allTheSensorsToPrint.set(event.sensor.getType(),  toPrint );
                monitor.setText("");
                for( int i = 0; i < allTheSensorsToPrint.size() ; i++ )
                {
                    monitor.append( allTheSensorsToPrint.get( i ) + " \n " );
                }
            }
            catch (Exception e)
            {
                monitor.setText("");
                monitor.append( "black death");
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
   /* public static String BARCODE="",MESSAGE="";
    SensorManager smm;
    List<Sensor> list;
    //barcode
    //private int requestCodeGetQR = 1;
    //private String barcode="",message="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *
     * @param view
     */
/*    public void getData(View view) {
        smm = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            list = smm.getSensorList(Sensor.TYPE_ALL);
        }
        TextView monitor = (TextView) findViewById(R.id.monitor);
        monitor.setText("");

        for (Sensor e : list){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                monitor.append(" \n name: " + e.getName());
            }

        }
    }

  /*  /**
     *
     * @param view
     */
/*    public void sendEmail(View view){
        Intent intent= new Intent(Intent.ACTION_SENDTO);
        TextView monitor = (TextView) findViewById(R.id.monitor);
        intent.setType("plain/text");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Detect Sensors on my phone");
        intent.putExtra(Intent.EXTRA_TEXT, monitor.getText());
        intent.putExtra(Intent.EXTRA_EMAIL, "test@gmail.com");
        if(intent.resolveActivity(getPackageManager())!=null) {
            startActivity(intent);
            message(getApplicationContext(),"Apertura email");
        }
        else
            message(getApplicationContext(),"Non è possibile inviare alcuna email");
    }
*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCodeGetQR && resultCode == RESULT_OK && data != null) {
            barcode = data.getStringExtra(BARCODE);
            message = data.getStringExtra(MESSAGE);
            barcode();
        }
    }*/
    /*private void message(Context context, String message){
        Log.i(context.getPackageName(), "message: message="+message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
*/