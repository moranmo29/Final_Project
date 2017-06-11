package com.example.user.myd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by User on 26/03/2017.
 */

public class BarcodeArrayAdapter extends ArrayAdapter<BarcodeNumber> {

    private final Context context;
    private final BarcodeNumber[] values;
    private final ArrayAdapter adapter;

    public BarcodeArrayAdapter(Context context, BarcodeNumber[] values) {
        super(context, R.layout.row_barcode_item, values);
        this.context = context;
        this.values = values;
        this.adapter = this;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_barcode_item, parent, false);
        TextView textDescriptionBarcode = (TextView) rowView.findViewById(R.id.tv_product_name);
        TextView textNumberBarcode = (TextView) rowView.findViewById(R.id.tv_barcode);
        TextView textStatus = (TextView) rowView.findViewById(R.id.tv_barcode_status);
        Switch textInvite = (Switch) rowView.findViewById(R.id.switch_barcode_invite);

        ImageButton editBarcode = (ImageButton) rowView.findViewById(R.id.btn_edit);
        ImageButton deleteBarcode = (ImageButton) rowView.findViewById(R.id.btn_delete);

        textDescriptionBarcode.setText("שם: " + values[position].getBarcodeDesc());
        textNumberBarcode.setText("מספר ברקוד: " + values[position].getBarcodeNum());
        textStatus.setText("" + values[position].getqUnitsBarcode());

        //user can delete order from the list
        //set the switch to ON
        textInvite.setChecked(false);
        textInvite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // go to suppliers activity for invite from them
                if (isChecked) {
                    Intent intent = new Intent(buttonView.getContext(), Suppliers.class);
                    buttonView.getContext().startActivity(intent);
                } else {
                    //ToDo
                }
            }
        });

        //user can delete barcode from the list
        deleteBarcode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.delete_title)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Barcode").child(values[position].getKey()).removeValue();
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

        //user can edit barcode from the list
        editBarcode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddBarcode.class);
                intent.putExtra("EXTRA_KEY_ID", values[position].getKey());
                view.getContext().startActivity(intent);
            }
        });
        return rowView;
    }
}
