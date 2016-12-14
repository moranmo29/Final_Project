package com.moran.myd;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    GridView gv;
    Context context;
    ArrayList prgmName;
    public static String[] prgmNameList = {"ספקים", "עלויות", "הזמנות", "חנויות משווקות", "טיפולים", "ברקוד", "מחירון", "מצב מלאי", "התנתק"};
    public static int[] prgmImages = {R.drawable.suppliers, R.drawable.costs, R.drawable.orders, R.drawable.shops, R.drawable.to_do, R.drawable.barcode, R.drawable.price, R.drawable.status_inv, R.drawable.log_out};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        gv = (GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new CustomAdapter(this, prgmNameList, prgmImages));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //go to activity
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                }
            }
        });
    }
}
