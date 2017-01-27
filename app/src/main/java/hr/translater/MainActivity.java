package hr.translater;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

import javax.inject.Inject;

import hr.translater.data.AppComponent;
import hr.translater.data.DaggerAppComponent;
import hr.translater.fragments.AddTranslateFragment;
import hr.translater.fragments.CroWordsFragment;
import hr.translater.fragments.SloWordsFragment;
import hr.translater.networking.Service;
import hr.translater.networking.TranslatorModule;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppComponent appComponent;

    @Inject
    public Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        int id = item.getItemId();

        if (id == R.id.nav_translater) {
            fragmentTransaction.replace(R.id.flContainer, new AddTranslateFragment(service),"addTranslateFragment");
            fragmentTransaction.addToBackStack("addTranslateFragment");
        } else if (id == R.id.nav_cro_words) {
            fragmentTransaction.replace(R.id.flContainer, new CroWordsFragment(service),"croWordsFragment");
            fragmentTransaction.addToBackStack("croWordsFragment");
            addInMemory(true);
        } else if (id == R.id.nav_slo_words) {
            fragmentTransaction.replace(R.id.flContainer, new SloWordsFragment(service),"sloWordsFragment");
            fragmentTransaction.addToBackStack("sloWordsFragment");
            addInMemory(false);
        } else if (id == R.id.nav_sign_out) {

        }

        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
}
