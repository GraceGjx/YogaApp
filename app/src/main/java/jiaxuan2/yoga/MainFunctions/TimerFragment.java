package jiaxuan2.yoga.MainFunctions;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.*;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import jiaxuan2.yoga.R;


public class TimerFragment extends Fragment {
    private static final String GREETING = "Hello! ";
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private final String userUid = currentUser.getUid();

    //Set up all field
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private DatabaseReference currentUserReference = databaseReference.
            child("users").child(userUid);

    private TextView mNameView;
    private ToggleButton mStartButton;
    private Chronometer mchronometer;

    long startTime;
    long endTime;

    public TimerFragment() {
        //Default constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        //Setting up all field
        mNameView = (TextView) view.findViewById(R.id.userNameTextView);
        mStartButton = (ToggleButton) view.findViewById(R.id.timeButton);
        mchronometer = (Chronometer) view.findViewById(R.id.chronometer);

        //Update the the TextView with Greeting
        String userName = getArguments().getString(MainActivity.USER_NAME);
        mNameView.setText(GREETING + userName);

        //Click the button to record time
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStartButton.isChecked()) {
                    mchronometer.setBase(SystemClock.elapsedRealtime());
                    mchronometer.start();
                    startTime = System.currentTimeMillis();
                } else {
                    mchronometer.stop();
                    endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    String time = timeCalculator(duration);
                    timeDialogSetUp(time);

                    //update time to database, both record and rank
                    SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy");
                    String date = fmt.format(new Date());

                    //Update data
                    currentUserReference.child("data").
                            child(date).setValue(time);

                    databaseReference.child("rank").child(currentUser.getDisplayName()).
                            setValue(time);
                }
            }
        });

        return view;
    }

    /**
     * Determine how long a user has practiced
     *
     * @param duration a long passed from onCreate
     * @return String representation of the practice time
     */
    private String timeCalculator(long duration) {
        long hour = TimeUnit.MILLISECONDS.toHours(duration);
        long min = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
        long sec = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
        String result = "";

        if (hour > 0) {
            result += String.format("%d h ", hour);
        }
        if (min > 0) {
            result += String.format("%d m ", min);
        }
        if (sec > 0) {
            result += String.format("%d s ", sec);
        }
        return result;
    }


    /**
     * Create a new AlertDialog when user finished using timer
     *
     * @param time String representation of time passed from onCreate
     */
    private void timeDialogSetUp(String time) {
        AlertDialog.Builder timeBuilder = new AlertDialog.Builder(getContext(),
                R.style.mAlertDialogStyle);

        timeBuilder.setTitle("You practiced");
        timeBuilder.setMessage(time);
        timeBuilder.setCancelable(true);

        timeBuilder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog timeAlert = timeBuilder.create();
        timeAlert.show();

        //Change the size of the message size
        TextView timeView = (TextView) timeAlert.findViewById(android.R.id.message);
        timeView.setTextSize(20);

    }


}
