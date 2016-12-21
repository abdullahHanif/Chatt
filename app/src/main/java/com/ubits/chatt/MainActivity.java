package com.ubits.chatt;

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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.chatt.Fragments.About;
import com.ubits.chatt.Fragments.ChatList;

/**
 * Created by Abdullah
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String username;
    Toolbar toolbar;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = getIntent().getExtras();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.openDrawer(GravityCompat.START);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(bundle != null) {
            username = bundle.getString("USERNAME");

            View headerLayout = navigationView.inflateHeaderView(R.layout.left_nav_header);
            TextView tvName = (TextView) headerLayout.findViewById(R.id.Username);
            TextView tvStatus = (TextView) headerLayout.findViewById(R.id.Userstatus);
            tvName.setText(username);
            tvStatus.setText("Online");
        }

        launchFragment(1);
        //Enable Socket Connection

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
          super.onBackPressed();
            setAppTitle();
            }
    }

    private void setAppTitle() {
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chat) {
            launchFragment(1);
        }
        else if (id == R.id.nav_setting) {
            Toast.makeText(MainActivity.this,"Tapped Settings",Toast.LENGTH_LONG).show();
            launchFragment(2);
        }
        else if (id == R.id.nav_about) {
            launchFragment(3);
        }
        else if (id == R.id.nav_logout) {
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void launchFragment(int paramInt) {

       if (paramInt == 1) {
            String str = "Chat";
            this.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ChatList()).commit();
            toolbar.setTitle(" Chatt");
       }

       else if (paramInt == 2){}
       else if (paramInt == 3){
            String str = "About";
            this.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new About()).commit();
            toolbar.setTitle(" About");
     }

    }
}
