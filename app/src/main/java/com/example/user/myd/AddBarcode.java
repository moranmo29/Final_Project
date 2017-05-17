package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class AddBarcode extends AppCompatActivity {

    Button cancelBarcodeBtn, saveBtn, btnPlus, btnMinus;
    EditText descriptionBarcode, barcodeNum, quantity;
    private String isKeyBarcode;

    //New!
    DatabaseReference productRef;
    EditText serialNumberBarcode, productName, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barcode);

        //New! - Scan barcode
        ImageButton scanBarcodeBtn = (ImageButton) findViewById(R.id.imgScanBarcode);
        productRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myd-product-manageement-app.firebaseio.com/");

        descriptionBarcode = (EditText) findViewById(R.id.et_product_name);
        barcodeNum = (EditText) findViewById(R.id.add_barcode_number);
        quantity = (EditText) findViewById(R.id.barcodeQuantity);

        btnPlus = (Button) findViewById(R.id.button_plus);
        btnMinus = (Button) findViewById(R.id.button_minus);

        saveBtn = (Button) findViewById(R.id.save_btn);
        cancelBarcodeBtn = (Button) findViewById(R.id.cancel_btn);

        isKeyBarcode = getIntent().getStringExtra("EXTRA_KEY_ID");
        if (isKeyBarcode != null) {

            FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Barcode").child(isKeyBarcode).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    BarcodeNumber barcode = dataSnapshot.getValue(BarcodeNumber.class);
                    descriptionBarcode.setText(barcode.getBarcodeDesc());
                    barcodeNum.setText("" + barcode.getBarcodeNum());
                    quantity.setText("" + barcode.getqUnitsBarcode());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        cancelBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToBarcodeScreen();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBarcodeInFirebase();
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


        //attaching onclick listener - scan button clicked
        scanBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //init scan object
                IntentIntegrator integrator = new IntentIntegrator(AddBarcode.this);
                integrator.initiateScan();
            }
        });

    }

    //Getting the scan results
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //View objects
        serialNumberBarcode = (EditText) findViewById(R.id.add_barcode_number);
        productName = (EditText) findViewById(R.id.et_product_name);
        quantity = (EditText) findViewById(R.id.barcodeQuantity);

        if (scanResult != null) {
            String scanContent = scanResult.getContents();
            serialNumberBarcode.setText(scanContent);
        } else {
            Toast toast = Toast.makeText(this, "לא נקלטה סריקת ברקוד", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void saveBarcodeInFirebase() {

        String description = descriptionBarcode.getText().toString().trim();
        String numBarcode = barcodeNum.getText().toString().trim();
        int quantityBarcode = Integer.parseInt(quantity.getText().toString());
        if (description.equals("") || numBarcode.equals("") | quantityBarcode < 0) {
            //displaying toast - must enter the details
            Toast.makeText(this, "חובה להזין את הפרטים", Toast.LENGTH_SHORT).show();
        } else {
            //Creating Cost object
            final BarcodeNumber bar = new BarcodeNumber(description, numBarcode, quantityBarcode);
            if (isKeyBarcode == null) {
                //Storing values to firebase
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Barcode").push().setValue(bar);
            } else {
                //if user want edit details of supplier - save the data that he change
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Barcode").child(isKeyBarcode).setValue(bar);
            }
            moveToBarcodeScreen();
        }
    }

    public void moveToBarcodeScreen() {
        Intent i = new Intent(AddBarcode.this, Barcode.class);
        startActivity(i);
    }

    //Increases the value by 1 each time a user clicks 'plus button'
    private void plusQuantityClicked() {
        String value = quantity.getText().toString();
        int finalValue = Integer.parseInt(value) + 1;
        quantity.setText("" + finalValue);
    }

    //Reduce one value from quantityUnits every time user press on 'minus button'
    private void minusQuantityClicked() {
        String value = quantity.getText().toString();
        int finalValue = Integer.parseInt(value) - 1;
        //Value must be positive
        if (finalValue <= 0) {
            finalValue = 0;
            Toast.makeText(getApplicationContext(), "הכמות חייבת להיות למעלה מאפס!", Toast.LENGTH_SHORT).show();
        }
        //Display the newly number
        quantity.setText("" + finalValue);
    }
}
