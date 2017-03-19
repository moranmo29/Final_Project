package com.example.user.myd;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 16/03/2017.
 */

public class FirebaseDbHandler {

    public static DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    public static FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
    public static String mUserId = mFirebaseUser.getUid();

}
