package com.example.fypsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.anastr.speedviewlib.PointerSpeedometer;

//detect magnetic radiation that cameras emit
public class MagneticRadiationDetector extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    TextView mtext, magnetDetectTextView;
    int magnitude = 0,x,y,z;
    int max = 0;
    MediaPlayer beep;
    PointerSpeedometer pointerSpeedometer;

    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magnetic_radiation_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pointerSpeedometer= (PointerSpeedometer) findViewById(R.id.pointerSpeedometer);
        pointerSpeedometer.speedTo(max);

        //register and activate magnetic sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //mtext = findViewById(R.id.m_textView);
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
        magnetDetectTextView = findViewById(R.id.magnet_detect_textView);
        beep = MediaPlayer.create(MagneticRadiationDetector.this, R.raw.beep);

        pointerSpeedometer.setUnit("µF");

        //create alert dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipMessage", "NOT checked");
        dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        alertDialogBuilder.setView(eulaLayout);
        // set title
        alertDialogBuilder.setTitle(R.string.Important);
        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.magnetic_radiation_message)
                .setCancelable(false)
                .setPositiveButton(R.string.OK,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String checkBoxResult = "Not checked";

                        if (dontShowAgain.isChecked()) {
                            checkBoxResult = "Checked";
                        }

                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();

                        editor.putString("skipMessage", checkBoxResult);
                        editor.apply();
                        editor.commit();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        if (!skipMessage.equals("Checked")) {
            alertDialog.show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = (int) event.values[0];
        y = (int) event.values[1];
        z = (int) event.values[2];

        magnitude = (int) Math.sqrt(x*x+y*y+z*z); //calculate electromagnetic field
        pointerSpeedometer.speedTo(magnitude);
        if(magnitude>=90 && magnitude <=140) {
            magnetDetectTextView.setText("Hidden camera detected!");
            beep.start();
        }
        else magnetDetectTextView.setText("You are safe! No hidden camera is detected.");
        //mtext.setText(String.valueOf(magnitude)+"µF");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onBackPressed() {
        beep.stop();
        super.onBackPressed();
    }
}
