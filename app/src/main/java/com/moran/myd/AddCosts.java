package com.moran.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddCosts extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner typeCoin;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_costs);

        typeCoin = (Spinner)findViewById(R.id.costs_type_coin_spinner);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);


         /*Manage the data - the adapter will put data inside the spinner
        android.R.layout.simple_spinner_item contain a TextView that is repeated to form the List structure  */
        ArrayAdapter adapterTypeCoin = ArrayAdapter.createFromResource(this, R.array.typeCoinSpinner, android.R.layout.simple_spinner_item);


        typeCoin.setAdapter(adapterTypeCoin);
        typeCoin.setOnItemSelectedListener(this);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToCosts();
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

    public void moveToCosts(){
        Intent i = new Intent(AddCosts.this, Costs.class);
        startActivity(i);
    }
}
