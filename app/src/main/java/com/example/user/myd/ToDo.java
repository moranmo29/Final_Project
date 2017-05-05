package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ToDo extends AppCompatActivity {

    ImageButton home, addTask;
    private ItemToDo[] tasks;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        //init view
        home = (ImageButton)findViewById(R.id.home_btnToDo);
        addTask = (ImageButton)findViewById(R.id.btn_add_task);
        listView = (ListView)findViewById(R.id.lv_todo);

        //Retrieve values from firebase
        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("ToDo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("ToDo ", "" + dataSnapshot.getChildrenCount());
                tasks = new ItemToDo[(int)dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    tasks[i] = postSnapshot.getValue(ItemToDo.class);
                    //get the key child of task - for function delete
                    tasks[i].setKey(postSnapshot.getKey());
                    i++;
                }
                listView.setAdapter(new ToDoArrayAdapter(getBaseContext(), tasks));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("ToDo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //refresh: update the ListView after delete task
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
