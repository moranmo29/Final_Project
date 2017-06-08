package com.example.user.myd;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Suppliers extends AppCompatActivity {

    ImageButton home, addSup;
    private Supplier[] suppliers;
    private ListView listView;
    private SearchView searchView;
    private SupplierArrayAdapter adapterSup; ///for the searchview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        home = (ImageButton) findViewById(R.id.home_btn);
        addSup = (ImageButton) findViewById(R.id.btnShowAddSupplier);
        listView = (ListView) findViewById(R.id.listviewSup);
        searchView = (SearchView) findViewById(R.id.search);

        //----------------------------- search supplier -------------------------------
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                // Toast.makeText(getBaseContext(), String.valueOf(hasFocus), Toast.LENGTH_SHORT).show();
            }
        });

        //*** setOnQueryTextListener ***
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                //Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                newText = newText.toLowerCase(); // the text that the user insert
                ArrayList<Supplier> newList = new ArrayList<>();
                // Toast.makeText(getBaseContext(), "length "+suppliers.length, Toast.LENGTH_SHORT).show();
                for (Supplier s : suppliers) {
                    // String phone = supplier.getPhoneNumber().toLowerCase(); // search by phone number
                    String name = s.getName().toLowerCase(); // search supplier by name
                    if (name.contains(newText)) {
                        newList.add(s);
                        // Toast.makeText(getBaseContext(), "iiiiiiiiiiiiiiiiiiiiiiiiiii", Toast.LENGTH_SHORT).show();
                    }
                }
                Supplier[] suppliersArray = newList.toArray(new Supplier[newList.size()]);
                adapterSup = new SupplierArrayAdapter(getBaseContext(), suppliersArray); ///
                listView.setAdapter(adapterSup);
                //Toast.makeText(getBaseContext(), newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        //---------------------------------------- End Search ---------------------------------------------

        //Retrieve values from firebase
        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Supplier").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                suppliers = new Supplier[(int) dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    suppliers[i] = postSnapshot.getValue(Supplier.class);
                    //get the key child of supplier - for function delete
                    suppliers[i].setKey(postSnapshot.getKey());
                    i++;
                }
                // listView.setAdapter(new SupplierArrayAdapter(getBaseContext(), suppliers));
                adapterSup = new SupplierArrayAdapter(getBaseContext(), suppliers); ///
                listView.setAdapter(adapterSup); ///
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Supplier").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //refresh: update the ListView after delete supplier
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

        addSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddSuppScreen();
            }
        });

    }

    public void moveFirstScreen() {
        Intent i = new Intent(Suppliers.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddSuppScreen() {
        Intent i = new Intent(Suppliers.this, AddSupplier.class);
        startActivity(i);
    }
}
