package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Orders extends AppCompatActivity {

    ImageButton home, addOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        home = (ImageButton)findViewById(R.id.orders_home_btn);
        addOrder = (ImageButton)findViewById(R.id.add_order_btn);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveFirstScreen();
            }
        });

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddOrderScreen();
            }
        });
    }

    public void moveFirstScreen() {
        Intent i = new Intent(Orders.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddOrderScreen() {
        Intent i = new Intent(Orders.this, AddOrder.class);
        startActivity(i);
    }
}
