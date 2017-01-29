package hr.translater.mvp.views;

/**
 * Created by Igor on 29.1.2017..
 */

public interface RegisterView extends BaseView, LoginErrorView {

    void setConfirmPasswordError();

    void setRegisterError();

    void navigateToLogin();
}
