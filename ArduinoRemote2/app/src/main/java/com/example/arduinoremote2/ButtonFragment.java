package com.example.arduinoremote2;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;

import static com.example.arduinoremote2.HomeActivity.btSocket;


public class ButtonFragment extends Fragment {

    static String finalOP = "00";
    static String preFinalOP= "00";

    ImageButton leftF;
    ImageButton leftB;
    ImageButton rightF;
    ImageButton rightB;
    ToggleButton adaptive;
    ToggleButton lights;
    ToggleButton cruise;
    ToggleButton park;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_button, container, false);
        leftF=myFragmentView.findViewById(R.id.imageButton6);
        leftB=myFragmentView.findViewById(R.id.imageButton5);
        rightF=myFragmentView.findViewById(R.id.imageButton2);
        rightB=myFragmentView.findViewById(R.id.imageButton3);

        adaptive=myFragmentView.findViewById(R.id.toggleButton2);
        lights=myFragmentView.findViewById(R.id.toggleButton3);
        cruise=myFragmentView.findViewById(R.id.toggleButton4);
        park=myFragmentView.findViewById(R.id.toggleButton5);

        adaptive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        lights.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        cruise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        park.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        leftF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                            leftForward();
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            if (finalOP == "LR") {
                                finalOP = "RB";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else if(finalOP=="FF"){
                                finalOP="RF";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else{
                                stop();
                            }

                        }
                        catch(IOException e) {
                            msg("Error");
                        }

                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            if (finalOP == "LR") {
                                finalOP = "RB";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else if(finalOP=="FF"){
                                finalOP="RF";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else{
                                stop();
                            }
                        }
                        catch(IOException e) {
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
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                            leftBackward();
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            if (finalOP == "RL") {
                                finalOP = "RF";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else if(finalOP=="BB"){
                                finalOP="RB";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else{
                                stop();
                            }
                        }
                        catch(IOException e) {
                            msg("Error");
                        }
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            if (finalOP == "RL") {
                                finalOP = "RF";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else if(finalOP=="BB"){
                                finalOP="RB";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else{
                                stop();
                            }
                        }
                        catch(IOException e) {
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
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                            rightForward();
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            if (finalOP == "RL") {
                                finalOP = "LB";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else if(finalOP=="FF"){
                                finalOP="LF";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else{
                                stop();
                            }
                        }
                        catch(IOException e) {
                            msg("Error");
                        }

                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            if (finalOP == "RL") {
                                finalOP = "LB";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else if(finalOP=="FF"){
                                finalOP="LF";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else{
                                stop();
                            }
                        }
                        catch(IOException e) {
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
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rightBackward();
                        return true;
                    case MotionEvent.ACTION_UP:
                        try {
                            if (finalOP == "LR") {
                                finalOP = "LF";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else if(finalOP=="BB"){
                                finalOP="LB";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else{
                                stop();
                            }
                        }
                        catch(IOException e) {
                            msg("Error");
                        }
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        try {
                            if (finalOP == "LR") {
                                finalOP = "LF";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else if(finalOP=="BB"){
                                finalOP="LB";
                                btSocket.getOutputStream().write(finalOP.getBytes());
                            } else{
                                stop();
                            }
                        }
                        catch(IOException e) {
                            msg("Error");
                        }
                        return true;

                }
                return false;
            }
        });

        return myFragmentView;

    }

    private void msg(String s)
    {
        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
    }

    public void leftForward(){
        if (btSocket!=null)
        {
            try
            {
                if(finalOP=="00") {
                    finalOP="LF";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }
                else if(finalOP=="RF"){
                    finalOP="FF";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }
                else if(finalOP=="RB") {
                    finalOP="LR";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }

            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void leftBackward(){
        if (btSocket!=null)
        {
            try
            {
                if(finalOP=="00") {
                    finalOP="LB";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }
                else if(finalOP=="RB"){
                    finalOP="BB";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }
                else if(finalOP=="RF"){
                    finalOP="RL";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void rightForward(){
        if (btSocket!=null)
        {
            try
            {
                if(finalOP=="00") {
                    finalOP="RF";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }
                else if(finalOP=="LF"){
                    finalOP="FF";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }
                else if(finalOP=="LB"){
                    finalOP="RL";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }

            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void rightBackward(){
        if (btSocket!=null)
        {
            try
            {
                if(finalOP=="00") {
                    finalOP="RB";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }
                else if(finalOP=="LB"){
                    finalOP="BB";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }
                else if(finalOP=="LF"){
                    finalOP="LR";
                    btSocket.getOutputStream().write(finalOP.getBytes());
                }

            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Menu 1");
    }

    public void stop(){
        try{
            finalOP="00";

            btSocket.getOutputStream().write(finalOP.getBytes());
        }
        catch(IOException e){
            msg("Error");
        }
    }
}
