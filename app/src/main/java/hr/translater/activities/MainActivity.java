package hr.translater.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.translater.R;
import hr.translater.data.AppComponent;
import hr.translater.data.DaggerAppComponent;
import hr.translater.fragments.AddTranslateFragment;
import hr.translater.fragments.CroWordsFragment;
import hr.translater.fragments.SloWordsFragment;
import hr.translater.mvp.models.LoginData;
import hr.translater.networking.Service;
import hr.translater.networking.TranslatorModule;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppComponent appComponent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Inject
    public Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);

        TextView tvUser = (TextView) header.findViewById(R.id.tv_user);

        LoginData loginData = SQLite.select().
                from(LoginData.class).querySingle();

        HeaderViewHolder headerViewHolder = new HeaderViewHolder(header);
        headerViewHolder.setUser(loginData.getUsername());

        File cacheFile = new File(getCacheDir(), "responses");
        appComponent = DaggerAppComponent.builder().translatorModule(new TranslatorModule(cacheFile)).build();
        getAppComponent().inject(this);


        getFragmentManager().beginTransaction().add(R.id.flContainer, new CroWordsFragment(service), "croWordsFragment").commit();
        addInMemory(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        int id = item.getItemId();



        if (id == R.id.nav_cro_words) {
            fragmentTransaction.replace(R.id.flContainer, new CroWordsFragment(service),"croWordsFragment");
            fragmentTransaction.addToBackStack("croWordsFragment");
            addInMemory(true);
        } else if (id == R.id.nav_slo_words) {
            fragmentTransaction.replace(R.id.flContainer, new SloWordsFragment(service),"sloWordsFragment");
            fragmentTransaction.addToBackStack("sloWordsFragment");
            addInMemory(false);
        } else if (id == R.id.nav_sign_out) {
            LoginData loginData = SQLite.select().
                    from(LoginData.class).querySingle();
            loginData.delete();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        fragmentTransaction.commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    void addInMemory(boolean isCroFragmentActive){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("croFragmentActive",isCroFragmentActive);
        editor.commit();
    }

    protected static class HeaderViewHolder {

        @BindView(R.id.tv_user)
        TextView tvUser;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setUser(String tvUser) {
            this.tvUser.setText(tvUser);
        }
    }
}
