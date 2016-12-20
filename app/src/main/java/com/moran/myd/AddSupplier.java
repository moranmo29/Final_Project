package com.moran.myd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddSupplier extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner phoneSpinner, emailSpinner, addressSpinner;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);

        phoneSpinner = (Spinner)findViewById(R.id.phone_spinner);
        emailSpinner = (Spinner)findViewById(R.id.email_spinner);
        addressSpinner = (Spinner)findViewById(R.id.address_spinner);

        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        /*Manage the data - the adapter will put data inside the spinner
        android.R.layout.simple_spinner_item contain a TextView that is repeated to form the List structure  */
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.typePhoneNumber, android.R.layout.simple_spinner_item);
        ArrayAdapter adapterMailSpinner = ArrayAdapter.createFromResource(this, R.array.typeEmail, android.R.layout.simple_spinner_item);
        ArrayAdapter adapterAddressSpinner = ArrayAdapter.createFromResource(this, R.array.typeAddress, android.R.layout.simple_spinner_item);

        phoneSpinner.setAdapter(adapter);
        emailSpinner.setAdapter(adapterMailSpinner);
        addressSpinner.setAdapter(adapterAddressSpinner);

        //click on spinner
        phoneSpinner.setOnItemSelectedListener(this);
        emailSpinner.setOnItemSelectedListener(this);
        addressSpinner.setOnItemSelectedListener(this);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSupplier();
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

    public void moveToSupplier(){
        Intent i = new Intent(AddSupplier.this, Suppliers.class);
        startActivity(i);
    }
}
