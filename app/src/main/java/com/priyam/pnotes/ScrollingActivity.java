package com.priyam.pnotes;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.priyam.pnotes.models.Note;
import com.priyam.pnotes.shared.PrefManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private List<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteAdapter mNoteAdapter;

    // Shared Prefernces
    PrefManager prefManager;
    String signedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        findViewById(R.id.activity_scrolling_progress).setVisibility(View.VISIBLE);
        // Pref manager to manage shared preferences
        prefManager = new PrefManager(this);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Firebase database first get the account signed in
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        signedInUser = account.getEmail();
        int len = signedInUser.length();
        signedInUser = signedInUser.substring(0,len-10);  //getting only the email name
        Log.d("Siugned In user", signedInUser);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(signedInUser);

//        Note note = new Note("First note","description","date");
//        noteList.add(note);
//
//        note = new Note("Second ONe","description","date");
//        noteList.add(note);

        // For adding the notelist to the screen
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_note_list);
        mNoteAdapter = new NoteAdapter(noteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mNoteAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Right now it updates all
                //Log.d("Count",""+dataSnapshot.getChildrenCount());
                if(dataSnapshot.getChildrenCount()==0){
                    // New user
                    findViewById(R.id.welcome_message).setVisibility(View.VISIBLE);
                    findViewById(R.id.activity_scrolling_progress).setVisibility(View.GONE);
                } else{
                    noteList.clear();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Note post = postSnapshot.getValue(Note.class);
                        //Log.d("Priyam Error",post.getTitle());
                        noteList.add(post);
                    }
                    findViewById(R.id.activity_scrolling_progress).setVisibility(View.GONE);
                    recyclerView.setAdapter(mNoteAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
                findViewById(R.id.activity_scrolling_progress).setVisibility(View.GONE);
                Toast.makeText(ScrollingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

//        note = new Note("Hello Bye","descripton","date");
//        noteList.add(note);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        Log.d("Priyam Start","log out now");

        // Update shared preferences
        prefManager.setIsLogin(false);
        prefManager.setEmail("");

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Now sign out
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(getApplicationContext(),SignInActivity.class);
                        startActivity(i);
                        finish(); // Complete
                    }
                });
    }
}
