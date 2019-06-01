package com.example.arduinoremote2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;

import static com.example.arduinoremote2.HomeActivity.btSocket;

public class TiltFragment extends Fragment implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor rotation;
    private ImageButton front;
    private ImageButton back;
    static String move = "0";
    static String angle = "L00";
    private TextView angleView;
    ToggleButton adaptive;
    ToggleButton autopilot;
    ToggleButton lights;
    ToggleButton cruise;
    ToggleButton obstacleavd;
    boolean flagButton = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
        rotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mSensorManager.registerListener(this, rotation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tilt, container, false);
        front = rootView.findViewById(R.id.imageButton9);
        back = rootView.findViewById(R.id.imageButton8);
        angleView = rootView.findViewById(R.id.angleView);
        angleView.setText(angle);
        adaptive = rootView.findViewById(R.id.toggleButton7);
        autopilot = rootView.findViewById(R.id.toggleButton11);
        lights = rootView.findViewById(R.id.toggleButton8);
        cruise = rootView.findViewById(R.id.toggleButton9);
        obstacleavd = rootView.findViewById(R.id.toggleButton10);
        front.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        try {
                            uncheckOptions();
                            ButtonFragment.finalOP = "FF";
                            move = "F";
                            btSocket.getOutputStream().write((move + angle).getBytes());
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            ButtonFragment.finalOP = "00";
                            move = "0";
                            btSocket.getOutputStream().write((move + angle).getBytes());
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            ButtonFragment.finalOP = "00";
                            move = "0";
                            btSocket.getOutputStream().write((move + angle).getBytes());
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;
                }
                return false;
            }
        });
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        try {
                            uncheckOptions();
                            ButtonFragment.finalOP = "BB";
                            move = "B";
                            btSocket.getOutputStream().write((move + angle).getBytes());
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            ButtonFragment.finalOP = "00";
                            move = "0";
                            btSocket.getOutputStream().write((move + angle).getBytes());
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            ButtonFragment.finalOP = "00";
                            move = "0";
                            btSocket.getOutputStream().write((move + angle).getBytes());
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;
                }
                return false;
            }
        });

        adaptive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flagButton = true;

                    try {
                        ButtonFragment.finalOP = "AC" + SettingsActivity.accFollow;
                        btSocket.getOutputStream().write(ButtonFragment.finalOP.getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        ButtonFragment.finalOP = "00";
                        btSocket.getOutputStream().write((ButtonFragment.finalOP + (SettingsActivity.carMode)).getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }
        });

        lights.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flagButton = true;

                    try {
                        ButtonFragment.finalOP = "LI";
                        btSocket.getOutputStream().write((ButtonFragment.finalOP + "ON").getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        ButtonFragment.finalOP = "LI";
                        btSocket.getOutputStream().write((ButtonFragment.finalOP + "OF").getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }
        });

        cruise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flagButton = true;

                    try {
                        showDialogue(getActivity());
                        btSocket.getOutputStream().write(("SC" + SettingsActivity.cruiseSpeed).getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        ButtonFragment.finalOP = "00";
                        btSocket.getOutputStream().write((ButtonFragment.finalOP + (SettingsActivity.carMode)).getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }
        });

        autopilot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        flagButton = true;
                        ButtonFragment.finalOP = "ADON";
                        btSocket.getOutputStream().write(ButtonFragment.finalOP.getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        ButtonFragment.finalOP = "00";
                        btSocket.getOutputStream().write((ButtonFragment.finalOP + (SettingsActivity.carMode)).getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }
        });

        obstacleavd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        flagButton = true;
                        ButtonFragment.finalOP = "OMON";
                        btSocket.getOutputStream().write(ButtonFragment.finalOP.getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        ButtonFragment.finalOP = "00";
                        btSocket.getOutputStream().write((ButtonFragment.finalOP + (SettingsActivity.carMode)).getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }
        });
        return rootView;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] rotationMatrix = new float[16];
        SensorManager.getRotationMatrixFromVector(
                rotationMatrix, sensorEvent.values);
        float[] remappedRotationMatrix = new float[16];
        SensorManager.remapCoordinateSystem(rotationMatrix,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                remappedRotationMatrix);
        float[] orientations = new float[3];
        SensorManager.getOrientation(remappedRotationMatrix, orientations);
        for (int i = 0; i < 3; i++) {
            orientations[i] = (float) (Math.toDegrees(orientations[i]));
        }

        if ((orientations[2] > -80) && (orientations[2] <= 0)) {
            angle = "R" + (Math.round(Math.abs(orientations[2] + 90)));
        } else if ((orientations[2] < -100) && orientations[2] >= -180) {
            angle = "L" + (Math.round(Math.abs(orientations[2] + 90)));
        } else {
            angle = "L00";
        }
        angleView.setText(angle);
        try {
            if (ButtonFragment.finalOP.equals("00") || ButtonFragment.finalOP.equals("FF") || ButtonFragment.finalOP.equals("BB")) {
                btSocket.getOutputStream().write((move + angle).getBytes());
            }
        } catch (IOException e) {
            msg("Error");
        }

    }

    public void showDialogue(Context context) {
        final AlertDialog.Builder d = new AlertDialog.Builder(context);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.numberpicker_dialogue, null);
        d.setTitle("Static Cruise Control");
        d.setMessage("Set Speed");
        d.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(20);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                SettingsActivity.cruiseSpeed = i1;
                try {
                    ButtonFragment.finalOP = "SC";
                    btSocket.getOutputStream().write((ButtonFragment.finalOP + SettingsActivity.cruiseSpeed).getBytes());
                } catch (IOException e) {

                    msg("Error");
                }
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }

    public void uncheckOptions() {
        adaptive.setChecked(false);
        cruise.setChecked(false);
        obstacleavd.setChecked(false);
        autopilot.setChecked(false);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void msg(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

}
