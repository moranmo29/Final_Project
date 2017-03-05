package com.example.user.myd;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Price extends AppCompatActivity {

    ImageButton home, addPrice, addCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        home = (ImageButton)findViewById(R.id.home_btnPrice);
        addPrice = (ImageButton)findViewById(R.id.btn_add_price);
        addCheck = (ImageButton) findViewById(R.id.btn_check_price);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMenuScreen();
            }
        });

        addPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddPriceScreen();
            }
        });

        addCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddPriceCheckScreen();
            }
        });

    }

    public void moveMenuScreen() {
        Intent i = new Intent(Price.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddPriceScreen() {
        Intent i = new Intent(Price.this, AddPrice.class);
        startActivity(i);
    }

    public void moveAddPriceCheckScreen() {
        Intent i = new Intent(Price.this, AddPriceCheck.class);
        startActivity(i);
    }

}
