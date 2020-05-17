package com.priyam.pnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.priyam.pnotes.models.Note;
import com.priyam.pnotes.models.NoteNew;

import java.util.Date;

public class AddNote extends AppCompatActivity {

    EditText noteTitleEditText, noteDescriptionEditText;
    String signedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        // Get the current signedin user
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        signedInUser = account.getEmail();
        int len = signedInUser.length();
        signedInUser = signedInUser.substring(0,len-10);  //getting only the email name

        // Get layout parameters
        noteTitleEditText = findViewById(R.id.update_note_title);
        noteDescriptionEditText = findViewById(R.id.update_note_descr);

        // Update button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_update);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();

            }
        });

    }


    @Override
    public void onBackPressed(){
        if(noteDescriptionEditText.getText().toString().trim().equals("") && noteTitleEditText.getText().toString().trim().equals("")){
            // No changes made
            finish();
        } else{
            // Show warning before quit as unsaved changes
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Changed not saved!");
            builder.setMessage("Do you want to save the note?");
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(UpdateNote.this,"Save this!", Toast.LENGTH_SHORT).show();
                    saveNote();
                    finish();
                }
            });

            builder.setNegativeButton("Don't Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create().show();
        }

    }

    private void saveNote(){

        if(noteDescriptionEditText.getText().toString().trim().equals("") && noteTitleEditText.getText().toString().trim().equals("")){
            // No changes made
            finish();
        } else {

            // Now update realtime database
            DatabaseReference myRef = DatabaseInstance.getDatabase().getReference(signedInUser);
            // Implementing a class with which data persists and same instance can be used!

            NoteNew note = new NoteNew();
            note.setTitle(noteTitleEditText.getText().toString());
            note.setDescription(noteDescriptionEditText.getText().toString());
            note.setTimestamp(ServerValue.TIMESTAMP);  //Adding the timestamp to the note (This adds long value, and
            //Log.d("Updated:", "check now");
            Date d = new Date();
            CharSequence s  = DateFormat.format("MMM d", d.getTime());
            note.setDate(s.toString()); //Set the data when the note was updated

            myRef.push().setValue(note); // Push it to the database

            Toast.makeText(AddNote.this, "Added Note Successfully!", Toast.LENGTH_SHORT ).show();
            finish();

        }

    }
}
