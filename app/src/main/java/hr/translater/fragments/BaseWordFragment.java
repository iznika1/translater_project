package hr.translater.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.translater.R;
import hr.translater.adapters.WordsAdapter;
import hr.translater.mvp.models.Word;
import hr.translater.mvp.models.WordResponse;
import hr.translater.mvp.views.WordsView;
import hr.translater.networking.Service;

/**
 * Created by Igor on 25.1.2017..
 */

public class BaseWordFragment extends Fragment implements WordsView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeContainer;

    protected Activity activity;

    protected Service service;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = context instanceof Activity ? (Activity) context : null;
        this.activity = activity;
    }

    public BaseWordFragment(Service service){
        this.service = service;
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Toast.makeText(activity.getApplicationContext(), appErrorMessage,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getWordsListSuccess(WordResponse wordResponse) {

        WordsAdapter adapter = new WordsAdapter(activity.getApplicationContext(), wordResponse.getWords(),
                new WordsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Word item) {
                        Toast.makeText(activity.getApplicationContext(), item.getWord(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setRefreshing(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_words,container,false);
        ButterKnife.bind(this,root);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(false);
            }
        }, 2000);
    }
}
