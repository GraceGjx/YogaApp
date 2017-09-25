package jiaxuan2.yoga.MainFunctions;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import jiaxuan2.yoga.R;


public class CalendarFragment extends Fragment {
    private final SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy");
    private CalendarView mCalendarView;
    private TextView mDateView;
    private TextView mTimeView;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dataReference = firebaseDatabase.getReference().child("users").
            child(user.getUid()).child("data");


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(jiaxuan2.yoga.R.layout.fragment_calendar, container, false);
        //Set up all fields
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
        mDateView = (TextView) view.findViewById(R.id.dateTextView);
        mTimeView = (TextView) view.findViewById(R.id.practiceTimeTextView);

        //Set up intialize date and time
        String currentDate = fmt.format(new Date());
        mDateView.setText(currentDate);
        setTime(currentDate);

        //Update both date and time when user choose a different date
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView,
                                            int year, int month, int day) {
                Calendar calendar = new GregorianCalendar(year, month, day);
                String date = fmt.format(calendar.getTime());
                mDateView.setText(date);
                setTime(date);
            }
        });

        return view;
    }


    /**
     * Helper method to get the time with corresponding date from the database and
     * set the time in timeView.
     *
     * @param date String representation of the current selecting date
     */
    private void setTime(String date) {
        dataReference.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String time = dataSnapshot.getValue(String.class);
                if (time == null) {
                    mTimeView.setText("--");
                } else {
                    mTimeView.setText(time);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
