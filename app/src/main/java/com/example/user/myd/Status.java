package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Status extends AppCompatActivity {
    ImageButton home, editStatusBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        home = (ImageButton) findViewById(R.id.home_btnStatus);
        editStatusBtn = (ImageButton) findViewById(R.id.btn_add_status);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMenuScreen();
            }
        });

        editStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddEditStstusScreen();
            }
        });

    }

    public void moveMenuScreen() {
        Intent i = new Intent(Status.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddEditStstusScreen() {
        Intent i = new Intent(Status.this, AddEditStatus.class);
        startActivity(i);
    }
}
