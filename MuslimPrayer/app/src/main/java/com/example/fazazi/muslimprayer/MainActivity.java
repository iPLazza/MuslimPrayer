package com.example.fazazi.muslimprayer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.layali.codedreamer.muslim.Models.recitator.Recitator;
import com.layali.codedreamer.muslim.Utility.NetWorkStatus;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NetWorkStatus status;
    private Recitator book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame, new Home());
        tx.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            status = new NetWorkStatus(this);
            if (!status.isConnecting()) {
                showAlertDialog(MainActivity.this, getString(R.string.err_network), getString(R.string.err_network_msg));
            }
        } catch (Exception e) {
            Log.v("Exception", Log.getStackTraceString(e));
        }
    }

    public class Invoke extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected Void doInBackground(Void... arg0) {


            return null;
        }

        protected void onPostExecute(Void name) {

            dialog.dismiss();
        }

    }

    public void showAlertDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setTitle(title).setMessage(msg).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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
        Fragment fragment = new Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager(); // For AppCompat use getSupportFragmentManager
        switch (item.getItemId()) {
            //  Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            case R.id.home:
                fragment = new Home();
                break;
            case R.id.nav_camera:
                fragment = new recitator();
                break;
            case R.id.tasbih:
                fragment = new tasbeh();
                break;
            case R.id.names:
                fragment = new Allah_names();
                break;
            case R.id.qiblah:
                fragment = new Compass();
                break;
            case R.id.books:
                fragment = new bookFra();
                break;
            case R.id.priere:
                fragment = new preyerTime();
                break;
            case R.id.hadith:
                fragment = new audioramadan();
                break;
            case R.id.nav_slideshow:
                fragment = new verset();
                break;
            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Muslim Application  Created With Heart By Mohamed x (Fazazi + Khomsi) ";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.about:
                fragment = new About();
                break;
            case R.id.rate:
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://play.google.com/store/apps/developer?id=MMA+Inc"));
                startActivity(viewIntent);
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
