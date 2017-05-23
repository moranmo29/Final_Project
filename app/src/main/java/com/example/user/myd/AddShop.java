package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AddShop extends AppCompatActivity {

    Button cancelBtn, saveBtn, btnPlus, btnMinus;
    EditText nameShop, addressShop, cMan, emailShop, nPhone, qSold;
    private String isKeyShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        //init view
        nameShop = (EditText) findViewById(R.id.add_shop_name);
        cMan = (EditText) findViewById(R.id.et_shop_contact);
        addressShop = (EditText) findViewById(R.id.et_shop_address);
        emailShop = (EditText) findViewById(R.id.et_shop_email);
        nPhone = (EditText) findViewById(R.id.et_shop_phone_number);
        qSold = (EditText) findViewById(R.id.soldQuantity);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        saveBtn = (Button) findViewById(R.id.save_btn);
        btnPlus = (Button) findViewById(R.id.button_plus);
        btnMinus = (Button) findViewById(R.id.button_minus);

        isKeyShop = getIntent().getStringExtra("EXTRA_KEY_ID");
        if (isKeyShop != null) {
            FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Shops").child(isKeyShop).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Shop shop = dataSnapshot.getValue(Shop.class);
                    nameShop.setText(shop.getShopName());
                    cMan.setText(shop.getContactMan());
                    addressShop.setText(shop.getShopAddress());
                    emailShop.setText(shop.getShopEmail());
                    nPhone.setText(shop.getShopNumberPhone());
                    qSold.setText(""+shop.getqSold());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveShopInFirebase();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToShops();
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusQuantityClicked();
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusQuantityClicked();
            }
        });
    }

    public void saveShopInFirebase() {
        String name = nameShop.getText().toString().trim();
        String phone = nPhone.getText().toString().trim();
        int quantity = Integer.parseInt(qSold.getText().toString().trim());
        if (name.equals("") || phone.equals("") || quantity <= 0 ) {
            Toast.makeText(this, "חובה להזין שם החנות, מספר טלפון וכמות", Toast.LENGTH_SHORT).show();
        } else {
            String address = addressShop.getText().toString().trim();
            String contact = cMan.getText().toString().trim();
            String email = emailShop.getText().toString().trim();

            //Creating Shop object
            final Shop shop = new Shop(name, address, contact, email, phone, quantity);

            if (isKeyShop == null) {
                //Storing values to firebase
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Shops").push().setValue(shop);
            } else {
                //if user want edit details of supplier - save the data that he change
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Shops").child(isKeyShop).setValue(shop);
            }
            moveToShops();
        }
    }

    public void moveToShops(){
        Intent i = new Intent(AddShop.this, Shops.class);
        startActivity(i);
    }

    //Increases the value by 1 each time a user clicks 'plus button'
    private void plusQuantityClicked() {
        String value = qSold.getText().toString();
        int finalValue = Integer.parseInt(value) + 1;
        qSold.setText("" + finalValue);
    }

    //Reduce one value from quantityUnits every time user press on 'minus button'
    private void minusQuantityClicked() {
        String value = qSold.getText().toString();
        int finalValue = Integer.parseInt(value) - 1;
        //Value must be positive
        if (finalValue <= 0) {
            finalValue = 0;
            Toast.makeText(getApplicationContext(), "הכמות חייבת להיות למעלה מאפס!", Toast.LENGTH_SHORT).show();
        }
        //Display the newly number
        qSold.setText("" + finalValue);
    }
}
