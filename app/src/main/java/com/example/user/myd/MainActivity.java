package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button reg;
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reg = (Button) findViewById(R.id.angry_btn2);
        log = (Button) findViewById(R.id.angry_btn);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveReg();
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveLogin();
            }
        });
    }

    public void moveReg() {
        Intent i = new Intent(MainActivity.this, Registration.class);
        startActivity(i);
    }

    public void moveLogin() {
        Intent i = new Intent(MainActivity.this, MainMenu.class);
        startActivity(i);
    }

}

