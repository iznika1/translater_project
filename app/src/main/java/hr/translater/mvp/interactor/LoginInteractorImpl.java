package hr.translater.mvp.interactor;

import android.text.TextUtils;
import android.util.Log;

import hr.translater.mvp.models.AccessToken;
import hr.translater.mvp.models.LoginData;
import hr.translater.mvp.models.TranslateData;
import hr.translater.networking.Service;
import hr.translater.networking.TranslatorError;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Igor on 28.1.2017..
 */

public class LoginInteractorImpl implements LoginInteractor {

    private Service service;
    private CompositeSubscription subscriptions;

    public LoginInteractorImpl(Service service) {
        this.service = service;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void login(final String usn, final String pass, final OnLoginFinishedListener listener) {

        boolean error = false;
        if (TextUtils.isEmpty(usn)) {
            listener.onUsernameError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            listener.onPasswordError();
            error = true;
            return;
        }

        LoginData loginData = new LoginData(usn, pass);

        if (!error)
            login(loginData, listener);

    }

    private void login(final LoginData loginData, final OnLoginFinishedListener listener) {

        Subscription subscription = service.login(loginData,new hr.translater.networking.Service.PostLoginCallback() {
            @Override
            public void onSuccess(AccessToken accessToken) {
                saveAccessToken(accessToken, loginData);
                listener.onLoginSuccess();
            }

            @Override
            public void onError(TranslatorError translatorError) {
                listener.onLoginFailure();
            }

        });

        subscriptions.add(subscription);
    }

    private void saveAccessToken(AccessToken accessToken, LoginData loginData){
        accessToken.save();
        loginData.setAccessToken(accessToken);
        loginData.save();
    }


}

