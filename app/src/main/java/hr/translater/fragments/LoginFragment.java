package hr.translater.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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
import hr.translater.activities.MainActivity;
import hr.translater.mvp.presenters.LoginPresenter;
import hr.translater.mvp.presenters.LoginPresenterImpl;
import hr.translater.mvp.views.LoginView;
import hr.translater.networking.Service;

/**
 * Created by Igor on 29.1.2017..
 */

public class LoginFragment extends Fragment implements LoginView {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    Activity activity;
    LoginPresenter presenter;
    Service service;

    public LoginFragment(Service service) {
        this.service = service;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, root);

        presenter = new LoginPresenterImpl(service, this);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = context instanceof Activity ? (Activity) context : null;
        this.activity = activity;
    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void setLoginError() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Username or password is wrong!", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
        activity.finish();
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

    @OnClick(R.id.login_btn)
    void onLoginBtnClick() {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
    }

    @OnClick(R.id.register_btn)
    void onRegisterBtnClick() {
        loadFragment(new RegisterFragment(service),"registerFragment");
    }

    private void loadFragment(Fragment fragment, String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment ,tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
}
