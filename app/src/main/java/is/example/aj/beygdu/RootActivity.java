package is.example.aj.beygdu;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import is.example.aj.beygdu.Async.BinAsyncTask;
import is.example.aj.beygdu.Fragments.CacheFragment;
import is.example.aj.beygdu.Fragments.ResultFragment;
import is.example.aj.beygdu.Fragments.SearchFragment;
import is.example.aj.beygdu.Parser.WordResult;
import is.example.aj.beygdu.Skrambi.SkrambiWT;
import is.example.aj.beygdu.UIElements.CustomDialog;
import is.example.aj.beygdu.Utils.InputValidator;

public class RootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback, CustomDialog.CustomDialogListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        selectItem(0);
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
        getMenuInflater().inflate(R.menu.root, menu);
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

        if (id == R.id.drawer_search) {
            selectItem(0);
        } else if (id == R.id.drawer_about) {
            selectItem(1);
        } else if (id == R.id.drawer_last_searches) {
            selectItem(2);
        }  else if (id == R.id.drawer_authors) {

        } else if (id == R.id.drawer_contact) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void selectItem(int position) {

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(position == 0) {
            ft.replace(R.id.frame_layout, new SearchFragment());
        }
        else if(position == 1) {
           makeToast("AboutFragment");
        }
        else if(position == 2) {
            CacheFragment cacheFragment = new CacheFragment();

            Bundle bundle = new Bundle();
            bundle.putStringArray("arguments", new String[] { "one", "two", "three", "four" });

            cacheFragment.setArguments(bundle);

            ft.replace(R.id.frame_layout, cacheFragment);
        }
        else {

        }

        ft.commit();
    }

    private void makeToast(String o) {
        Toast toast = Toast.makeText(getApplicationContext(), o, Toast.LENGTH_LONG);
        toast.show();
    }


/*
    @Override
    public void onSearchFragmentInteraction(String s, boolean state) {
        //makeToast("Test");
        Bundle bundle = new Bundle();
        bundle.putString("title", "I LIKE B..");
        bundle.putStringArray("arguments", new String[] { "I", "like", "b...", "and", "e.." });
        CustomDialog customDialog = new CustomDialog();
        customDialog.setArguments(bundle);
        customDialog.show(getFragmentManager(), "0");
    }
*/

    private void prepareResultFragment(String url) {

        try {
            WordResult wR = new BinAsyncTask(getApplicationContext()).execute(url).get();

            if(wR.getDescription().equals(WordResult.singleHit)) {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("WordResult", wR);
                ResultFragment fragment = new ResultFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.frame_layout, fragment);
                ft.commit();
            }
            else if(wR.getDescription().equals(WordResult.multiHit)) {
                //TODO: implementation
            }
            else {

            }


            //makeToast(wR.getDescription());
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (NullPointerException g) {
            g.printStackTrace();
        }

    }

    @Override
    public void onDialogClick(int position) {
        makeToast(position + "");
    }

    @Override
    public void onSearchCallback(String input, boolean extended) {
        /*
        SkrambiWT skrambiWT = new SkrambiWT(getApplicationContext(), input);
        String[] arr = skrambiWT.extractCorrections();
        Bundle bundle = new Bundle();
        bundle.putString("title", "I LIKE B..");
        bundle.putStringArray("arguments", arr);
        CustomDialog customDialog = new CustomDialog();
        customDialog.setArguments(bundle);
        customDialog.show(getFragmentManager(), "0");
        */
        InputValidator validator = new InputValidator();
        if(validator.validate(input, extended)) {
            prepareResultFragment(validator.createUrl(input, extended));
        }

        // TODO: errorhandling
    }

    @Override
    public void onAboutCallback() {

    }

    @Override
    public void onCacheCallback(Object item) {
        makeToast("CacheCallback");
    }

    @Override
    public void onDebugCallback(String debug) {
        makeToast(debug);
    }
}
