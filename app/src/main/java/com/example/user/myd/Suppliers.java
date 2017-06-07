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


public class Suppliers extends AppCompatActivity {

    ImageButton home, addSup;
    private Supplier[] suppliers;
    private ListView listView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        home = (ImageButton) findViewById(R.id.home_btn);
        addSup = (ImageButton) findViewById(R.id.btnShowAddSupplier);
        listView = (ListView) findViewById(R.id.listviewSup);
        searchView = (SearchView) findViewById(R.id.search);

        //Retrieve values from firebase
        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Supplier").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                suppliers = new Supplier[(int)dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    suppliers[i] = postSnapshot.getValue(Supplier.class);
                    //get the key child of supplier - for function delete
                    suppliers[i].setKey(postSnapshot.getKey());
                    i++;
                }
                listView.setAdapter(new SupplierArrayAdapter(getBaseContext(), suppliers));
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
