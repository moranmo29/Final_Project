package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Barcode extends AppCompatActivity {

    ImageButton home, addBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        home = (ImageButton)findViewById(R.id.home_btnBarcode);
        addBarcode = (ImageButton)findViewById(R.id.btn_add_barcode);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMenuScreen();
            }
        });

        addBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddBarcodeScreen();
            }
        });
    }

    public void moveMenuScreen() {
        Intent i = new Intent(Barcode.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddBarcodeScreen() {
        Intent i = new Intent(Barcode.this, AddBarcode.class);
        startActivity(i);
    }
}
