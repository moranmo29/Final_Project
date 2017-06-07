package com.example.user.myd;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
 * Registration.java
 * This class offers sign up via email and password using Firebase authentication.
 * */
public class Registration extends AppCompatActivity {

    //views
    private Button buttonCancelReg, buttonReg;
    private EditText editTextRegPassword, editTextConfirmPassword, editTextRegEmail;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init layout
        setContentView(R.layout.activity_registration);

        //init views
        editTextRegEmail = (EditText) findViewById(R.id.reg_email);
        editTextRegPassword = (EditText) findViewById(R.id.reg_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        buttonReg = (Button) findViewById(R.id.reg_btn);
        buttonCancelReg = (Button) findViewById(R.id.cancel_btn);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // ---------------------------- eventListeners ------------------------------
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextRegEmail.getText().toString().trim();
                String password = editTextRegPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    // Show error message if email is empty
                    Toast.makeText(getApplicationContext(), R.string.validation_mail, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    // Show error message if password is empty
                    Toast.makeText(getApplicationContext(), R.string.validation_password, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    // Show error message if password is too short
                    Toast.makeText(getApplicationContext(), R.string.error_password_short, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editTextConfirmPassword.getText().toString().equals(editTextRegPassword.getText().toString())) {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        if (!activeNetwork()) {
                                            // Show error message if user not connected to the internet
                                            Toast.makeText(Registration.this, getString(R.string.connection_failed), Toast.LENGTH_LONG).show();
                                        } else {
                                            // If sign up fails, display a message to the user.
                                            Toast.makeText(Registration.this, getString(R.string.authentication_failed),
                                                    Toast.LENGTH_LONG).show();
                                            Toast.makeText(Registration.this, "" + task.getException(), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        //If sign up succeeds, display a message to the user and sign in
                                        Toast.makeText(Registration.this, getString(R.string.success_createUserWithEmail), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Registration.this, MainMenu.class));
                                        finish();
                                    }
                                }
                            });
                } else {
                    // Show error message if password and confirm password not equal
                    Toast.makeText(getApplicationContext(), R.string.error_confirm_password, Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancelReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveLogin();
            }
        });

    }

    private void moveLogin() {
        //go to login activity
        Intent i = new Intent(Registration.this, LoginActivity.class);
        startActivity(i);
    }

    //This method check if there is connection to the internet
    private boolean activeNetwork() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return con.getActiveNetworkInfo() != null && con.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
