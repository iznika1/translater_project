package hr.translater.mvp.presenters;

import hr.translater.mvp.views.WordsView;
import hr.translater.mvp.models.WordResponse;
import hr.translater.networking.Service;
import hr.translater.networking.TranslatorError;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Igor on 24.1.2017..
 */

public class WordsPresenter {

    private final Service service;
    private final WordsView view;
    private CompositeSubscription subscriptions;

    public WordsPresenter(Service service, WordsView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }
    public void getCroWordsList() {
        view.showWait();

        Subscription subscription = service.getCroatianWordsList(new Service.GetWordsCallback() {
            @Override
            public void onSuccess(WordResponse wordsResponse) {
                view.removeWait();
                view.getWordsListSuccess(wordsResponse);
            }

            @Override
            public void onError(TranslatorError translatorError) {
                view.removeWait();
                view.onFailure(translatorError.getAppErrorMessage());
            }

        });


        subscriptions.add(subscription);
    }

    public void getSloWordsList() {
        view.showWait();

        Subscription subscription = service.getSlovenianWordsList(new Service.GetWordsCallback() {
            @Override
            public void onSuccess(WordResponse wordsResponse) {
                view.removeWait();
                view.getWordsListSuccess(wordsResponse);
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
