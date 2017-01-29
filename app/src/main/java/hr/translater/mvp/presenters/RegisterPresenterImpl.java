package hr.translater.mvp.presenters;

import hr.translater.mvp.interactor.RegisterInteractor;
import hr.translater.mvp.interactor.RegisterInteractorImpl;
import hr.translater.mvp.views.RegisterView;
import hr.translater.networking.Service;

/**
 * Created by Igor on 29.1.2017..
 */

public class RegisterPresenterImpl implements RegisterPresenter, RegisterInteractor.OnRegisterFinishedListener {


    private RegisterView registerView;
    private RegisterInteractor registerInteractor;

    public RegisterPresenterImpl(Service service, RegisterView registerView) {
        this.registerView = registerView;
        this.registerInteractor = new RegisterInteractorImpl(service);
    }


    @Override
    public void onUsernameError() {
        if (registerView != null) {
            registerView.setUsernameError();
            registerView.removeWait();
        }
    }

    @Override
    public void onPasswordError() {
        if (registerView != null) {
            registerView.setPasswordError();
            registerView.removeWait();
        }
    }

    @Override
    public void onConfirmPasswordError() {
        if (registerView != null) {
            registerView.setConfirmPasswordError();
            registerView.removeWait();
        }
    }

    @Override
    public void onRegisterFailure() {
        if (registerView != null) {
            registerView.setRegisterError();
            registerView.removeWait();
        }
    }

    @Override
    public void onRegisterSuccess() {
        if (registerView != null) {
            registerView.navigateToLogin();
        }
    }

    @Override
    public void validateInputFields(String usn, String pass, String confPass) {
        if (registerView != null) {
            registerView.showWait();
        }

        registerInteractor.register(usn, pass, confPass, this);
    }
}
