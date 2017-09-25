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

public class SignUpActivity extends AppCompatActivity {
    public static final String SIGN_UP_USER = "Email";
    public static final String TAG = "SignUpEmail";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;

    private LogInSystemHelper lHelper = new LogInSystemHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
        mName = (EditText) findViewById(R.id.nameEditText);
        mEmail = (EditText) findViewById(R.id.emailEditText);
        mPassword = (EditText) findViewById(R.id.passwordEditText);

        //update button, create a new account and direct to home page
        findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mEmail, mPassword, mName);
            }
        });

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
     * Creating a new user with the given email and password
     *
     * @param email    String representation of email
     * @param password String representation of password
     */
    private void createAccount(final EditText email, EditText password, final EditText name) {
        Log.d(TAG, "createAccount: " + email);
        if (!lHelper.validateSignUp(email, password, name)) {
            return;
        }

        //Create new user with email
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),
                password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "create new user status: " + task.isSuccessful());

                if (task.isSuccessful()) {
                    String userName = name.getText().toString();
                    lHelper.updateAccountName(userName, mAuth);

                    Intent signUpIntent = new Intent(SignUpActivity.this, MainActivity.class);
                    signUpIntent.putExtra(SIGN_UP_USER, userName);
                    startActivity(signUpIntent);
                } else {
                    Toast.makeText(SignUpActivity.this, "Create new user failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
