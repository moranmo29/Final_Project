package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AddSupplier extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner phoneSpinner, emailSpinner, addressSpinner;
    Button cancelBtn, saveBtn;
    EditText nameSupplier, companySupplier, addressSupplier, emailSupplier, phoneNumberSup, commentsSupplier;
    private String isKeySupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);

        //init view
        phoneSpinner = (Spinner) findViewById(R.id.phone_spinner);
        emailSpinner = (Spinner) findViewById(R.id.email_spinner);
        addressSpinner = (Spinner) findViewById(R.id.address_spinner);

        nameSupplier = (EditText) findViewById(R.id.sup_add_new_name);
        companySupplier = (EditText) findViewById(R.id.supRole);
        addressSupplier = (EditText) findViewById(R.id.supAddress);
        emailSupplier = (EditText) findViewById(R.id.sup_add_new_email);
        phoneNumberSup = (EditText) findViewById(R.id.supPhone);
        commentsSupplier = (EditText) findViewById(R.id.supComments);

        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        saveBtn = (Button) findViewById(R.id.save_btn);

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

        isKeySupplier = getIntent().getStringExtra("EXTRA_KEY_ID");
        if (isKeySupplier != null) {

            FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Supplier").child(isKeySupplier).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Supplier supplier = dataSnapshot.getValue(Supplier.class);
                    nameSupplier.setText(supplier.getName());
                    phoneNumberSup.setText(supplier.getPhoneNumber());
                    addressSupplier.setText(supplier.getAddress());
                    companySupplier.setText(supplier.getCompany());
                    emailSupplier.setText(supplier.getEmail());
                    commentsSupplier.setText(supplier.getComments());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSuppliers();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSupplierInFirebase();
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

    public void saveSupplierInFirebase() {

        String name = nameSupplier.getText().toString();
        String phone = phoneNumberSup.getText().toString();
        if (name.equals("") || phone.equals("")) {
            Toast.makeText(this, "חובה להזין שם פרטי ומספר טלפון של הספק", Toast.LENGTH_SHORT).show();
        } else {
            String address = addressSupplier.getText().toString();
            String company = companySupplier.getText().toString();
            String email = emailSupplier.getText().toString();
            String comments = commentsSupplier.getText().toString();

            //Creating Supplier object
            final Supplier supplier = new Supplier(name, company, address, email, phone, comments);


            if (isKeySupplier == null) {
                //Storing values to firebase
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Supplier").push().setValue(supplier);
            } else {
                //if user want edit details of supplier - save the data that he change
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Supplier").child(isKeySupplier).setValue(supplier);
            }
            moveToSuppliers();
        }
    }

    public void moveToSuppliers() {
        Intent i = new Intent(AddSupplier.this, Suppliers.class);
        startActivity(i);
    }

}
