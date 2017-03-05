package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class ToDo extends AppCompatActivity {

    ImageButton home, addTask;
    ListView itemsToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        home = (ImageButton)findViewById(R.id.home_btnToDo);
        addTask = (ImageButton)findViewById(R.id.btn_add_task);
        itemsToDo = (ListView)findViewById(R.id.listView_todo);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMenuScreen();
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddToDoActivity();
            }
        });

    }

    public void moveAddToDoActivity() {
        Intent i = new Intent(ToDo.this, AddToDo.class);
        startActivity(i);
    }

    public void moveMenuScreen() {
        Intent i = new Intent(ToDo.this, MainMenu.class);
        startActivity(i);
    }

}
