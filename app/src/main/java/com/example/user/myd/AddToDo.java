package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AddToDo extends AppCompatActivity {

    Button cancelToDoBtn, saveToDoBtn, btnPlus, btnMinus;
    EditText titleToDo, setPriority;
    private String isKeyOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        titleToDo = (EditText) findViewById(R.id.etAddTask);
        setPriority = (EditText) findViewById(R.id.et_todo_pr);
        saveToDoBtn = (Button) findViewById(R.id.save_btn);
        cancelToDoBtn = (Button) findViewById(R.id.cancel_btn);
        btnPlus = (Button) findViewById(R.id.button_plus);
        btnMinus = (Button) findViewById(R.id.button_minus);


        isKeyOrder = getIntent().getStringExtra("EXTRA_KEY_ID");
        if (isKeyOrder != null) {

            FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("ToDo").child(isKeyOrder).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ItemToDo todo = dataSnapshot.getValue(ItemToDo.class);
                    titleToDo.setText(todo.getTitle());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        saveToDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTaskInFirebase();
            }
        });


        cancelToDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDoActivity();
            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusPriorityClicked();
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusPriorityClicked();
            }
        });
    }

    public void saveTaskInFirebase() {

        String description = titleToDo.getText().toString().trim();
        int priorityTodo = Integer.parseInt(setPriority.getText().toString().trim());

        if (description.equals("")) {
            //displaying toast - must enter the details
            Toast.makeText(this, "חובה להזין את הפרטים", Toast.LENGTH_SHORT).show();
        } else {
            //Creating Task object
            final ItemToDo itemToDo = new ItemToDo(description, priorityTodo);


            if (isKeyOrder == null) {
                //Storing values to firebase
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("ToDo").push().setValue(itemToDo);
            } else {
                //if user want edit details of task - save the data that he change
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("ToDo").child(isKeyOrder).setValue(itemToDo);
            }
            moveToDoActivity();
        }
    }


    public void moveToDoActivity() {
        Intent i = new Intent(AddToDo.this, ToDo.class);
        startActivity(i);
    }

    //Increases the value by 1 each time a user clicks 'plus button'
    private void plusPriorityClicked() {
        String value = setPriority.getText().toString();
        int finalValue = Integer.parseInt(value) + 1;
        //Value must be between 1-3
        if (finalValue <= 1 || finalValue > 3) {
            finalValue = 1;
            Toast.makeText(getApplicationContext(), "בחר עדיפות בין 1-3!", Toast.LENGTH_SHORT).show();
        }
        setPriority.setText("" + finalValue);
    }

    //Reduce one value from quantityUnits every time user press on 'minus button'
    private void minusPriorityClicked() {
        String value = setPriority.getText().toString();
        int finalValue = Integer.parseInt(value) - 1;
        //Value must be between 1-3
        if (finalValue <= 1 || finalValue > 3) {
            finalValue = 1;
            Toast.makeText(getApplicationContext(), "בחר עדיפות בין 1-3!", Toast.LENGTH_SHORT).show();
        }
        //Display the newly number
        setPriority.setText("" + finalValue);
    }
}
