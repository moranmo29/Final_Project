package com.example.user.myd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;


/**
 * Created by User on 14/03/2017.
 */

public class SupplierArrayAdapter extends ArrayAdapter<Supplier> {

    private final Context context;
    private final Supplier[] values;
    private final ArrayAdapter adapter;
    private CharSequence options[] = new CharSequence[] {"ערוך פרטי ספק", "מחק ספק זה"};


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
        Button showName = (Button) rowView.findViewById(R.id.tv_name); // display name supplier on button
        TextView textCompanySupplier = (TextView) rowView.findViewById(R.id.tv_role);
        TextView textAddressSupplier = (TextView) rowView.findViewById(R.id.tv_address);
        TextView textCommentSupplier = (TextView) rowView.findViewById(R.id.tv_comment);

        ImageButton callNumber = (ImageButton) rowView.findViewById(R.id.btn_call);
        ImageButton sendMail = (ImageButton) rowView.findViewById(R.id.btn_mail);

        textNameSupplier.setText(values[position].getName());
        textCompanySupplier.setText("חברה: " + values[position].getCompany());
        textAddressSupplier.setText("כתובת: " + values[position].getAddress());
        textCommentSupplier.setText("הערה: " + values[position].getComments());

        //user can call to the correct supplier
        callNumber.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.title_call)
                        .setMessage(R.string.msg_call)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                callIntent.setData(Uri.parse("tel:" + values[position].getPhoneNumber()));

                                if (ActivityCompat.checkSelfPermission(context,
                                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                getContext().startActivity(callIntent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
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

        //User can edit or delete from list when he clicked on name button
        showName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(false);
                builder.setTitle(R.string.select_option);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on options[which]
                        switch (which) {
                            case 0: // Edit
                            {
                                //user can edit supplier from the list
                                Intent intent = new Intent(getContext(), AddSupplier.class);
                                //Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("EXTRA_KEY_ID", values[position].getKey());
                                getContext().startActivity(intent);
                            }
                            break;
                            case 1: // Delete
                            {
                                //user can delete supplier from the list
                                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Supplier").child(values[position].getKey()).removeValue();
                                break;
                            }
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //the user clicked on Cancel
                    }
                });
                builder.show();
            }
        });

        return rowView;
    }
}
