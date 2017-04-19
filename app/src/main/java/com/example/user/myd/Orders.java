package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Orders extends AppCompatActivity {

    ImageButton home, addOrder;
    private Order[] orders;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        //init view
        home = (ImageButton)findViewById(R.id.orders_home_btn);
        addOrder = (ImageButton)findViewById(R.id.add_order_btn);
        listView = (ListView) findViewById(R.id.lv_order);

        //Retrieve values from firebase
        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Order ", "" + dataSnapshot.getChildrenCount());
                orders = new Order[(int)dataSnapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    orders[i] = postSnapshot.getValue(Order.class);
                    //get the key child of order - for function delete
                    orders[i].setKey(postSnapshot.getKey());
                    i++;
                }
                listView.setAdapter(new OrderArrayAdapter(getBaseContext(), orders));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //refresh: update the ListView after delete order
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

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveAddOrderScreen();
            }
        });
    }


    public void moveFirstScreen() {
        Intent i = new Intent(Orders.this, MainMenu.class);
        startActivity(i);
    }

    public void moveAddOrderScreen() {
        Intent i = new Intent(Orders.this, AddOrder.class);
        startActivity(i);
    }

}
