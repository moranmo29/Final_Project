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

public class AddPriceCheck extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner priceTypeCoin;
    Button backBtn, checkBtn, btnPlus, btnMinus;;
    EditText totalCostUnit, customerPrice, quantity;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price_check);

        priceTypeCoin = (Spinner)findViewById(R.id.price_unit_spinner);
        backBtn = (Button)findViewById(R.id.back_btn);

        checkBtn = (Button)findViewById(R.id.add_price_check_btn);
        btnPlus = (Button) findViewById(R.id.button_plus);
        btnMinus = (Button) findViewById(R.id.button_minus);
        totalCostUnit = (EditText)findViewById(R.id.add_total_cost_product);
        customerPrice = (EditText)findViewById(R.id.addPriceCostToCustomer);
        quantity = (EditText)findViewById(R.id.priceMinimumQuantity);
        info = (TextView)findViewById(R.id.info);


           /*Manage the data - the adapter will put data inside the spinner
        android.R.layout.simple_spinner_item contain a TextView that is repeated to form the List structure  */
        ArrayAdapter adapterTypeCoin = ArrayAdapter.createFromResource(this, R.array.priceTypeCoinSpinner, android.R.layout.simple_spinner_item);


        priceTypeCoin.setAdapter(adapterTypeCoin);
        priceTypeCoin.setOnItemSelectedListener(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToMenu();
            }
        });
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double calc = 0;
                double unit,priceCustomer;
                try {
                    unit = Double.parseDouble(totalCostUnit.getText().toString().trim());
                    priceCustomer = Double.parseDouble(customerPrice.getText().toString().trim());
                } catch (NumberFormatException e) {
                    //default values
                    unit = 0;
                    priceCustomer = 0;
                }
                if(priceCustomer < unit)
                {
                    Toast.makeText(getBaseContext(), "עסקה זו יוצאת בהפסד" , Toast.LENGTH_SHORT).show();
                }
                int quantityPur = Integer.parseInt(quantity.getText().toString().trim());
                try {
                    calc = Double.parseDouble(String.format("%.5f", quantityPur*(priceCustomer-unit)));
                } catch (NumberFormatException e) {
                    calc = 0;
                }
                //Creating PriceCheck object
                //final PriceCheck p = new PriceCheck(unit, priceCustomer, quantityPur);
                info.setText("הרווח שלך מהעסקה: " + "\n" + calc);
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

    public void moveToMenu(){
        Intent i = new Intent(AddPriceCheck.this, MainMenu.class);
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
