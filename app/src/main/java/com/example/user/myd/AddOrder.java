package com.example.user.myd;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;


public class AddOrder extends AppCompatActivity {

    Button cancelBtnOrder;
    EditText descOrder, qOrder;
    Button btnSave, btnAdd, btnSub;
    private int orderYear, orderMonth, orderDay;
    static final int DATE_DIALOG_ID = 0;
    private Button btnDate;
    private String isKeyOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        cancelBtnOrder = (Button) findViewById(R.id.cancel_btn);
        descOrder = (EditText) findViewById(R.id.add_order_description);
        qOrder = (EditText) findViewById(R.id.costsQuantity);

        btnAdd = (Button) findViewById(R.id.button_plus);
        btnSub = (Button) findViewById(R.id.button_minus);
        btnSave = (Button) findViewById(R.id.save_btn);

        final Calendar cal = Calendar.getInstance();
        orderYear = cal.get(Calendar.YEAR);
        orderMonth = cal.get(Calendar.MONTH);
        orderDay = cal.get(Calendar.DAY_OF_MONTH);

        //date dialog
        showDialogOnButtonClick();

        isKeyOrder = getIntent().getStringExtra("EXTRA_KEY_ID");
        if (isKeyOrder != null) {

            FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Orders").child(isKeyOrder).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Order order = dataSnapshot.getValue(Order.class);
                    try {
                        descOrder.setText(order.getOrderDesc());
                        qOrder.setText("" + order.getqUnitsOrder());
                        btnDate.setText("" + order.getDateOrder()); //
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


        cancelBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToOrders();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOrderInFirebase();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusQuantityClicked();
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusQuantityClicked();
            }
        });

    }


    public void showDialogOnButtonClick() {
        btnDate = (Button) findViewById(R.id.btn_date);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, orderYear, orderMonth, orderDay);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            orderYear = year;
            orderMonth = monthOfYear;
            orderDay = dayOfMonth;
            //btnDate.setTextColor(getResources().getColor(R.color.colorPrimary));
            btnDate.setTextColor(Color.parseColor("#000000"));
            btnDate.setText(orderDay + "/" + (orderMonth + 1) + "/" + orderYear);

        }
    };

    public void saveOrderInFirebase() {

        String description = descOrder.getText().toString().trim();
        int quantity = Integer.parseInt(qOrder.getText().toString().trim());
        String date = btnDate.getText().toString().trim();
        //double priceForUnit = Double.parseDouble(priceUnitCost.getText().toString());
        if (description.equals("") || quantity == 0) {
            //displaying toast - must enter the details
            Toast.makeText(this, "חובה להזין את הפרטים", Toast.LENGTH_SHORT).show();
        } else {
            //Creating Order object
            final Order order = new Order(description, quantity, date);
            //final Cost cost = new Cost(description,priceForUnit, priceForUnit*quantity,quantity);


            if (isKeyOrder == null) {
                //Storing values to firebase
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Orders").push().setValue(order);
            } else {
                //if user want edit details of supplier - save the data that he change
                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Orders").child(isKeyOrder).setValue(order);
            }
            moveToOrders();
        }
    }


    public void moveToOrders() {
        Intent i = new Intent(AddOrder.this, Orders.class);
        startActivity(i);
    }

    //Increases the value by 1 each time a user clicks 'plus button'
    private void plusQuantityClicked() {
        String value = qOrder.getText().toString();
        int finalValue = Integer.parseInt(value) + 1;
        qOrder.setText("" + finalValue);
    }

    //Reduce one value from quantityUnits every time user press on 'minus button'
    private void minusQuantityClicked() {
        String value = qOrder.getText().toString();
        int finalValue = Integer.parseInt(value) - 1;
        //Value must be positive
        if (finalValue <= 0) {
            finalValue = 0;
            Toast.makeText(getApplicationContext(), "הכמות חייבת להיות למעלה מאפס!", Toast.LENGTH_SHORT).show();
        }
        //Display the newly number
        qOrder.setText("" + finalValue);
    }
}
