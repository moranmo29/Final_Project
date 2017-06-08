package com.example.user.myd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by User on 14/03/2017.
 */

public class SupplierArrayAdapter extends ArrayAdapter<Supplier> {

    private final Context context;
    private final Supplier[] values;
    private final ArrayAdapter adapter;


    public SupplierArrayAdapter(Context context, Supplier[] values) {
        super(context, R.layout.row_supplier_item, values);
        this.context = context;
        this.values = values;
        this.adapter = this;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_supplier_item, parent, false);
        TextView textNameSupplier = (TextView) rowView.findViewById(R.id.tv_name);
        TextView textCompanySupplier = (TextView) rowView.findViewById(R.id.tv_role);
        TextView textAddressSupplier = (TextView) rowView.findViewById(R.id.tv_address);
        TextView textCommentSupplier = (TextView) rowView.findViewById(R.id.tv_comment);

        ImageButton editSupplier = (ImageButton) rowView.findViewById(R.id.btn_edit);
        ImageButton deleteSupplier = (ImageButton) rowView.findViewById(R.id.btn_delete);
        ImageButton callNumber = (ImageButton) rowView.findViewById(R.id.btn_call);
        ImageButton sendMail = (ImageButton) rowView.findViewById(R.id.btn_mail);


        textNameSupplier.setText(values[position].getName());
        textCompanySupplier.setText("חברה: " + values[position].getCompany());
        textAddressSupplier.setText("כתובת: " + values[position].getAddress());
        textCommentSupplier.setText("הערה: " + values[position].getComments());

        //user can call to the correct supplier
        callNumber.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + values[position].getPhoneNumber()));

                if (ActivityCompat.checkSelfPermission(context,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                view.getContext().startActivity(callIntent);
            }
        });

        //user can send mail to the correct supplier
        sendMail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{values[position].getEmail()});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "body of email");
                try {
                    view.getContext().startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //user can delete supplier from the list
        deleteSupplier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.delete_title)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                //String supplierId = mDatabase.child("users").child(mUserId).child("Supplier").child(SupplierArrayAdapter.getItem(position).getRefKey());
                                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Supplier").child(values[position].getKey()).removeValue();
                                //Log.e("Key Value ", "" + values[position].getKey());
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

        //user can delete supplier from the list
        deleteSupplier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.delete_title)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                //String supplierId = mDatabase.child("users").child(mUserId).child("Supplier").child(SupplierArrayAdapter.getItem(position).getRefKey());
                                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Supplier").child(values[position].getKey()).removeValue();
                                //Log.e("Key Value ", "" + values[position].getKey());
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
        editSupplier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), AddSupplier.class);
                intent.putExtra("EXTRA_KEY_ID", values[position].getKey());
                view.getContext().startActivity(intent);

            }
        });

        return rowView;
    }


}
