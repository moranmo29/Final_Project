package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddToDo extends AppCompatActivity {

    Button cancelToDoBtn, saveToDoBtn;
    EditText titleToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        titleToDo = (EditText) findViewById(R.id.etAddTask);
        saveToDoBtn = (Button) findViewById(R.id.save_btn);
        cancelToDoBtn = (Button) findViewById(R.id.cancel_btn);

        saveToDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddToDo.this, ToDo.class);
                startActivity(i);
            }
        });

        cancelToDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDoActivity();
            }
        });
    }

    public void moveToDoActivity() {
        Intent i = new Intent(AddToDo.this, ToDo.class);
        startActivity(i);
    }
}
