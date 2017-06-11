package com.example.user.myd;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Price extends AppCompatActivity {

    ImageButton home, addPrice;
    private PriceList[] prices;
    private ListView listView;
    private SearchView searchView;
    private PriceArrayAdapter adapterPrice; ///for the searchview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        listView = (ListView) findViewById(R.id.lv_price);
        searchView = (SearchView) findViewById(R.id.searchPrice);

        //----------------------------- search price -------------------------------
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
                ArrayList<PriceList> newList = new ArrayList<>();
                for (PriceList p : prices) {
                    String name = p.getProductName().toLowerCase(); // search price by product name
                    String comment = p.getPriceComments().toLowerCase(); // search price in comments
                    String price = String.valueOf(p.getPriceUnit()); // search price by price
                    if (name.contains(newText) || comment.contains(newText) || price.contains(newText)) {
                        newList.add(p);
                    }
                }
                PriceList[] priceArray = newList.toArray(new PriceList[newList.size()]);
                adapterPrice = new PriceArrayAdapter(getBaseContext(), priceArray); ///
                listView.setAdapter(adapterPrice);
                return true;
            }
        });
        //---------------------------------------- End Search ---------------------------------------------

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
