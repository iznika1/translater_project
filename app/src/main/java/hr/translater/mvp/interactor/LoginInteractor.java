package hr.translater.mvp.interactor;

/**
 * Created by Igor on 28.1.2017..
 */

public interface LoginInteractor {
    interface  OnLoginFinishedListener{
        void onUsernameError();
        void onPasswordError();
        void onLoginFailure();
        void onLoginSuccess();
    }
    void login(String usn, String pass, OnLoginFinishedListener listener);
}
