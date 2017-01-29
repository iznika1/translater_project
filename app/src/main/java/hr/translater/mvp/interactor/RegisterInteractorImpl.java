package hr.translater.mvp.interactor;

import android.text.TextUtils;

import hr.translater.mvp.models.AccessToken;
import hr.translater.mvp.models.LoginData;
import hr.translater.networking.Service;
import hr.translater.networking.TranslatorError;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Igor on 29.1.2017..
 */

public class RegisterInteractorImpl implements RegisterInteractor {

    private Service service;
    private CompositeSubscription subscriptions;

    public RegisterInteractorImpl(Service service) {
        this.service = service;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void register(String usn, String pass, String confPass, OnRegisterFinishedListener listener) {
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
        if (TextUtils.isEmpty(confPass)) {
            listener.onPasswordError();
            error = true;
            return;
        }

        if (!pass.equals(confPass)) {
            listener.onPasswordError();
            listener.onConfirmPasswordError();
            error = true;
            return;
        }

        LoginData loginData = new LoginData(usn, pass);

        if(!error)
            register(loginData, listener);

    }

    private void register(final LoginData loginData, final RegisterInteractor.OnRegisterFinishedListener listener) {

        Subscription subscription = service.register(loginData,new hr.translater.networking.Service.PostCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                listener.onRegisterSuccess();
            }

            @Override
            public void onError(TranslatorError translatorError) {
                listener.onRegisterFailure();
            }

        });

        subscriptions.add(subscription);
    }
}
