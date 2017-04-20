package faucher.paul.fleetinsurance;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ClaimCreator.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        LoggedInFragment.OnFragmentInteractionListener,
        LoggedOutFragment.OnFragmentInteractionListener,
        ClaimViewer.OnFragmentInteractionListener,
        PlanChanger.OnFragmentInteractionListener,
        Contact.OnFragmentInteractionListener,
        PlanInformation.OnFragmentInteractionListener,
        PlanFragment.OnFragmentInteractionListener{



    FragmentManager fm;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Plan Selected", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setImageResource(R.drawable.ic_add_black_24dp);
        //fab = null;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_main, new ProfileFragment());
        ft.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();


        if (id == R.id.nav_myPlan)
        {
            ft.replace(R.id.content_main, new ProfileFragment());
            ft.setCustomAnimations(R.anim.slideinfromright, R.anim.slideoutfromleft);
            ft.commit();
        }
        else if (id == R.id.nav_createClaim)
        {
            ft.replace(R.id.content_main, new ClaimCreator());
            ft.setCustomAnimations(R.anim.slideinfromright, R.anim.slideoutfromleft);
            ft.commit();
        }
        else if (id == R.id.nav_currentClaim)
        {
            ft.replace(R.id.content_main, new ClaimViewer());
            ft.setCustomAnimations(R.anim.slideinfromright, R.anim.slideoutfromleft);
            ft.commit();

        }
        else if (id == R.id.nav_planChange)
        {
            ft.replace(R.id.content_main, new PlanInformation());
            ft.setCustomAnimations(R.anim.slideinfromright, R.anim.slideoutfromleft);
            ft.commit();

        }
        else if (id == R.id.nav_contact)
        {
            ft.replace(R.id.content_main, new Contact());
            ft.setCustomAnimations(R.anim.slideinfromright, R.anim.slideoutfromleft);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
