package hr.translater.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.translater.R;
import hr.translater.data.AppComponent;
import hr.translater.data.DaggerAppComponent;
import hr.translater.fragments.CroWordsFragment;
import hr.translater.fragments.LoginFragment;
import hr.translater.mvp.presenters.LoginPresenter;
import hr.translater.mvp.presenters.LoginPresenterImpl;
import hr.translater.mvp.views.LoginView;
import hr.translater.networking.Service;
import hr.translater.networking.TranslatorModule;

/**
 * Created by Igor on 28.1.2017..
 */

public class LoginActivity extends AppCompatActivity{

    private AppComponent appComponent;

    @Inject
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        ButterKnife.bind(this);

        File cacheFile = new File(getCacheDir(), "responses");
        appComponent = DaggerAppComponent.builder().translatorModule(new TranslatorModule(cacheFile)).build();
        getAppComponent().inject(this);

        getFragmentManager().beginTransaction().add(R.id.flContainer, new LoginFragment(service), "loginFragment").commit();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
