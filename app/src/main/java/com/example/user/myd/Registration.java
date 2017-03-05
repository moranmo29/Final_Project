package com.example.user.myd;

import android.content.Intent;
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

public class Registration extends AppCompatActivity {

    Button buttonCancelReg;
    EditText editTextRegPassword;
    EditText editTextConfirmPassword;
    EditText editTextRegEmail;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextRegEmail = (EditText) findViewById(R.id.reg_email);
        editTextRegPassword = (EditText) findViewById(R.id.reg_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        Button buttonReg = (Button) findViewById(R.id.reg_btn);
        buttonCancelReg = (Button) findViewById(R.id.cancel_btn);
        auth = FirebaseAuth.getInstance();


        buttonCancelReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveFirstScreen();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextRegEmail.getText().toString().trim();
                String password = editTextRegPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editTextConfirmPassword.getText().toString().equals(editTextRegPassword.getText().toString())) {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(Registration.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_LONG).show();
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Registration.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        startActivity(new Intent(Registration.this, MainMenu.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void moveFirstScreen() {
        Intent i = new Intent(Registration.this, LoginActivity.class);
        startActivity(i);
    }
}
