package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseListAdapter;


public class Suppliers extends AppCompatActivity {

    ImageButton home, addSup;
    //
    ListView listViewSupplier;
    DatabaseReference db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        home = (ImageButton)findViewById(R.id.home_btn);
        addSup = (ImageButton)findViewById(R.id.btnShowAddSupplier);

        /////
        db= FirebaseDatabase.getInstance().getReferenceFromUrl("https://myd-product-manageement-app.firebaseio.com/users");
        FirebaseListAdapter<Supplier> firebaseListAdapter = new FirebaseListAdapter<Supplier>(this,Supplier.class, R.layout.row_supplier_item, db) {
            @Override
            protected void populateView(View v, Supplier supplier, int position) {
                TextView text1=(TextView)v.findViewById(R.id.text1);
                TextView text2=(TextView)v.findViewById(R.id.text2);
                TextView text3=(TextView)v.findViewById(R.id.text3);
                TextView text4=(TextView)v.findViewById(R.id.text4);
                TextView text5=(TextView)v.findViewById(R.id.text5);
                TextView text6=(TextView)v.findViewById(R.id.text6);
                text1.setText(supplier.getName());
                text2.setText(supplier.getCompany());
                text3.setText(supplier.getAddress());
                text4.setText(supplier.getEmail());
                text5.setText(supplier.getPhoneNumber());
                text6.setText(supplier.getComments());
            }
        };
        listViewSupplier = (ListView)findViewById(R.id.listviewSup);
        listViewSupplier.setAdapter(firebaseListAdapter);
        ////

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
