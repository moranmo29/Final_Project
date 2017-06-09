package com.example.user.myd;

import android.content.Intent;
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

public class Shops extends AppCompatActivity {

    private ImageButton addShop, home;
    private Shop[] shops;
    private ListView listView;
    private SearchView searchOrder;
    private ShopArrayAdapter adapterShop; ///for the searchview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        //init view
        listView = (ListView) findViewById(R.id.lv_shops);
        addShop = (ImageButton)findViewById(R.id.addShopButton);
        home = (ImageButton)findViewById(R.id.home_btnShop);
        searchOrder = (SearchView) findViewById(R.id.searchShop);

        //----------------------------- search shop -------------------------------
        //*** setOnQueryTextFocusChangeListener ***
        searchOrder.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
            }
        });

        //*** setOnQueryTextListener ***
        searchOrder.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                newText = newText.toLowerCase(); // the text that the user insert
                ArrayList<Shop> newList = new ArrayList<>();
                for (Shop shop : shops) {
                    String nameShop = shop.getShopName().toLowerCase(); // search shop by name
                    if (nameShop.contains(newText)) {
                        newList.add(shop);
                    }
                }
                Shop[] shopArray = newList.toArray(new Shop[newList.size()]);
                adapterShop = new ShopArrayAdapter(getBaseContext(), shopArray);
                listView.setAdapter(adapterShop);
                return true;
            }
        });
        //---------------------------------------- End Search ---------------------------------------------

        //Retrieve values from firebase
        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Shops").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                shops = new Shop[(int)dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    shops[i] = postSnapshot.getValue(Shop.class);
                    //get the key child of Shop - for function delete
                    shops[i].setKey(postSnapshot.getKey());
                    i++;
                }
                listView.setAdapter(new ShopArrayAdapter(getBaseContext(), shops));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Shops").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //refresh: update the ListView after delete shop
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
                moveFirstScreen();
            }
        });

        addShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddShopScreen();
            }
        });
    }

    public void moveFirstScreen() {
        Intent i = new Intent(Shops.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddShopScreen() {
        Intent i = new Intent(Shops.this, AddShop.class);
        startActivity(i);
    }
}
