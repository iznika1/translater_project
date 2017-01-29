package hr.translater.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.translater.R;
import hr.translater.mvp.presenters.RegisterPresenter;
import hr.translater.mvp.presenters.RegisterPresenterImpl;
import hr.translater.mvp.views.RegisterView;
import hr.translater.networking.Service;

/**
 * Created by Igor on 29.1.2017..
 */

public class RegisterFragment extends Fragment implements RegisterView {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    Activity activity;
    RegisterPresenter presenter;
    Service service;

    public RegisterFragment(Service service) {
        this.service = service;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.register_fragment, container, false);
        ButterKnife.bind(this, root);

        presenter = new RegisterPresenterImpl(service, this);

        return root;
    }

    @Override
    public void setConfirmPasswordError() {
        username.setError(getString(R.string.confirm_password_error));
    }

    @Override
    public void setRegisterError() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Something wrong! Try again!", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void navigateToLogin() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "User register successfull!", Snackbar.LENGTH_LONG);
        snackbar.show();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, new LoginFragment(service) ,"loginFragment");
        fragmentTransaction.addToBackStack("loginFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, appErrorMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @OnClick(R.id.register_btn)
    void onRegisterBtnClick() {
        presenter.validateInputFields(username.getText().toString(), password.getText().toString(), confirmPassword.getText().toString() );
    }
}
