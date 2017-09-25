package jiaxuan2.yoga.MainFunctions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import jiaxuan2.yoga.BaseClass.User;
import jiaxuan2.yoga.R;

public class RankActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<User> mUsers = new ArrayList<>();
    private RankAdapter mRankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRankAdapter = new RankAdapter(mUsers);
        mRecyclerView.setAdapter(mRankAdapter);

    }
}
