package com.moran.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registration extends AppCompatActivity {

    Button cancel_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        cancel_reg = (Button)findViewById(R.id.cancel_btn);

        cancel_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveFirstScreen();
            }
        });

    }

    private void moveFirstScreen() {
        Intent i = new Intent(Registration.this, LoginActivity.class);
        startActivity(i);
    }
}
