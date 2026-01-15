package com.katza.calmind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.firebase.auth.*;

public class loginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth auth;
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("הדבק_כאן_את_הערך_שהעתקת")
            .requestEmail()
            .requestScopes(new Scope("https://www.googleapis.com/auth/calendar.events"))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

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
                // 1. קבלת החשבון מתוך ה-Intent
                GoogleSignInAccount acc = GoogleSignIn.getSignedInAccountFromIntent(data)
                        .getResult(ApiException.class);

                // 2. בדיקה שה-Token אינו ריק (מניעת קריסה)
                if (acc != null && acc.getIdToken() != null) {

                    AuthCredential cred = GoogleAuthProvider.getCredential(acc.getIdToken(), null);

                    // 3. התחברות ל-Firebase
                    auth.signInWithCredential(cred)
                            .addOnSuccessListener(r -> {
                                Intent i = new Intent(this, homeActivity.class);
                                i.putExtra("name", acc.getDisplayName());
                                startActivity(i);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Log.e("LoginError", "Firebase Auth failed", e);
                                Toast.makeText(this, "אימות מול Firebase נכשל", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Log.e("LoginError", "ID Token is null. Check Web Client ID in Google Console.");
                    Toast.makeText(this, "שגיאה בקבלת מזהה מגוגל", Toast.LENGTH_SHORT).show();
                }

            } catch (ApiException e) {
                // הדפסת קוד השגיאה המדויק (למשל 10 או 12500)
                Log.e("LoginError", "Google sign in failed, code: " + e.getStatusCode());
                Toast.makeText(this, "התחברות לגוגל נכשלה: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    }
