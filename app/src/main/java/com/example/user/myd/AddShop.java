package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddShop extends AppCompatActivity {

    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToShops();
            }
        });
    }

    public void moveToShops(){
        Intent i = new Intent(AddShop.this, Shops.class);
        startActivity(i);
    }
}
