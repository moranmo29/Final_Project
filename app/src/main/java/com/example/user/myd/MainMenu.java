package com.example.user.myd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.liuguangqiang.cookie.CookieBar;
import com.liuguangqiang.cookie.OnActionClickListener;

public class MainMenu extends AppCompatActivity {

    GridView gv;
    Context context;
    ArrayList prgmName;
    public static String [] prgmNameList={"ספקים","עלויות","הזמנות","חנויות משווקות","טיפולים","ברקוד","מחירון","מצב מלאי","התנתק"};
    public static int [] prgmImages={R.drawable.suppliers,R.drawable.costs,R.drawable.orders,R.drawable.shops,R.drawable.to_do,R.drawable.barcode,R.drawable.price,R.drawable.status_inv,R.drawable.log_out};
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        gv=(GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //go to activity
                switch(position){
                    case 0:
                        Intent i = new Intent(MainMenu.this,Suppliers.class);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(MainMenu.this,Costs.class);
                        startActivity(i);
                        break;
                    case 2:
                        i = new Intent(MainMenu.this,Orders.class);
                        startActivity(i);
                        break;
                    case 3:
                        i = new Intent(MainMenu.this,Shops.class);
                        startActivity(i);
                        break;
                    case 4:
                        i = new Intent(MainMenu.this,ToDo.class);
                        startActivity(i);
                        break;
                    case 5:
                        i = new Intent(MainMenu.this,Barcode.class);
                        startActivity(i);
                        break;
                    case 6:
                        i = new Intent(MainMenu.this,Price.class);
                        startActivity(i);
                        break;
                    case 7:
                        i = new Intent(MainMenu.this,Status.class);
                        startActivity(i);
                        break;
                    case 8:
                        popUpExit();
                        break;
                }
            }
        });
    }

    // Menu bar -
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_setting:
                // Setting clicked
                moveSetting();
                return true;
            case R.id.action_about:
                // About
              moveAbout();
                return true;
            case R.id.action_exit_app:
                // log-off clicked
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Launching new activity
     * */
    private void moveSetting() {
        Intent i = new Intent(MainMenu.this, Setting.class);
        startActivity(i);
    }

    private void moveAbout(){
        Intent i = new Intent(this, About.class);
        startActivity(i);
    }
    //sign out method
    public void signOut() {
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
    }

    //Popup message to sign-out from the app
    private void popUpExit() {
        new CookieBar.Builder(MainMenu.this)
                .setTitle(R.string.messege_titleExit)
                .setIcon(R.mipmap.ic_launcher)
                .setLayoutGravity(Gravity.BOTTOM)
                .setMessage(R.string.messege_body)
                .setBackgroundColor(R.color.colorRed)
                .setAction(R.string.messege_confirm, new OnActionClickListener() {
                    @Override
                    public void onClick() {
                        signOut();
                    }
                })
                .show();
    }
}
