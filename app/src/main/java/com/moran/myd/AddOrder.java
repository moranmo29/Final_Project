package com.moran.myd;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;


public class AddOrder extends AppCompatActivity {

    Button cancelBtnOrder;
    private int orderYear, orderMonth, orderDay;
    static final int DATE_DIALOG_ID = 0;
    private Button btnDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        cancelBtnOrder = (Button) findViewById(R.id.cancel_btn);

        final Calendar cal = Calendar.getInstance();
        orderYear = cal.get(Calendar.YEAR);
        orderMonth = cal.get(Calendar.MONTH);
        orderDay = cal.get(Calendar.DAY_OF_MONTH);

        //date dialog
        showDialogOnButtonClick();


        cancelBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToOrders();
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

    public void moveToOrders(){
        Intent i = new Intent(AddOrder.this, Orders.class);
        startActivity(i);
    }

}
