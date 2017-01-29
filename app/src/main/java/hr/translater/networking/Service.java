package hr.translater.networking;

import hr.translater.mvp.models.AccessToken;
import hr.translater.mvp.models.LoginData;
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

    public Subscription getWordsListForLang(String lang, final GetWordsCallback callback) {

        return translatorService.getWordsListForLang(lang)
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

    public Subscription getTranslate(String langFrom, String langTo, String word, final GetTranslateCallback callback) {

        return translatorService.getTranslate(langFrom, langTo, word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<TranslateResponse>>() {
                    @Override
                    public Observable<TranslateResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<TranslateResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new TranslatorError(e));

                    }

                    @Override
                    public void onNext(TranslateResponse translateResponse) {
                        callback.onSuccess(translateResponse);

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

    public Subscription register(final LoginData loginData, final PostCallback callback) {

        return translatorService.register(loginData)
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
                    public void onNext(ResponseBody responseBody) {
                        callback.onSuccess(responseBody);

                    }
                });
    }

    public Subscription login(final LoginData loginData, final PostLoginCallback callback) {

        return translatorService.login(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<AccessToken>>() {
                    @Override
                    public Observable<AccessToken> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<AccessToken>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new TranslatorError(e));

                    }

                    @Override
                    public void onNext(AccessToken accessToken) {
                        callback.onSuccess(accessToken);

                    }
                });
    }

    public interface GetWordsCallback extends ErrorCallback{
        void onSuccess(WordResponse wordsResponse);
    }

    public interface GetTranslateCallback extends ErrorCallback{
        void onSuccess(TranslateResponse translateResponse);
    }

    public interface PostCallback extends ErrorCallback{
        void onSuccess(ResponseBody responseBody);
    }

    public interface PostLoginCallback extends ErrorCallback{
        void onSuccess(AccessToken accessToken);
    }

    public interface ErrorCallback{
        void onError(TranslatorError translatorError);
    }

}
