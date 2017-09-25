package jiaxuan2.yoga.MainFunctions;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jiaxuan2.yoga.BaseClass.User;
import jiaxuan2.yoga.R;

/**
 * Created by jiaxuan2 on 4/18/17.
 */

public class RankAdapter extends  RecyclerView.Adapter<RankAdapter.ViewHolder>{

    ArrayList<User> users;

    public RankAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rankListItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.rank_list_items, parent, false);
        return new ViewHolder(rankListItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User user = users.get(position);

        holder.nameView.setText(user.getName());
        holder.timeView.setText(user.getCurrentData() + "");

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     *  A ViewHolder class for our adapter that 'caches' the references to the
     * subviews, so we don't have to look them up each time.
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView nameView;
        private TextView timeView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameView = (TextView) itemView.findViewById(R.id.nameTextView);
            timeView = (TextView) itemView.findViewById(R.id.timeTextView);
        }
    }
}


