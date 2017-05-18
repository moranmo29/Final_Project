package com.example.user.myd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by User on 16/03/2017.
 */

public class CostArrayAdapter extends ArrayAdapter<Cost> {

    private final Context context;
    private final Cost[] values;
    private final ArrayAdapter adapter;

    public CostArrayAdapter(Context context, Cost[] values) {
        super(context, R.layout.row_cost_item, values);
        this.context = context;
        this.values = values;
        this.adapter = this;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_cost_item, parent, false);
        TextView textDescriptionCost = (TextView) rowView.findViewById(R.id.tv_description);
        TextView textPriceUnitCost = (TextView) rowView.findViewById(R.id.tv_price);
        TextView textQuantityUnit = (TextView) rowView.findViewById(R.id.tv_quantity);
        TextView textPriceTotalCost = (TextView) rowView.findViewById(R.id.tv_result_price);

        ImageButton editCost = (ImageButton) rowView.findViewById(R.id.btn_edit);
        ImageButton deleteCost = (ImageButton) rowView.findViewById(R.id.btn_delete);

        textDescriptionCost.setText(values[position].getDescription());
        textPriceUnitCost.setText("מחיר יחידה: " + values[position].getPriceUnit());
        textQuantityUnit.setText("כמות: " + values[position].getQuantityUnits());
        textPriceTotalCost.setText(""+values[position].getPriceTotal());

        //user can delete cost from the list
        deleteCost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.delete_title)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Costs").child(values[position].getKey()).removeValue();
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

        //user can edit supplier from the list
        editCost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), AddCosts.class);
                intent.putExtra("EXTRA_KEY_ID", values[position].getKey());
                view.getContext().startActivity(intent);
            }
        });
        return rowView;
    }
}
