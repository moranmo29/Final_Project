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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ShopArrayAdapter extends ArrayAdapter<Shop> {

    private final Context context;
    private final Shop[] values;
    private final ArrayAdapter adapter;


    //Constructor
    public ShopArrayAdapter(Context context, Shop[] values) {
        super(context, R.layout.row_shop_item, values);
        this.context = context;
        this.values = values;
        this.adapter = this;
    }

    //Get a View that displays the data at the specified position in the data set
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //LayoutInflater is used to create a new View object from row_shop_item.xml layout.
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_shop_item, parent, false);
        TextView textNameShop = (TextView)rowView.findViewById(R.id.tv_shop_name);
        TextView textContactManShop = (TextView)rowView.findViewById(R.id.tv_contact);
        TextView textAddressShop = (TextView)rowView.findViewById(R.id.tv_shop_address);
        TextView textEmailShop = (TextView)rowView.findViewById(R.id.tv_mail);
        TextView textQuantityShop = (TextView)rowView.findViewById(R.id.tv_shop_quantity);
        TextView textPhoneShop = (TextView)rowView.findViewById(R.id.btnDialing);

        Button editShop = (Button)rowView.findViewById(R.id.btnEdit);
        Button deleteShop = (Button)rowView.findViewById(R.id.btnDelete);
        Button shareShop =(Button)rowView.findViewById(R.id.btnShare);
        Button dialNumber = (Button)rowView.findViewById(R.id.btnDialing);
        // set the color of the button dial
        dialNumber.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
        //set background drawable of a view, shape dial number button
        dialNumber.setBackgroundResource(R.drawable.button_dial_shape);

        textNameShop.setText(values[position].getShopName());
        textContactManShop.setText("איש קשר: " + values[position].getContactMan());
        textAddressShop.setText("כתובת: " + values[position].getShopAddress()); //.substring(0,3) + "..."
        textEmailShop.setText(values[position].getShopEmail());
        textQuantityShop.setText(values[position].getqSold()+" נמכרו");
        textPhoneShop.setText(values[position].getShopNumberPhone());

        //user can share store with his contacts
        shareShop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //sharing implementation here
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Myd app - אפליקציה לניהול ושיווק מוצר");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"המוצר שלי נמכר ב-\n" + "שם החנות: " + values[position].getShopName() + "\nכתובת חנות: " + values[position].getShopAddress());
                v.getContext().startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        //user can call the store
        dialNumber.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try{
                    String uri = "tel:" + values[position].getShopNumberPhone();
                    Intent dialIntent  = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
                    view.getContext().startActivity(dialIntent );
                }
               catch (Exception e)
               {
                   Toast.makeText(getApplicationContext(),"Your call has failed...",
                           Toast.LENGTH_LONG).show();
                   e.printStackTrace();
               }
                /**if (ActivityCompat.checkSelfPermission(context,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }*/
            }
        });

        //user can remove shop from the list
        deleteShop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.delete_title)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("Shops").child(values[position].getKey()).removeValue();
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

        //user can edit details from the list
        editShop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddShop.class);
                intent.putExtra("EXTRA_KEY_ID", values[position].getKey());
                view.getContext().startActivity(intent);
            }
        });
        return rowView;
    }

}

