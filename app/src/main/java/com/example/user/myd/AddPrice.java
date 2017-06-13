package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddPrice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner priceTypeCoin;
    Button priceCancelBtn, saveBtn, btnPlus, btnMinus;
    EditText minQuantity, priceForUnit, pComments;
    private String isKeyPrice;
    //for spinner
    private Spinner productNameList;
    private List<String> nameProducts;
    String selectedProductName, selectedCoinType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);

        //init view
        minQuantity = (EditText) findViewById(R.id.priceMinimumQuantity);
        priceForUnit = (EditText) findViewById(R.id.addPriceCost);
        pComments = (EditText) findViewById(R.id.priceComments);
        saveBtn = (Button) findViewById(R.id.save_btn);
        btnPlus = (Button) findViewById(R.id.button_plus);
        btnMinus = (Button) findViewById(R.id.button_minus);

        productNameList = (Spinner) findViewById(R.id.spinnerProductName);
        nameProducts = new ArrayList<>();// for spinner

        // Displays the product name list at Spinner - in Price activity
        DatabaseReference ref = FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Barcode");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameProducts.add("שם מוצר"); // add title to the spinner
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    //Getting the names of all products name that exist in barcode screen
                    nameProducts.add(singleSnapshot.getValue(BarcodeNumber.class).getBarcodeDesc());
                }

                //Displays the list of supplier in Spinner - the adapter will put data inside the spinner
                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_list_item_1, nameProducts);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                productNameList.setAdapter(adp1);
                //Get the selected text in Spinner via position
                productNameList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedProductName = parent.getItemAtPosition(position).toString().trim();
                        //If user choose the spinner title, insert an empty string
                        if (selectedProductName == parent.getItemAtPosition(0).toString().trim()) {
                            selectedProductName = "";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        isKeyPrice = getIntent().getStringExtra("EXTRA_KEY_ID");
        if (isKeyPrice != null) {
            FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Price").child(isKeyPrice).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PriceList price = dataSnapshot.getValue(PriceList.class);
                    try {
                        minQuantity.setText("" + price.getqMin());
                        priceForUnit.setText("" + price.getPriceUnit());
                        pComments.setText(price.getPriceComments());
                    } catch (NullPointerException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //Toast.makeText(getBaseContext(),"Deleted",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        priceTypeCoin = (Spinner) findViewById(R.id.price_unit_spinner);
        priceCancelBtn = (Button) findViewById(R.id.cancel_btn);

          /*Manage the data - the adapter will put data inside the spinner
        android.R.layout.simple_spinner_item contain a TextView that is repeated to form the List structure  */
        ArrayAdapter adapterTypeCoin = ArrayAdapter.createFromResource(this, R.array.priceTypeCoinSpinner, android.R.layout.simple_spinner_item);

        //Spinner TypeCoin
        priceTypeCoin.setAdapter(adapterTypeCoin);
        priceTypeCoin.setOnItemSelectedListener(this);

        priceCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPrice();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePriceInFirebase();
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
        selectedCoinType = adapterView.getItemAtPosition(i).toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void savePriceInFirebase() {
        try {
            int quantityMin = Integer.parseInt(minQuantity.getText().toString().trim());
            double priceUnit = Double.parseDouble(priceForUnit.getText().toString().trim());
            if (priceUnit < 0 || quantityMin <= 0) {
                Toast.makeText(this, "חובה להזין פרטים", Toast.LENGTH_SHORT).show();
            } else {
                String comment = pComments.getText().toString().trim();

                //Creating Price object
                final PriceList price = new PriceList(selectedProductName, quantityMin, priceUnit, selectedCoinType, comment);

                if (isKeyPrice == null) {
                    //Storing values to firebase
                    FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Price").push().setValue(price);
                } else {
                    //if user want edit details of supplier - save the data that he change
                    FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Price").child(isKeyPrice).setValue(price);
                }
                moveToPrice();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "חובה להזין פרטים", Toast.LENGTH_SHORT).show();
        }
    }

    public void moveToPrice() {
        Intent i = new Intent(AddPrice.this, Price.class);
        i.putExtra("coins", selectedCoinType);
        startActivity(i);
    }


    //Increases the value by 1 each time a user clicks 'plus button'
    private void plusQuantityClicked() {
        String value = minQuantity.getText().toString();
        int finalValue = Integer.parseInt(value) + 1;
        minQuantity.setText("" + finalValue);
    }

    //Reduce one value from quantityUnits every time user press on 'minus button'
    private void minusQuantityClicked() {
        String value = minQuantity.getText().toString();
        int finalValue = Integer.parseInt(value) - 1;
        //Value must be positive
        if (finalValue <= 0) {
            finalValue = 0;
            Toast.makeText(getApplicationContext(), "הכמות חייבת להיות למעלה מאפס!", Toast.LENGTH_SHORT).show();
        }
        //Display the newly number
        minQuantity.setText("" + finalValue);
    }
}
