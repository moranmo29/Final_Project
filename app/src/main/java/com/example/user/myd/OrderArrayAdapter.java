package com.example.user.myd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by User on 19/03/2017.
 */

public class OrderArrayAdapter extends ArrayAdapter<Order> {

    private final Context context;
    private final Order[] values;
    private final ArrayAdapter adapter;
    private CharSequence options[] = new CharSequence[] {"ערוך פרטי הזמנה", "מחק הזמנה"};


    public OrderArrayAdapter(Context context, Order[] values) {
        super(context, R.layout.row_order_item, values);
        this.context = context;
        this.values = values;
        this.adapter = this;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_order_item, parent, false);
        TextView textDescriptionOrder = (TextView) rowView.findViewById(R.id.tv_orderDescription);
        TextView textUnitsOrder = (TextView) rowView.findViewById(R.id.tv_orderQuan);
        TextView textDateOrder = (TextView) rowView.findViewById(R.id.tv_orderDate);
        Button setAlarm = (Button) rowView.findViewById(R.id.alertImg);
        ImageButton editOrder = (ImageButton) rowView.findViewById(R.id.btn_edit);
        ImageButton deleteOrder = (ImageButton) rowView.findViewById(R.id.btn_delete);


        textDescriptionOrder.setText(values[position].getOrderDesc());
        textUnitsOrder.setText("כמות: " + values[position].getqUnitsOrder());
        textDateOrder.setText("ספק: " +values[position].getSelectedSupplier() + "\nהגעת הזמנה: " + values[position].getDateOrder());

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), OrderSetAlarm.class);
                view.getContext().startActivity(i);
            }
        });

        deleteOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.delete_title)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Orders").child(values[position].getKey()).removeValue();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        //user can edit order from the list
        editOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddOrder.class);
                intent.putExtra("EXTRA_KEY_ID", values[position].getKey());
                view.getContext().startActivity(intent);
            }
        });
        return rowView;
    }
}
