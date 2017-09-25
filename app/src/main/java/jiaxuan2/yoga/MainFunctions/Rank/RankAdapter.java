package jiaxuan2.yoga.MainFunctions.Rank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

import jiaxuan2.yoga.R;

/**
 * Created by jiaxuan2 on 4/18/17.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    //Set up fields needed
    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private String currentUserName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

    //Set up two parallel arrayList
    private ArrayList<String> mUsers = new ArrayList<>();
    private ArrayList<String> userData = new ArrayList<>();


    public RankAdapter(final Context context, DatabaseReference ref) {
        mContext = context;
        mDatabaseReference = ref;

        ChildEventListener rankEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String time = dataSnapshot.getValue(String.class);

                mUsers.add(dataSnapshot.getKey());
                userData.add(time);
                notifyItemInserted(mUsers.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String newUser = dataSnapshot.getKey();

                int userIndex = mUsers.indexOf(newUser);
                if (userIndex > -1) {
                    userData.set(userIndex, dataSnapshot.getValue(String.class));
                    notifyItemChanged(userIndex);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getKey();
                int userIndex = mUsers.indexOf(user);

                if (userIndex > -1) {
                    mUsers.remove(userIndex);
                    userData.remove(userIndex);
                    notifyItemRemoved(userIndex);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, "Failed to load data.", Toast.LENGTH_LONG).show();
            }
        };
        ref.child("rank").addChildEventListener(rankEventListener);
        mChildEventListener = rankEventListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rankListItem = LayoutInflater.from(mContext).
                inflate(R.layout.rank_list_items, parent, false);
        return new ViewHolder(rankListItem);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (!mUsers.get(position).equals(currentUserName)) {
            final String name = mUsers.get(position);

            //Display name and time for all users
            holder.nameView.setText(name);
            holder.timeView.setText(userData.get(position));


            //Display the like count of this user
            ValueEventListener likeListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    holder.likeView.setText(dataSnapshot.getChildrenCount() + "");
                    if (dataSnapshot.hasChild(currentUserName)) {
                        holder.likeButton.setChecked(dataSnapshot.child(currentUserName).
                                getValue(Boolean.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mDatabaseReference.child("like").child(name).addValueEventListener(likeListener);


            //Update the like count of this user when click the button in database
            holder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.likeButton.isChecked()) {
                        mDatabaseReference.child("like").child(name).
                                child(currentUserName).setValue(true);
                    } else {
                        mDatabaseReference.child("like").child(name).
                                child(currentUserName).removeValue();
                    }
                }
            });
        } else {
            //If its the current user, hides all the data
            holder.likeButton.setVisibility(View.GONE);
            holder.nameView.setVisibility(View.GONE);
            holder.timeView.setVisibility(View.GONE);
            holder.likeView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void cleanupListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }
    }


    /**
     * A ViewHolder class for our adapter that 'caches' the references to the
     * subviews, so we don't have to look them up each time.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView timeView;
        private ToggleButton likeButton;
        private TextView likeView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.nameTextView);
            timeView = (TextView) itemView.findViewById(R.id.timeTextView);
            likeButton = (ToggleButton) itemView.findViewById(R.id.likeToggleButton);
            likeView = (TextView) itemView.findViewById(R.id.likeCountTextView);
        }
    }
}


