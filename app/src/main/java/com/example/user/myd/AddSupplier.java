package com.example.user.myd;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AddSupplier extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner phoneSpinner, emailSpinner, addressSpinner;
    Button cancelBtn, saveBtn;
    EditText nameSupplier, companySupplier, addressSupplier, emailSupplier, phoneNumberSup, commentsSupplier;

    //////
    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private List<Supplier> itemSupplier;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);

        ////
        Firebase.setAndroidContext(this);
        ////
        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();
        itemSupplier = new ArrayList<Supplier>();

        phoneSpinner = (Spinner) findViewById(R.id.phone_spinner);
        emailSpinner = (Spinner) findViewById(R.id.email_spinner);
        addressSpinner = (Spinner) findViewById(R.id.address_spinner);

        //
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

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSupplier();
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

    public void moveToSupplier() {
        Intent i = new Intent(AddSupplier.this, Suppliers.class);
        startActivity(i);
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

            //Creating firebase object
            Firebase ref = new Firebase("https://myd-product-manageement-app.firebaseio.com/");

            //Adding values
            supplier.setName(name);
            supplier.setPhoneNumber(phone);
            supplier.setCompany(company);
            supplier.setAddress(address);
            supplier.setEmail(email);
            supplier.setComments(comments);

            //Storing values to firebase
            mDatabase.child("users").child(mUserId).child("Supplier").push().setValue(supplier);

            Intent i = new Intent(this, Suppliers.class);
            startActivity(i);

        }
    }


}
