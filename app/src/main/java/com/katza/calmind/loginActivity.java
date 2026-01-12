package com.katza.calmind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.*;

public class loginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestScopes(new com.google.android.gms.common.api.Scope(
                        "https://www.googleapis.com/auth/calendar"))
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton btn = findViewById(R.id.btnGoogleSignIn);
        btn.setOnClickListener(v ->
                startActivityForResult(googleSignInClient.getSignInIntent(), RC_SIGN_IN)
        );
    }

    @Override
    protected void onActivityResult(int code, int result, Intent data) {
        super.onActivityResult(code, result, data);

        if (code == RC_SIGN_IN) {
            try {
                GoogleSignInAccount acc =
                        GoogleSignIn.getSignedInAccountFromIntent(data)
                                .getResult(ApiException.class);

                AuthCredential cred =
                        GoogleAuthProvider.getCredential(acc.getIdToken(), null);

                auth.signInWithCredential(cred).addOnSuccessListener(r -> {
                    Intent i = new Intent(this, HomeActivity.class);
                    i.putExtra("name", acc.getDisplayName());
                    startActivity(i);
                    finish();
                });

            } catch (Exception e) {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
