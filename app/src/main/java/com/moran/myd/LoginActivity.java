package com.moran.myd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class LoginActivity extends AppCompatActivity {

    Button reg;
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reg = (Button) findViewById(R.id.register_btn);
        log = (Button) findViewById(R.id.enter_btn);


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
        Intent i = new Intent(LoginActivity.this, Registration.class);
        startActivity(i);
    }

    public void moveLogin() {
        Intent i = new Intent(LoginActivity.this, MainMenu.class);
        startActivity(i);
    }


}
