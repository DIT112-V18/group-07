package com.example.arduinoremote2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    Switch sportmode;
    Switch autobrake;
    static String brakeMode;
    static String carMode = "ON";
    static int accFollow = 30;
    static int cruiseSpeed = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        NumberPicker numberPicker = findViewById(R.id.np);
        numberPicker.setMinValue(20);
        numberPicker.setMaxValue(70);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                accFollow = i2;
            }
        });

        sportmode = findViewById(R.id.switch2);
        autobrake = findViewById(R.id.switch3);

        if (carMode == "ON") {
            sportmode.setChecked(true);
        } else {
            sportmode.setChecked(false);
        }

        if (brakeMode == "ON") {
            autobrake.setChecked(true);
        } else {
            autobrake.setChecked(false);
        }


        sportmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    carMode = "ON";
                    sportmode.setChecked(true);
                } else {
                    carMode = "OF";
                    sportmode.setChecked(false);
                }
            }
        });

        autobrake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    brakeMode = "ON";
                    autobrake.setChecked(true);
                } else {
                    brakeMode = "OF";
                    autobrake.setChecked(false);
                }
            }
        });

    }
}
