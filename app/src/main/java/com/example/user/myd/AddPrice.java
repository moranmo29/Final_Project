package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddPrice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner priceTypeCoin;
    Button priceCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);

        priceTypeCoin = (Spinner)findViewById(R.id.price_unit_spinner);
        priceCancelBtn = (Button)findViewById(R.id.cancel_btn);

          /*Manage the data - the adapter will put data inside the spinner
        android.R.layout.simple_spinner_item contain a TextView that is repeated to form the List structure  */
        ArrayAdapter adapterTypeCoin = ArrayAdapter.createFromResource(this, R.array.priceTypeCoinSpinner, android.R.layout.simple_spinner_item);


        priceTypeCoin.setAdapter(adapterTypeCoin);
        priceTypeCoin.setOnItemSelectedListener(this);

        priceCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPrice();
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

    public void moveToPrice(){
        Intent i = new Intent(AddPrice.this, Price.class);
        startActivity(i);
    }
}
