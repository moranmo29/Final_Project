package com.moran.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddBarcode extends AppCompatActivity {

    Button cancelBarcodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barcode);

        cancelBarcodeBtn = (Button)findViewById(R.id.cancel_btn);

        cancelBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToBarcodeScreen();
            }
        });
    }

    public void moveToBarcodeScreen(){
        Intent i = new Intent(AddBarcode.this, Barcode.class);
        startActivity(i);
    }
}
