package com.example.user.myd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by User on 28/03/2017.
 */

public class PriceArrayAdapter extends ArrayAdapter<PriceList> {

    private final Context context;
    private final PriceList[] values;
    private final ArrayAdapter adapter;

    public PriceArrayAdapter(Context context, PriceList[] values) {
        super(context, R.layout.row_price_item, values);
        this.context = context;
        this.values = values;
        this.adapter = this;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_price_item, parent, false);
        TextView textNameProduct = (TextView) rowView.findViewById(R.id.tv_product_name);
        TextView textMinQuantity = (TextView) rowView.findViewById(R.id.tv_qMin);
        TextView textComment = (TextView) rowView.findViewById(R.id.tv_comments);
        TextView textPriceUnit = (TextView) rowView.findViewById(R.id.tv_priceUnit);

        ImageButton editPrice = (ImageButton) rowView.findViewById(R.id.btn_edit);
        ImageButton deletePrice = (ImageButton) rowView.findViewById(R.id.btn_delete);

        textNameProduct.setText(values[position].getProductName());
        textMinQuantity.setText("כמות מינמלית: " + values[position].getqMin());
        textComment.setText("הערה: " + values[position].getPriceComments());
        textPriceUnit.setText(""+values[position].getPriceUnit());

        //user can delete price from the list
        deletePrice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.delete_title)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Price").child(values[position].getKey()).removeValue();
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

        //user can edit price from the list
        editPrice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddPrice.class);
                intent.putExtra("EXTRA_KEY_ID", values[position].getKey());
                view.getContext().startActivity(intent);
            }
        });
        return rowView;
    }
}
