package hr.translater.mvp.interactor;

/**
 * Created by Igor on 29.1.2017..
 */

public interface RegisterInteractor {
    interface  OnRegisterFinishedListener{
        void onUsernameError();
        void onPasswordError();
        void onConfirmPasswordError();
        void onRegisterFailure();
        void onRegisterSuccess();
    }
    void register(String usn, String pass, String confPass, RegisterInteractor.OnRegisterFinishedListener listener);
}
