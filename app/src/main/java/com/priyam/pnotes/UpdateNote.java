package com.priyam.pnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateNote extends AppCompatActivity {

    EditText noteTitleEditText, noteDescriptionEditText;
    String gotTitle, gotDescription;
    TextWatcher textWatcher;
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

    public void saveNote(){
        if(noteDescriptionEditText.getText().toString().equals(gotDescription) && noteTitleEditText.getText().toString().equals(gotTitle)){
            // No changes made
            finish();
        } else{
            // Now update realtime database

        }
    }
}
