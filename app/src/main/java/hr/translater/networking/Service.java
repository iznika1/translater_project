package hr.translater.networking;

import hr.translater.mvp.models.TranslateData;
import hr.translater.mvp.models.TranslateResponse;
import hr.translater.mvp.models.WordResponse;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Igor on 24.1.2017..
 */

public class Service {

    private final TranslatorService translatorService;

    public Service(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    public Subscription getCroatianWordsList(final GetWordsCallback callback) {

        return translatorService.getCroatianWordsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<WordResponse>>() {
                    @Override
                    public Observable<WordResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<WordResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new TranslatorError(e));

                    }

                    @Override
                    public void onNext(WordResponse wordsResponse) {
                        callback.onSuccess(wordsResponse);

                    }
                });
    }

    public Subscription getSlovenianWordsList(final GetWordsCallback callback) {

        return translatorService.getSlovenianWordsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<WordResponse>>() {
                    @Override
                    public Observable<WordResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<WordResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new TranslatorError(e));

                    }

                    @Override
                    public void onNext(WordResponse wordsResponse) {
                        callback.onSuccess(wordsResponse);

                    }
                });
    }

    public Subscription addTranslation(final TranslateData translateData, final PostCallback callback) {

        return translatorService.postTranslation(translateData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new TranslatorError(e));

                    }

                    @Override
                    public void onNext(ResponseBody translateResponse) {
                        callback.onSuccess(translateResponse);

                    }
                });
    }

    public interface GetWordsCallback extends ErrorCallback{
        void onSuccess(WordResponse wordsResponse);
    }

    public interface PostCallback extends ErrorCallback{
        void onSuccess(ResponseBody rranslateResponse);
    }

    public interface ErrorCallback{
        void onError(TranslatorError translatorError);
    }

}
