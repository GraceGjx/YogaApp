package jiaxuan2.yoga.MainFunctions.Rank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import jiaxuan2.yoga.R;


public class RankFragment extends Fragment {
    //Declare all fields
    private RecyclerView mRecyclerView;
    private RankAdapter mRankAdapter;
    private TextView mNameView;
    private TextView mTimeView;
    private TextView mLikeView;
    private ToggleButton mLikeButton;

    //Declare and assign all fire base fields
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dataReference = firebaseDatabase.getReference();
    private String currentUserName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    private DatabaseReference currentUserReference = dataReference.child("rank").child(currentUserName);
    private DatabaseReference currentLikeReference = dataReference.child("like").child(currentUserName);

    public RankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);

        //Set up all views
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mNameView = (TextView) view.findViewById(R.id.currentUserTextView);
        mTimeView = (TextView) view.findViewById(R.id.currentTimeTextView);
        mLikeView = (TextView) view.findViewById(R.id.selfCountTextView);
        mLikeButton = (ToggleButton) view.findViewById(R.id.selfToggleButton);

        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLikeButtonStatus();
            }
        });

        //Get the current user's data
        currentUserData();
        currentLikeCount();

        //Set up the recycler view
        mRankAdapter = new RankAdapter(getContext(), dataReference);
        mRecyclerView.setAdapter(mRankAdapter);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mRankAdapter.cleanupListener();
    }


    /**
     * Get the current user's like counts
     */
    private void currentLikeCount() {
        ValueEventListener likeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mLikeView.setText(dataSnapshot.getChildrenCount() + "");
                if (dataSnapshot.hasChild(currentUserName)) {
                    mLikeButton.setChecked(dataSnapshot.child(currentUserName).
                            getValue(Boolean.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        currentLikeReference.addValueEventListener(likeListener);
    }


    /**
     * Update the like count in the database
     */
    private void checkLikeButtonStatus() {
        if (mLikeButton.isChecked()) {
            currentLikeReference.child(currentUserName).setValue(true);
        } else {
            currentLikeReference.child(currentUserName).removeValue();
        }
    }


    /**
     * Get the current user's data and sets up all fields
     */
    private void currentUserData() {
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNameView.setText(dataSnapshot.getKey());
                mTimeView.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        currentUserReference.addValueEventListener(userListener);
    }


}
