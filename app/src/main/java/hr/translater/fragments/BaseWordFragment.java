package hr.translater.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.translater.R;
import hr.translater.adapters.WordsAdapter;
import hr.translater.mvp.models.Word;
import hr.translater.mvp.models.WordResponse;
import hr.translater.mvp.presenters.WordsPresenter;
import hr.translater.mvp.views.BaseView;
import hr.translater.mvp.views.DetailView;
import hr.translater.mvp.views.WordsView;
import hr.translater.networking.Service;

/**
 * Created by Igor on 25.1.2017..
 */

public class BaseWordFragment extends Fragment implements WordsView, DetailView {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView((R.id.fab))
    FloatingActionButton floatingActionButton;

    protected Activity activity;

    protected Service service;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = context instanceof Activity ? (Activity) context : null;
        this.activity = activity;
    }

    public BaseWordFragment(Service service) {
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
    public void loadDetailFragment() {
        loadFragment(new TranslateFragment(service),"translationFragment");
    }

    @Override
    public void getWordsListSuccess(WordResponse wordResponse) {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.words_fragment, container, false);
        ButterKnife.bind(this, root);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    @OnClick(R.id.fab)
    void addTranslateBtnClick() {
        loadFragment(new AddTranslateFragment(service),"addTranslateFragment");
    }

    void loadFragment(Fragment fragment, String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment ,tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
}
