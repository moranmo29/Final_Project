package com.moran.myd;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Suppliers extends AppCompatActivity {

    ImageButton home, addSup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        home = (ImageButton)findViewById(R.id.home_btn);
        addSup = (ImageButton)findViewById(R.id.btnShowAddSupplier);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveFirstScreen();
            }
        });

        addSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddSuppScreen();
            }
        });
    }

    public void moveFirstScreen() {
        Intent i = new Intent(Suppliers.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddSuppScreen() {
        Intent i = new Intent(Suppliers.this, AddSupplier.class);
        startActivity(i);
    }
}
