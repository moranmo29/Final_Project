package com.moran.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddToDo extends AppCompatActivity {

    Button cancelToDoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        cancelToDoBtn = (Button)findViewById(R.id.cancel_btn);

        cancelToDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDoActivity();
            }
        });
    }

    public void moveToDoActivity(){
        Intent i = new Intent(AddToDo.this, ToDo.class);
        startActivity(i);
    }
}
