package com.priyam.pnotes;

import com.google.firebase.database.FirebaseDatabase;

public class DatabaseInstance {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            // get a database instance if not created yet
            mDatabase = FirebaseDatabase.getInstance();

            mDatabase.setPersistenceEnabled(true); //This stores the cache in the device offline so that without internet it can be in the cache
            // This also update and sync automatically when the network is back!
        }
        return mDatabase;
    }
}
