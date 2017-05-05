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

    Button cancelToDoBtn, saveToDoBtn;
    EditText titleToDo;
    private RadioGroup rgPriority;
    private RadioButton high, med, low;
    ImageView ivHigh, ivMedium ,ivLow;
    private String isKeyOrder;
    private String selectedType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        titleToDo = (EditText) findViewById(R.id.etAddTask);
        saveToDoBtn = (Button) findViewById(R.id.save_btn);
        cancelToDoBtn = (Button) findViewById(R.id.cancel_btn);
        rgPriority = (RadioGroup)findViewById(R.id.radioPriority); //
        high = (RadioButton) findViewById(R.id.rb_high);
        med = (RadioButton) findViewById(R.id.rab_medium);
        low = (RadioButton) findViewById(R.id.rb_low);
        ivHigh = (ImageView)findViewById(R.id.iv_high);
        ivMedium = (ImageView)findViewById(R.id.iv_medium);
        ivLow = (ImageView)findViewById(R.id.iv_low);


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
    }

    public void saveTaskInFirebase() {

        String description = titleToDo.getText().toString().trim();
        //int quantity = Integer.parseInt(qOrder.getText().toString().trim());
        //double priceForUnit = Double.parseDouble(priceUnitCost.getText().toString());
        if (description.equals("")) {
            //displaying toast - must enter the details
            Toast.makeText(this, "חובה להזין את הפרטים", Toast.LENGTH_SHORT).show();
        } else {
            //Creating Task object
            final ItemToDo itemToDo = new ItemToDo(description);
            //final Cost cost = new Cost(description,priceForUnit, priceForUnit*quantity,quantity);


            if (isKeyOrder == null) {
                //Storing values to firebase
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("ToDo").push().setValue(itemToDo);
            } else {
                //if user want edit details of supplier - save the data that he change
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("ToDo").child(isKeyOrder).setValue(itemToDo);
            }
            moveToDoActivity();
        }
    }


    public void moveToDoActivity() {
        Intent i = new Intent(AddToDo.this, ToDo.class);
        startActivity(i);
    }
}
