package com.moran.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Shops extends AppCompatActivity {

    ImageButton addShop, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        addShop = (ImageButton)findViewById(R.id.addShopButton);
        home = (ImageButton)findViewById(R.id.home_btnShop);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveFirstScreen();
            }
        });

        addShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddShopScreen();
            }
        });
    }

    public void moveFirstScreen() {
        Intent i = new Intent(Shops.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddShopScreen() {
        Intent i = new Intent(Shops.this, AddShop.class);
        startActivity(i);
    }
}
