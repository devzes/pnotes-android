package com.priyam.pnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.priyam.pnotes.shared.PrefManager;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 1001;

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hiding the TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar

        setContentView(R.layout.activity_sign_in);
        //Log.d("Priyam Start","Sign in acivity");

        // Initialise shared preferences
        prefManager = new PrefManager(this);


        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);

    }

    private void updateUI(GoogleSignInAccount account) {

        if(account!=null){
            // User is already signed in!
            //Log.d("Account signed in", account.getEmail());
            Intent i = new Intent(SignInActivity.this, ScrollingActivity.class);
            startActivity(i);
            finish(); //Clear the stack
        } else{
            //User is not logged in, see  what to do
            findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.sign_in_button:
                            signIn();
                            break;
                        // ...
                    }
                }
            });
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.

            try {
                //Log.d("Priyam Message: ", account.getEmail());
                prefManager.setIsLogin(true);
                prefManager.setEmail(account.getEmail());

                // Give message for successful sign in
//                Toast.makeText(SignInActivity.this, "Sign In successful!",Toast.LENGTH_SHORT).show();

                // Update UI after successful sign in!
                updateUI(account);

            } catch(Exception e){
                Log.e("Priyam error", Objects.requireNonNull(e.getMessage()));
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("PRIYAM Message", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);

        }
    }
}
