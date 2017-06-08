package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Costs extends AppCompatActivity {

    ImageButton floatButton, homeFromCosts;
    private Cost[] costs;
    private ListView listView;
    private SearchView searchView;
    private CostArrayAdapter adapterCost; ///for the searchview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);
        String value = getIntent().getStringExtra("coins");
        listView = (ListView) findViewById(R.id.lv_Costs);
        searchView = (SearchView) findViewById(R.id.searchCost);

        //Log.e(" Cost ", "" + FirebaseDbHandler.mDatabase);

        //----------------------------- search costs -------------------------------
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
            }
        });

        //*** setOnQueryTextListener ***
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                newText = newText.toLowerCase(); // the text that the user insert
                ArrayList<Cost> newList = new ArrayList<>();
                for (Cost c : costs) {
                    String descriptionCost = c.getDescription().toLowerCase(); // search cost by description
                    if (descriptionCost.contains(newText)) {
                        newList.add(c);
                    }
                }
                Cost[] costsArray = newList.toArray(new Cost[newList.size()]);
                adapterCost = new CostArrayAdapter(getBaseContext(), costsArray);
                listView.setAdapter(adapterCost);
                return true;
            }
        });
        //---------------------------------------- End Search ---------------------------------------------

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

    private void moveFirstScreen() {
        Intent i = new Intent(Costs.this, MainMenu.class);
        startActivity(i);
    }

    private void moveAddSCosts() {
        Intent i = new Intent(Costs.this, AddCosts.class);
        startActivity(i);
    }

}
