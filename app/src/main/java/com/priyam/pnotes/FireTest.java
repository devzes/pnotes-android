package com.priyam.pnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.priyam.pnotes.models.Note;

public class FireTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_test);

        // Write a message to the database
        Note note = new Note();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("sethpriyam1");

        note.setTitle("Hello1");
        note.setDescription("Description");
        note.setDate("May 16, 2019");
        myRef.push().setValue(note);
        Log.d("Priyam Check","Check now....................");


    }
}
