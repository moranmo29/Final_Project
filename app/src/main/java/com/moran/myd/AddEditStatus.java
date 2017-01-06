package com.moran.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddEditStatus extends AppCompatActivity {

    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_status);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToStatusScreen();
            }
        });
    }

    public void moveToStatusScreen() {
        Intent i = new Intent(AddEditStatus.this, Status.class);
        startActivity(i);
    }
}