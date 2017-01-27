package hr.translater.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.translater.R;
import hr.translater.adapters.WordsAdapter;
import hr.translater.mvp.models.Word;
import hr.translater.mvp.models.WordResponse;
import hr.translater.mvp.presenters.WordsPresenter;
import hr.translater.networking.Service;

/**
 * Created by Igor on 25.1.2017..
 */

public class SloWordsFragment extends BaseWordFragment {

    public SloWordsFragment(Service service){
        super(service);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater,container,savedInstanceState);
        WordsPresenter presenter = new WordsPresenter(service, this);
        presenter.getWordsListForLang("slovenian");

        return root;
    }

    @Override
    public void getWordsListSuccess(WordResponse wordResponse) {
        WordsAdapter adapter = new WordsAdapter(activity.getApplicationContext(), wordResponse.getWords(),
                new WordsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Word item) {
                        addInMemory(item.getWord());
                        loadDetailFragment();
                    }
                });

        recyclerView.setAdapter(adapter);
    }

    void addInMemory(String word){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("word", word);
        editor.commit();
    }
}
