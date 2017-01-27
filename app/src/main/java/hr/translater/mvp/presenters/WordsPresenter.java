package hr.translater.mvp.presenters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import hr.translater.R;
import hr.translater.fragments.TranslateFragment;
import hr.translater.mvp.models.TranslateData;
import hr.translater.mvp.models.TranslateResponse;
import hr.translater.mvp.views.AddView;
import hr.translater.mvp.views.BaseView;
import hr.translater.mvp.views.TranslateView;
import hr.translater.mvp.views.WordsView;
import hr.translater.mvp.models.WordResponse;
import hr.translater.networking.Service;
import hr.translater.networking.TranslatorError;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Igor on 24.1.2017..
 */

public class WordsPresenter {

    private final Service service;
    private final BaseView view;
    private CompositeSubscription subscriptions;

    public WordsPresenter(Service service, BaseView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getWordsListForLang(String lang) {
        view.showWait();

        Subscription subscription = service.getWordsListForLang(lang, new Service.GetWordsCallback() {
            @Override
            public void onSuccess(WordResponse wordsResponse) {
                view.removeWait();
                ((WordsView)view).getWordsListSuccess(wordsResponse);
            }

            @Override
            public void onError(TranslatorError translatorError) {
                view.removeWait();
                view.onFailure(translatorError.getAppErrorMessage());
            }

        });


        subscriptions.add(subscription);
    }

    public void addTranslation(TranslateData translateData) {
        view.showWait();

        Subscription subscription = service.addTranslation(translateData,new Service.PostCallback() {
            @Override
            public void onSuccess(ResponseBody translateResponse) {
                view.removeWait();
                ((AddView)view).addTranslateSuccess(translateResponse);
            }

            @Override
            public void onError(TranslatorError translatorError) {
                view.removeWait();
                view.onFailure(translatorError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void getTranslate(String langFrom, String langTo, String word) {
        view.showWait();

        Subscription subscription = service.getTranslate(langFrom, langTo, word, new Service.GetTranslateCallback() {
            @Override
            public void onSuccess(TranslateResponse translateResponse) {
                view.removeWait();
                ((TranslateView) view).getTranslationSucess(translateResponse);
            }

            @Override
            public void onError(TranslatorError translatorError) {
                view.removeWait();
                view.onFailure(translatorError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
