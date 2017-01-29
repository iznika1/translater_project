package hr.translater.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.translater.R;
import hr.translater.adapters.TranslateAdapter;
import hr.translater.mvp.models.TranslateResponse;
import hr.translater.mvp.presenters.WordsPresenter;
import hr.translater.mvp.views.TranslateView;
import hr.translater.networking.Service;

/**
 * Created by Igor on 27.1.2017..
 */

public class TranslateFragment extends Fragment implements TranslateView {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.listTranslation)
    RecyclerView recyclerView;

    private Activity activity;

    private Service service;

    public TranslateFragment(Service service){
        this.service = service;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = context instanceof Activity ? (Activity) context : null;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.translation_fragment,container,false);
        ButterKnife.bind(this,root);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        final WordsPresenter presenter = new WordsPresenter(service, this);
        if(isCroFragmentBefore())
            presenter.getTranslate("croatian","slovenian",readFromMemory());
        else
            presenter.getTranslate("slovenian","croatian",readFromMemory());

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void getTranslationSucess(TranslateResponse translateResponse) {
        TranslateAdapter adapter = new TranslateAdapter(activity.getApplicationContext(), translateResponse.getLangToSet());
        recyclerView.setAdapter(adapter);

        tvWord.setText(readFromMemory());
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

    String readFromMemory(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String word = sharedPref.getString("word","");

        return word;
    }

    boolean isCroFragmentBefore(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean croFragmentActive = sharedPref.getBoolean("croFragmentActive",false);

        return croFragmentActive;
    }
}
