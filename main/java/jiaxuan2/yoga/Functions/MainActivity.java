package jiaxuan2.yoga.MainFunctions;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import jiaxuan2.yoga.BaseClass.PracticeData;
import jiaxuan2.yoga.LogInSystem.LogInActivity;
import jiaxuan2.yoga.LogInSystem.SignUpActivity;
import jiaxuan2.yoga.R;
import jiaxuan2.yoga.BaseClass.User;

public class MainActivity extends AppCompatActivity {
    private static final String GREETING = "Hello!\n";

    private TextView mNameView;
    private TextView mTimeView;
    private ToggleButton mStartButton;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolBar);

        //Setting up all field
        mNameView = (TextView) findViewById(R.id.userNameTextView);
        mTimeView = (TextView) findViewById(R.id.timeTextView);
        mStartButton = (ToggleButton) findViewById(R.id.timeButton);

        mStartButton.setTextOff("START");
        mStartButton.setTextOn("STOP");


        upDateGreeting();


        //Click the button to record time
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer();
            }
        });

        //databaseReference.push().setValue(currentUser);
    }


    public void upDateGreeting(){
        //Get the intent from other activities
        Intent intent = getIntent();
        String logInName = intent.getStringExtra(LogInActivity.LOGIN_USER);
        String signUpName = intent.getStringExtra(SignUpActivity.SIGN_UP_USER);

        //Determine name to be display
        if (logInName != null) {
            Log.d("Greeting: ", logInName);
            mNameView.setText(GREETING + logInName);
        } else {
            Log.d("Greeting: ", signUpName);
            mNameView.setText(GREETING + signUpName);

            User currentUser = new User(signUpName);
            currentUser.addData(new PracticeData(DateFormat.getDateInstance().format(new Date()), 0));
            databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(currentUser);
            databaseReference.child("rank").child(currentUser.getName()).setValue(currentUser.
                    getCurrentData());
        }
    }


    public void timer(){
        long startTime = 0;
        long endTime = 0;

        if (mStartButton.getText().equals("START")) {
            startTime = Calendar.getInstance().getTimeInMillis();
        } else {
            endTime = Calendar.getInstance().getTimeInMillis();
        }

        mTimeView.setText("" + (int) ((endTime / (1000 * 60)) % 60));

    }


    /**
     * Overriding the onBackPressed to prevent user to go back to the log in activity
     */
    @Override
    public void onBackPressed() {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                //Go to another activity and update profile
                return true;

            case R.id.action_logOut:
                //Initialize sign out button
                FirebaseAuth.getInstance().signOut();
                Intent backToLogin = new Intent(getApplicationContext(), LogInActivity.class);
                        startActivity(backToLogin);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}