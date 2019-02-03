package ba.fit.bookdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import ba.fit.bookdiary.ViewModels.CurrentlyReadingBooksViewModel;
import ba.fit.bookdiary.ViewModels.EditProfileViewModel;
import ba.fit.bookdiary.fragments.ChangePasswordFragment;
import ba.fit.bookdiary.fragments.CurrentlyReadingBooksFragment;
import ba.fit.bookdiary.fragments.EditProfileFragment;
import ba.fit.bookdiary.fragments.FinishedReadingFragment;
import ba.fit.bookdiary.helpers.FragmentUtils;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyRunnable;
import ba.fit.bookdiary.helpers.MySession;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentUtils.replaceFragment(this, R.id.placeholder, CurrentlyReadingBooksFragment.newInstance());
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

        final TextView name = (TextView)findViewById(R.id.loggedUserNameTextView);
        final TextView email = (TextView)findViewById(R.id.loggedUserEmailtextView);

        MyApiRequest.get(this, "Accounts/GetProfileInfo", new MyRunnable<EditProfileViewModel>() {
            @Override
            public void run(EditProfileViewModel model){
                if(model != null){
                    name.setText(model.firstName + " " + model.lastName);
                    email.setText(model.email);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        activity = this;

        if (id == R.id.nav_edit_profile) {
            EditProfileFragment editProfileFragment = EditProfileFragment.newInstance();
            FragmentUtils.openFragmentAsDialog(this, editProfileFragment, "editProfile");
        }
        else if (id == R.id.finishedReading) {
            FragmentUtils.replaceFragment(this, R.id.placeholder, FinishedReadingFragment.newInstance());
        }
        else if (id == R.id.nav_logout) {
            MyApiRequest.delete(this, "Autentifikacija/Delete", new MyRunnable<Object>() {
                @Override
                public void run(Object o) {
                    MySession.setKorisnik( null);
                    startActivity(new Intent(activity , LoginActivity.class));
                }
            });
        }
        else if (id == R.id.nav_change_password) {
            FragmentUtils.replaceFragment(this, R.id.placeholder, ChangePasswordFragment.newInstance());
        }
        else if (id == R.id.homepage) {
            FragmentUtils.replaceFragment(this, R.id.placeholder, CurrentlyReadingBooksFragment.newInstance());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
