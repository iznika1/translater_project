package hr.translater.mvp.presenters;


import hr.translater.mvp.interactor.LoginInteractor;
import hr.translater.mvp.interactor.LoginInteractorImpl;
import hr.translater.mvp.views.LoginView;
import hr.translater.networking.Service;

/**
 * Created by Igor on 28.1.2017..
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(Service service, LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl(service);
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showWait();
        }

        loginInteractor.login(username, password, this);
    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.removeWait();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.removeWait();
        }
    }

    @Override
    public void onLoginFailure() {
        if (loginView != null) {
            loginView.setLoginError();
            loginView.removeWait();
        }
    }

    @Override
    public void onLoginSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}
