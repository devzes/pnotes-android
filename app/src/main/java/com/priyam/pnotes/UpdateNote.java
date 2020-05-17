package com.priyam.pnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.Date;

public class UpdateNote extends AppCompatActivity {

    EditText noteTitleEditText, noteDescriptionEditText;
    String gotTitle, gotDescription, gotNoteId, gotUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        // Get layout parameters
        noteTitleEditText = findViewById(R.id.update_note_title);
        noteDescriptionEditText = findViewById(R.id.update_note_descr);

        // Getting values from the last intent
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        gotTitle = bundle.getString("title");
        gotDescription = bundle.getString("description");
        gotNoteId = bundle.getString("noteid");
        gotUser = bundle.getString("user");
        noteTitleEditText.setText(gotTitle);
        noteDescriptionEditText.setText(gotDescription);

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
        if(noteDescriptionEditText.getText().toString().equals(gotDescription) && noteTitleEditText.getText().toString().equals(gotTitle)){
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

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            // delete the note
            DatabaseReference myRef = DatabaseInstance.getDatabase().getReference(gotUser).child(gotNoteId);
            myRef.removeValue();
            myRef.push();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveNote(){
        if(noteDescriptionEditText.getText().toString().equals(gotDescription) && noteTitleEditText.getText().toString().equals(gotTitle)){
            // No changes made
            finish();
        } else{
            // Now update realtime database
            DatabaseReference myRef = DatabaseInstance.getDatabase().getReference(gotUser).child(gotNoteId);
            // Implementing a class with which data persists and same instance can be used!

            myRef.child("title").setValue(noteTitleEditText.getText().toString());
            myRef.child("description").setValue(noteDescriptionEditText.getText().toString());
            myRef.child("timestamp").setValue(ServerValue.TIMESTAMP);  //Adding the timestamp to the note
            //Log.d("Updated:", "check now");
            Date d = new Date();
            CharSequence s  = DateFormat.format("MMM d", d.getTime());
            myRef.child("date").setValue(s); //Set the data when the note was updated

            myRef.push(); // Push it to the database

            Toast.makeText(UpdateNote.this, "Updated Successfully!", Toast.LENGTH_SHORT ).show();
            finish();
        }
    }


}
