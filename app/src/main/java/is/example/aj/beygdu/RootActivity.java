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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import is.example.aj.beygdu.Async.BinAsyncTask;
import is.example.aj.beygdu.Async.CacheAsyncTask;
import is.example.aj.beygdu.Async.SkrambiAsyncTask;
import is.example.aj.beygdu.Fragments.AboutFragment;
import is.example.aj.beygdu.Fragments.AuthorFragment;
import is.example.aj.beygdu.Fragments.CacheFragment;
import is.example.aj.beygdu.Fragments.MailFragment;
import is.example.aj.beygdu.Fragments.MapFragment;
import is.example.aj.beygdu.Fragments.ResultFragment;
import is.example.aj.beygdu.Fragments.SearchFragment;
import is.example.aj.beygdu.Parser.WordResult;
import is.example.aj.beygdu.Skrambi.SkrambiWT;
import is.example.aj.beygdu.UIElements.CustomDialog;
import is.example.aj.beygdu.Utils.InputValidator;

public class RootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback, CustomDialog.CustomDialogListener {

    private boolean isCorrection = false;

    private void toggleCorrectionState() {
        isCorrection = !isCorrection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            // Does nothing i think?
        }

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
            selectItem(3);
        } else if (id == R.id.drawer_contact) {
            selectItem(4);
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
           ft.replace(R.id.frame_layout, new AboutFragment());
        }
        else if(position == 2) {
            CacheFragment cacheFragment = new CacheFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("arguments", getCacheList());

            cacheFragment.setArguments(bundle);

            ft.replace(R.id.frame_layout, cacheFragment);
        }
        else if(position == 3) {
            ft.replace(R.id.frame_layout, new AuthorFragment());
        }
        else if(position == 4) {
            ft.replace(R.id.frame_layout, new MailFragment());
        }
        // Map
        else if(position == 5) {
            ft.replace(R.id.frame_layout, new MapFragment());
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


    private ArrayList<WordResult> getCacheList() {
        try {
            return (ArrayList<WordResult>) new CacheAsyncTask(this).execute("1").get();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onDialogClick(String str, int id) {

        try {
            WordResult wR = new BinAsyncTask(this).execute(InputValidator.createUrl(id)).get();
            if(wR != null) {
                wR.setSearchWord(str);
                prepareSingleHitSearch(wR);
            }
            else {
                makeToast("onDialogClick - Wordresult is null");
            }
        }
        catch (Exception e) {
            makeToast("onDialogClick - Failed to create WordResult from id");
        }
    }

    @Override
    public void onDialogClick(String str) {
        prepareSearchCallback(str, false);
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
        prepareSearchCallback(input, extended);
    }

    @Override
    public void onAboutCallback() {

    }

    @Override
    public void onCacheCallback(Object item) {
        makeToast("CacheCallback");
    }

    @Override
    public void onFragmentSwitch(int state) {
        selectItem(state);
    }

    @Override
    public void onDebugCallback(String debug) {
        makeToast(debug);
    }

    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
    }

    //////
    //
    //////

    private void prepareSkrambiErrorCheck(String str) {

        try {
            SkrambiWT skrambiWT = new SkrambiWT(this, str);
            String[] possibleCorrections = skrambiWT.extractCorrections();
            if(possibleCorrections != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("state", 1);
                bundle.putString("title", "I LIKE B..");
                bundle.putStringArray("arguments", possibleCorrections);
                bundle.putIntArray("responses", null);
                CustomDialog customDialog = new CustomDialog();
                customDialog.setArguments(bundle);
                customDialog.show(getFragmentManager(), "1");
            }
            else {
                makeToast("prepareSkrambiErrorCheck - no corrections found");
            }
        }
        catch (Exception e) {

        }

    }

    private void prepareMultiHitSearch(WordResult wordResult) {
        Bundle bundle = new Bundle();
        bundle.putString("title", R.string.multiHitPrefix + wordResult.getSearchWord() + R.string.multiHitPostFix);
        bundle.putStringArray("arguments", wordResult.getMultiHitDescriptions());
        bundle.putIntArray("responses", wordResult.getMultiHitIds());
        CustomDialog customDialog = new CustomDialog();
        customDialog.setArguments(bundle);
        customDialog.show(getFragmentManager(), "0");
    }

    private void prepareSingleHitSearch(WordResult wordResult) {

        try {
            String cache = (String) new CacheAsyncTask(this).execute("0", wordResult).get();
            makeToast(cache);
        }
        catch (Exception e) {
            makeToast("prepareSingleHitSearch - failed to save result to cache");
        }

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("WordResult", wordResult);
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(bundle);
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();
    }

    private void prepareSearchCallback(String str, boolean extended) {
        if(InputValidator.validate(str, extended)) {
            try {
                WordResult wR = new BinAsyncTask(this).execute(InputValidator.createUrl(str, extended)).get();
                if(wR != null) {
                    if(wR.getDescription().equals(WordResult.singleHit)) {
                        wR.setSearchWord(str);
                        prepareSingleHitSearch(wR);
                    }
                    else if(wR.getDescription().equals(WordResult.multiHit)) {
                        wR.setSearchWord(str);
                        prepareMultiHitSearch(wR);
                    }
                    else {
                        prepareSkrambiErrorCheck(str);
                    }
                }
                else {
                    makeToast("prepareSearchCallback - wR is null");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                makeToast("prepareSearchCallback try-catch failed");
            }
        }
        else {
            makeToast("prepareSearchCallback - input illegal");
        }
    }
}
