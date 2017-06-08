package com.example.user.myd;

import android.content.Intent;
import android.media.Image;
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

public class Price extends AppCompatActivity {

    ImageButton home, addPrice;
    private PriceList[] prices;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        listView = (ListView) findViewById(R.id.lv_price);

        //Retrieve values from firebase
        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Price").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prices = new PriceList[(int)dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    prices[i] = postSnapshot.getValue(PriceList.class);
                    //get the key child of price - for function delete
                    prices[i].setKey(postSnapshot.getKey());
                    i++;
                }
                listView.setAdapter(new PriceArrayAdapter(getBaseContext(), prices));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Price").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //refresh: update the ListView after delete price
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


        home = (ImageButton)findViewById(R.id.home_btnPrice);
        addPrice = (ImageButton)findViewById(R.id.btn_add_price);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMenuScreen();
            }
        });

        addPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddPriceScreen();
            }
        });


    }

    public void moveMenuScreen() {
        Intent i = new Intent(Price.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddPriceScreen() {
        Intent i = new Intent(Price.this, AddPrice.class);
        startActivity(i);
    }

}
