package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Costs extends AppCompatActivity {

    ImageButton floatButton, homeFromCosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);

        //add button
        floatButton = (ImageButton) findViewById(R.id.imageButton);
        homeFromCosts = (ImageButton) findViewById(R.id.home_btnCost);


        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(getApplicationContext(),
                        "Button is clicked", Toast.LENGTH_LONG).show();*/
                moveAddSCosts();
            }
        });

        homeFromCosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveFirstScreen();
            }
        });

    }

    public void moveFirstScreen() {
        Intent i = new Intent(Costs.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddSCosts() {
        Intent i = new Intent(Costs.this, AddCosts.class);
        startActivity(i);
    }


}
