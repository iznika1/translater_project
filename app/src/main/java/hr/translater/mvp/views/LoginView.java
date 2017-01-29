package hr.translater.mvp.views;

/**
 * Created by Igor on 28.1.2017..
 */

public interface LoginView extends BaseView, LoginErrorView {
    void setLoginError();

    void navigateToHome();
}
