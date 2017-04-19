package com.example.user.myd;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AddCosts extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner typeCoin;
    Button cancelBtn, saveBtn, btnPlus, btnMinus;
    EditText descriptionCost, priceUnitCost, quantityUnitsCost, priceTotalCost;

    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String isKeyCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_add_costs);

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();

        typeCoin = (Spinner) findViewById(R.id.costs_type_coin_spinner);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        saveBtn = (Button) findViewById(R.id.save_btn);
        descriptionCost = (EditText) findViewById(R.id.add_costs_description);
        priceUnitCost = (EditText) findViewById(R.id.costPrice);
        quantityUnitsCost = (EditText) findViewById(R.id.costsQuantity);

        btnPlus = (Button) findViewById(R.id.button_plus);
        btnMinus = (Button) findViewById(R.id.button_minus);

         /*Manage the data - the adapter will put data inside the spinner
        android.R.layout.simple_spinner_item contain a TextView that is repeated to form the List structure  */
        ArrayAdapter adapterTypeCoin = ArrayAdapter.createFromResource(this, R.array.typeCoinSpinner, android.R.layout.simple_spinner_item);


        typeCoin.setAdapter(adapterTypeCoin);
        typeCoin.setOnItemSelectedListener(this);

        isKeyCost = getIntent().getStringExtra("EXTRA_KEY_ID");
        if (isKeyCost != null) {

            FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Costs").child(isKeyCost).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Cost cost = dataSnapshot.getValue(Cost.class);
                    descriptionCost.setText(cost.getDescription());
                    priceUnitCost.setText("" + cost.getPriceUnit());
                    quantityUnitsCost.setText("" + cost.getQuantityUnits());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToCosts();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCostInFirebase();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // TextView myText = (TextView) view;
        // Toast.makeText(this, "you selected:" + myText.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void saveCostInFirebase() {

        String description = descriptionCost.getText().toString();
        int quantity = Integer.parseInt(quantityUnitsCost.getText().toString());
        double priceForUnit = Double.parseDouble(priceUnitCost.getText().toString());
        if (description.equals("") || quantity == 0 || priceForUnit == 0) {
            //displaying toast - must enter the details
            Toast.makeText(this, "חובה להזין את הפרטים", Toast.LENGTH_SHORT).show();
        } else {
            //Total sum - up to three decimal places
            double totalSum = Double.parseDouble(String.format("%.2f", priceForUnit * quantity));
            //Creating Cost object
            final Cost cost = new Cost(description, priceForUnit, totalSum, quantity);
            //final Cost cost = new Cost(description,priceForUnit, priceForUnit*quantity,quantity);


            if (isKeyCost == null) {
                //Storing values to firebase
                mDatabase.child("users").child(mUserId).child("Costs").push().setValue(cost);
            } else {
                //if user want edit details of supplier - save the data that he change
                mDatabase.child("users").child(mUserId).child("Costs").child(isKeyCost).setValue(cost);
            }
            moveToCosts();
        }
    }

    public void moveToCosts() {
        Intent i = new Intent(AddCosts.this, Costs.class);
        startActivity(i);
    }

    //Increases the value by 1 each time a user clicks 'plus button'
    private void plusQuantityClicked() {
        String value = quantityUnitsCost.getText().toString();
        int finalValue = Integer.parseInt(value) + 1;
        quantityUnitsCost.setText("" + finalValue);
    }

    //Reduce one value from quantityUnits every time user press on 'minus button'
    private void minusQuantityClicked() {
        String value = quantityUnitsCost.getText().toString();
        int finalValue = Integer.parseInt(value) - 1;
        //Value must be positive
        if (finalValue <= 0) {
            finalValue = 0;
            Toast.makeText(getApplicationContext(), "הכמות חייבת להיות למעלה מאפס!", Toast.LENGTH_SHORT).show();
        }
        //Display the newly number
        quantityUnitsCost.setText("" + finalValue);
    }
}
