package com.example.user.myd;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/*
 * ResetPasswordActivity.java
 * This class offers to reset password in case user need via email.
 * */
public class ResetPasswordActivity extends AppCompatActivity {
    //views
    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init layout
        setContentView(R.layout.activity_reset_password);

        //init views
        inputEmail = (EditText) findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // ---------------------------- eventListeners ------------------------------
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                // Show error message if email is empty
                if (TextUtils.isEmpty(email)) {
                    //insert registered email
                    Toast.makeText(getApplication(), getString(R.string.enter_registered_email_id), Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Show message send instructions to reset password
                                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.send_instructions), Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!activeNetwork()) {
                                        // Show error message if user not connected to the internet
                                        Toast.makeText(ResetPasswordActivity.this, getString(R.string.connection_failed), Toast.LENGTH_LONG).show();
                                    } else {
                                        //Show message failed to send reset email
                                        Toast.makeText(ResetPasswordActivity.this, getString(R.string.send_failed), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }

    //This method check if there is connection to the internet
    private boolean activeNetwork() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return con.getActiveNetworkInfo() != null && con.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
