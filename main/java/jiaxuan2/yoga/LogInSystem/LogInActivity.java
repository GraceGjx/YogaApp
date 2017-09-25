package jiaxuan2.yoga.LogInSystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jiaxuan2.yoga.MainFunctions.MainActivity;
import jiaxuan2.yoga.R;

public class LogInActivity extends AppCompatActivity {
    public static final String LOGIN_USER = "EMAIL";
    private static final String TAG = "LogInEmail";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmail;
    private EditText mPassword;

    private LogInSystemHelper lHelper = new LogInSystemHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    //User sign in
                    Log.d(TAG, "user signed in: " + user.getUid());
                } else {
                    //User is signed out
                    Log.d(TAG, "user signed_out");
                }
            }
        };

        //Getting all edit field
        mEmail = (EditText) findViewById(R.id.EmailText);
        mPassword = (EditText) findViewById(R.id.PasswordText);

        //Update on button
        findViewById(R.id.SignUpbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
        findViewById(R.id.LogInbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(mEmail, mPassword);

            }
        });

    }



    @Override
    public void onBackPressed() {
    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



    /**
     * Method for signing a exist user
     *
     * @param email    String representation of email
     * @param password String representation of password
     */
    private void signIn(final EditText email, EditText password) {
        Log.d(TAG, "signInAccount: " + email);
        if (!lHelper.validateLogIn(email, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email.getText().toString(),
                password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "sign in user status: " + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Intent logInIntent = new Intent(LogInActivity.this, MainActivity.class);
                            logInIntent.putExtra(LOGIN_USER,
                                    mAuth.getCurrentUser().getDisplayName());
                            startActivity(logInIntent);
                        } else{
                            Toast.makeText(LogInActivity.this, "Sign in failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}

