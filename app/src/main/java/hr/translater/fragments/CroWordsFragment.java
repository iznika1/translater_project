package hr.translater.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.translater.mvp.presenters.WordsPresenter;
import hr.translater.networking.Service;

/**
 * Created by iznika on 25.1.2017..
 */

public class CroWordsFragment extends BaseWordFragment {


    public CroWordsFragment(Service service) {
        super(service);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater,container,savedInstanceState);
        WordsPresenter presenter = new WordsPresenter(service, this);
        presenter.getCroWordsList();

        return root;
    }

}
