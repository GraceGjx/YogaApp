package jiaxuan2.yoga.MainFunctions;

import android.support.v4.app.Fragment;
import android.content.Intent;;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;

import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import jiaxuan2.yoga.MainFunctions.Rank.RankFragment;
import jiaxuan2.yoga.LogInSystem.*;
import jiaxuan2.yoga.*;

public class MainActivity extends AppCompatActivity {
    public static final String USER_NAME = "UserName";

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private final String userUid = currentUser.getUid();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar setUp
        Toolbar mToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolBar);

        //Set up fragment relates
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment timerFragment = new TimerFragment();

        //Passing the user name during fragment transaction
        String userName = getUserName();
        Bundle argument = new Bundle();
        argument.putString(USER_NAME, userName);
        timerFragment.setArguments(argument);

        //Bottom navigation set up
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //Switch between different fragments
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction;
                        switch (item.getItemId()) {
                            case R.id.action_timer:
                                //Normal fragment transaction
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content, timerFragment).commit();
                                return true;

                            case R.id.action_rank:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                Fragment rankFragment = new RankFragment();
                                fragmentTransaction.replace(R.id.content, rankFragment).commit();
                                return true;

                            case R.id.action_calendar:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                Fragment calenderFragment = new CalendarFragment();
                                fragmentTransaction.replace(R.id.content, calenderFragment).commit();
                                return true;
                            default:
                                return false;
                        }
                    }
                }
        );

        //One time display of the first fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, timerFragment).commit();

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
                //Go to Setting activity and update profile
                Intent toSetting = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(toSetting);
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


    /**
     * Get the username from either the logInActivity or SignUpActivity
     * @return String representation of username
     */
    private String getUserName() {
        //Get the intent from other activities
        Intent intent = this.getIntent();
        String logInName = intent.getStringExtra(LogInActivity.LOGIN_USER);
        String signUpName = intent.getStringExtra(SignUpActivity.SIGN_UP_USER);

        String result = "";
        //Determine name to be display, and create user in the database
        if (logInName != null) {
            Log.d("Greeting: ", logInName);
            result = logInName;
        } else if (signUpName != null){
            Log.d("Greeting: ", signUpName);
            createUserInDatabase(signUpName);
            result = signUpName;
        }
        return result;
    }

    /**
     * Creating a new user space in the database
     *
     * @param signUpName String representation of user name passed from onCreate
     */
    private void createUserInDatabase(String signUpName) {
        databaseReference.
                child("users").child(userUid).child("name").setValue(signUpName);
        databaseReference.child("rank").child(signUpName).setValue("0");
        databaseReference.child("like").child(signUpName).setValue("0");
    }

}