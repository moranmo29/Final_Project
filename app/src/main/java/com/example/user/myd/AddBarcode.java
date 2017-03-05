package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class AddBarcode extends AppCompatActivity {

    Button cancelBarcodeBtn;

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


        cancelBarcodeBtn = (Button) findViewById(R.id.cancel_btn);

        cancelBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToBarcodeScreen();
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

    public void moveToBarcodeScreen() {
        Intent i = new Intent(AddBarcode.this, Barcode.class);
        startActivity(i);
    }

    //Getting the scan results
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //View objects
        serialNumberBarcode = (EditText) findViewById(R.id.add_barcode_number);
        productName = (EditText) findViewById(R.id.et_product_name);
        country = (EditText) findViewById(R.id.et_country);

        if (scanResult != null) {
            String scanContent = scanResult.getContents();
            serialNumberBarcode.setText(scanContent);
        } else {
            Toast toast = Toast.makeText(this, "לא נקלטה סריקת ברקוד", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
