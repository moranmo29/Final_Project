package com.example.user.myd;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by moran on 08/03/2017.
 */

public class FirebaseDBHelper {

    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private static String mUserId;


    public FirebaseDBHelper() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();
    }

    //Singeltone
    private static class SingeltonFirebaseHandler {
        private static final FirebaseDBHelper SHOW = new FirebaseDBHelper();
    }
    public static FirebaseDBHelper getSHOW(String mUserIdGet)
    {
        mUserId = mUserIdGet;
        return SingeltonFirebaseHandler.SHOW;
    }

    public void insertSupplier(Supplier supplier){
        //Storing values to firebase
        mDatabase.child("users").child(mUserId).child("Supplier").push().setValue(supplier);
    }

    public void insertCosts(Cost cost){
        //Storing values to firebase
        mDatabase.child("users").child(mUserId).child("Costs").push().setValue(cost);
    }



}
