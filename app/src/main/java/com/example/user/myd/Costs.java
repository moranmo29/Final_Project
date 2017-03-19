package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Costs extends AppCompatActivity {

    ImageButton floatButton, homeFromCosts;
    private Cost[] costs;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);

        listView = (ListView) findViewById(R.id.lv_Costs);

        Log.e(" Cost ", "" + FirebaseDbHandler.mDatabase);

        //Retrieve values from firebase
        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Costs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                costs = new Cost[(int)dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    costs[i] = postSnapshot.getValue(Cost.class);
                    //get the key child of cost - for function delete
                    costs[i].setKey(postSnapshot.getKey());
                    i++;
                }
                listView.setAdapter(new CostArrayAdapter(getBaseContext(), costs));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Costs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //refresh: update the ListView after delete cost
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


        //add button
        floatButton = (ImageButton) findViewById(R.id.imageButton);
        homeFromCosts = (ImageButton) findViewById(R.id.home_btnCost);


        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(getApplicationContext(),
                        "Button is clicked", Toast.LENGTH_LONG).show();*/
                moveAddSCosts();
            }
        });

        homeFromCosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveFirstScreen();
            }
        });

    }

    public void moveFirstScreen() {
        Intent i = new Intent(Costs.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddSCosts() {
        Intent i = new Intent(Costs.this, AddCosts.class);
        startActivity(i);
    }


}
