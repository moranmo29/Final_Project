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

public class Barcode extends AppCompatActivity {

    ImageButton home, addBarcode;
    private BarcodeNumber[] barcodes;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        listView = (ListView) findViewById(R.id.lv_barcode);

        //Retrieve values from firebase
        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Barcode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                barcodes = new BarcodeNumber[(int)dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    barcodes[i] = postSnapshot.getValue(BarcodeNumber.class);
                    //get the key child of barcodeNumber - for function delete
                    barcodes[i].setKey(postSnapshot.getKey());
                    i++;
                }
                listView.setAdapter(new BarcodeArrayAdapter(getBaseContext(), barcodes));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Barcode").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //refresh: update the ListView after delete barcode
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


        home = (ImageButton)findViewById(R.id.home_btnBarcode);
        addBarcode = (ImageButton)findViewById(R.id.btn_add_barcode);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMenuScreen();
            }
        });

        addBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddBarcodeScreen();
            }
        });
    }

    public void moveMenuScreen() {
        Intent i = new Intent(Barcode.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddBarcodeScreen() {
        Intent i = new Intent(Barcode.this, AddBarcode.class);
        startActivity(i);
    }
}
