package com.example.arduinoremote2;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.anastr.speedviewlib.PointerSpeedometer;

import java.io.IOException;

import static com.example.arduinoremote2.HomeActivity.btSocket;


public class ButtonFragment extends Fragment {

    static String finalOP = "00";
    static PointerSpeedometer speedometer;


    ImageButton leftF;
    ImageButton leftB;
    ImageButton rightF;
    ImageButton rightB;
    ToggleButton adaptive;
    ToggleButton autopilot;
    ToggleButton lights;
    ToggleButton cruise;
    ToggleButton obstacleavd;
    static int speedColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_button, container, false);
        leftF = myFragmentView.findViewById(R.id.imageButton6);
        leftB = myFragmentView.findViewById(R.id.imageButton5);
        rightF = myFragmentView.findViewById(R.id.imageButton2);
        rightB = myFragmentView.findViewById(R.id.imageButton3);

        adaptive = myFragmentView.findViewById(R.id.toggleButton2);
        lights = myFragmentView.findViewById(R.id.toggleButton3);
        cruise = myFragmentView.findViewById(R.id.toggleButton4);
        obstacleavd = myFragmentView.findViewById(R.id.toggleButton5);
        autopilot = myFragmentView.findViewById(R.id.toggleButton6);
        speedometer = myFragmentView.findViewById(R.id.pointerSpeedometer);
        speedColor = speedometer.getSpeedometerColor();


        adaptive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        finalOP = "AC" + SettingsActivity.accFollow;
                        btSocket.getOutputStream().write(finalOP.getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        finalOP = "00";
                        btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }
        });

        lights.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        finalOP = "LI";
                        btSocket.getOutputStream().write((finalOP + "ON").getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        finalOP = "LI";
                        btSocket.getOutputStream().write((finalOP + "OF").getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }
        });

        cruise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        showDialogue(getActivity());
                        btSocket.getOutputStream().write(("SC" + SettingsActivity.cruiseSpeed).getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        finalOP = "00";
                        btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
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
                        finalOP = "ADON";
                        btSocket.getOutputStream().write(finalOP.getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        finalOP = "00";
                        btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
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
                        finalOP = "OMON";
                        btSocket.getOutputStream().write(finalOP.getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }

                } else {
                    try {
                        finalOP = "00";
                        btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                    } catch (IOException e) {
                        msg("Error");
                    }
                }
            }
        });

        leftF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        uncheckOptions();
                        leftForward();
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            if (finalOP == "LR") {
                                finalOP = "RB";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else if (finalOP == "FF") {
                                finalOP = "RF";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else {
                                stop();
                            }

                        } catch (IOException e) {
                            msg("Error");
                        }

                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            if (finalOP == "LR") {
                                finalOP = "RB";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else if (finalOP == "FF") {
                                finalOP = "RF";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else {
                                stop();
                            }
                        } catch (IOException e) {
                            msg("Error");
                        }

                        return true;
                }
                return false;
            }
        });

        leftB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        uncheckOptions();

                        leftBackward();
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            if (finalOP == "RL") {
                                finalOP = "RF";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else if (finalOP == "BB") {
                                finalOP = "RB";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else {
                                stop();
                            }
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            if (finalOP == "RL") {
                                finalOP = "RF";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else if (finalOP == "BB") {
                                finalOP = "RB";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else {
                                stop();
                            }
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;
                }
                return false;
            }
        });

        rightF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        uncheckOptions();

                        rightForward();
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            if (finalOP == "RL") {
                                finalOP = "LB";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else if (finalOP == "FF") {
                                finalOP = "LF";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else {
                                stop();
                            }
                        } catch (IOException e) {
                            msg("Error");
                        }

                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            if (finalOP == "RL") {
                                finalOP = "LB";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else if (finalOP == "FF") {
                                finalOP = "LF";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else {
                                stop();
                            }
                        } catch (IOException e) {
                            msg("Error");
                        }

                        return true;
                }
                return false;
            }
        });

        rightB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        uncheckOptions();

                        rightBackward();
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            if (finalOP == "LR") {
                                finalOP = "LF";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else if (finalOP == "BB") {
                                finalOP = "LB";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else {
                                stop();
                            }
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            if (finalOP == "LR") {
                                finalOP = "LF";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else if (finalOP == "BB") {
                                finalOP = "LB";
                                btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                            } else {
                                stop();
                            }
                        } catch (IOException e) {
                            msg("Error");
                        }
                        return true;

                }
                return false;
            }
        });
        return myFragmentView;

    }

    private void msg(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    public void leftForward() {
        if (btSocket != null) {
            try {
                if (finalOP == "RF") {
                    finalOP = "FF";
                    regualteSpeedInc();
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                } else if (finalOP == "RB") {
                    finalOP = "LR";
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                } else {
                    finalOP = "LF";
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                }

            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    public void leftBackward() {
        if (btSocket != null) {
            try {
                if (finalOP == "RB") {
                    finalOP = "BB";
                    regualteSpeedInc();
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                } else if (finalOP == "RF") {
                    finalOP = "RL";
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                } else {
                    finalOP = "LB";
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                }
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    public void rightForward() {
        if (btSocket != null) {
            try {

                if (finalOP == "LF") {
                    finalOP = "FF";
                    regualteSpeedInc();
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                } else if (finalOP == "LB") {
                    finalOP = "RL";
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                } else {
                    finalOP = "RF";
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                }

            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    public void rightBackward() {
        if (btSocket != null) {
            try {
                if (finalOP == "LB") {
                    finalOP = "BB";
                    regualteSpeedInc();
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                } else if (finalOP == "LF") {
                    finalOP = "LR";
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                } else {
                    finalOP = "RB";
                    btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
                }

            } catch (IOException e) {
                msg("Error");
            }
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Button Control");
    }


    public void stop() {
        try {
            finalOP = "00";
            regulateSpeedDec();
            btSocket.getOutputStream().write((finalOP + (SettingsActivity.carMode)).getBytes());
        } catch (IOException e) {
            msg("Error");
        }
    }

    public void showDialogue(Context context) {
        final AlertDialog.Builder d = new AlertDialog.Builder(context);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.numberpicker_dialogue, null);
        d.setTitle("Cruise Control");
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
                    finalOP = "SC";
                    btSocket.getOutputStream().write((finalOP + SettingsActivity.cruiseSpeed).getBytes());
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


    Handler handler;
    Runnable runnable;

    public void regualteSpeedInc() {
        if (SettingsActivity.carMode == "OF") {
            handler = new Handler();
            handler.postDelayed(runnable = new Runnable() {
                @Override
                public void run() {
                    speedometer.speedTo(50, 1000);

                }
            }, 1000);
        } else {
            handler = new Handler();
            handler.postDelayed(runnable = new Runnable() {
                @Override
                public void run() {
                    speedometer.speedTo(80, 100);
                    speedometer.setSpeedometerColor(Color.RED);
                }
            }, 100);
        }

    }

    public void regulateSpeedDec() {
        handler.removeCallbacks(runnable);
        speedometer.speedTo(0, 100);
        speedometer.setSpeedometerColor(speedColor);
    }

}
