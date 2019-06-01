package com.example.arduinoremote2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    Button continueBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        continueBut = findViewById(R.id.button2);
        continueBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent mainIntent = new Intent(Splash.this, DeviceList.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        });

    }
}
