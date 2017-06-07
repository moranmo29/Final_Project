package com.example.user.myd;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
 * LoginActivity.java
 * This class offers login via email and password
 * using Firebase authentication
 * */
public class LoginActivity extends AppCompatActivity {

    //views
    private EditText inputEmail, inputPassword;
    private Button btnLogin, btnReset, reg;

    private FirebaseAuth auth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init layout
        setContentView(R.layout.activity_login);

        //Initialize, get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mFirebaseUser = auth.getCurrentUser();

        //Checking user session
        if (mFirebaseUser != null) {
            startActivity(new Intent(LoginActivity.this, MainMenu.class));
            finish();
        }

        //init views
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        reg = (Button) findViewById(R.id.register_btn);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // ---------------------------- eventListeners ------------------------------
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                // Show error message if email is empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), R.string.validation_mail, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Show error message if password is empty
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), R.string.validation_password, Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //If sign-in fails: display a message to the user.
                                if (!task.isSuccessful()) {
                                    // Show error message if password is too short
                                    if (password.length() < 6) {
                                        Toast.makeText(LoginActivity.this, R.string.error_password_short, Toast.LENGTH_SHORT).show();
                                    } else if (!activeNetwork()) {
                                        // Show error message if user not connected to the internet
                                        Toast.makeText(LoginActivity.this, getString(R.string.connection_failed), Toast.LENGTH_LONG).show();
                                    }
                                    // Show error message if the auth failed (wrong input, check sign-up)
                                    else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                    //If sign in succeeds
                                } else {
                                    //go to menu activity
                                    Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

        //go to registration screen
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveReg();
            }
        });
    }

    //This method go to registration activity
    private void moveReg() {
        Intent i = new Intent(LoginActivity.this, Registration.class);
        startActivity(i);
    }

    //This method check if there is connection to the internet
    private boolean activeNetwork() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return con.getActiveNetworkInfo() != null && con.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}

