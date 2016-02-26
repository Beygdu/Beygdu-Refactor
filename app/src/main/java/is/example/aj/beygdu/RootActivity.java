package is.example.aj.beygdu;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
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
import is.example.aj.beygdu.UIElements.NotificationDialog;
import is.example.aj.beygdu.Utils.InputValidator;

/**
 * @author Snær, Jón Friðrik, Daníel, Arnar,
 * @since 05.11.14
 * @version 2.0
 *
 * Refactored 01.16 - Arnar
 *
 * The initial and only activity of the application. Holds a navigation drawer and a frame layout.
 * The frame layout is populated by the desired fragment selected by the user.
 *
 * The activity handles EVERY logic AND data changes/transactions***, the fragments are only for
 * displaying content and grabbing user input
 *
 * *** With the exception of the ResultFragment - it needs to handle its own logic changes
 * for result-filtering purposes. The fragment can be implemented to let the activity handle the
 * changes, but that requires all fragment to use the same (dynamically changing) menu, which
 * is (probably) redundant
 *
 */
public class RootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback, CustomDialog.CustomDialogListener {

    // Control of FragmentManager.addToBackStack(String tag)
    private final String backStackTracer = "SearchFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Handle orientation change
        if(savedInstanceState != null) {
            // (Probably) Nothing needs to be saved/re-instantiated here
            // Is here as a rule of thumb
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

        // Open the "home" fragment of the application
        selectItem(0);
    }

    // TODO : (if desired) change application quit from one back-press (if the user is
    // TODO : located at the "home" fragment) to two back-presses
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if(getSupportFragmentManager().getBackStackEntryCount() == 1) {
            // Only the "home" fragment is in the stack
            finish();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The activity does not need to handle any menu item selection,
        // although, in some cases, it handles logic from fragment
        // menu item clicks

        return super.onOptionsItemSelected(item);
    }

    /**
     * A middleman in the applications fragment transactions
     * Not used for direct transactions due to support fragments not
     * found in the navigation drawer (MapFragment,...)
     *
     * @param item position of the item clicked in the nav-drawer
     * @return true for all instances
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

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

    /**
     * 0 = SearchFragment
     * 1 = AboutFragment
     * 2 = CacheFragment
     * 3 = AuthorFragment
     * 4 = MailFragment (a badly named Contact-fragment)
     * 5 = MapFragment (not in drawer)
     *
     * @param position Position id for the desired fragment to be shown
     */
    private void selectItem(int position) {

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        makeToast("SupportFragment holds : " + getSupportFragmentManager().getBackStackEntryCount() + " fragments");

        if(position == 0) {
            /*
            ft.replace(R.id.frame_layout, SearchFragment.newInstance());
            if(getFragmentManager().getBackStackEntryCount() != 0) {
                // TODO : clear the stack
                makeToast("Stack is not empty");
            }
            ft.addToBackStack(backStackTracer);
            */
            if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
                ft.replace(R.id.frame_layout, SearchFragment.newInstance());
                ft.addToBackStack(backStackTracer);
            }
            else {
                getSupportFragmentManager().popBackStack(backStackTracer, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.replace(R.id.frame_layout, SearchFragment.newInstance());
                ft.addToBackStack(backStackTracer);
            }
        }
        else if(position == 1) {
            ft.replace(R.id.frame_layout, AboutFragment.newInstance());
            ft.addToBackStack(null);
        }
        else if(position == 2) {
            CacheFragment cacheFragment = CacheFragment.newInstance();

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("arguments", getCacheList());

            cacheFragment.setArguments(bundle);

            ft.replace(R.id.frame_layout, cacheFragment);
            ft.addToBackStack(null);
        }
        else if(position == 3) {
            ft.replace(R.id.frame_layout, AuthorFragment.newInstance());
            ft.addToBackStack(null);
        }
        else if(position == 4) {
            ft.replace(R.id.frame_layout, MailFragment.newInstance());
            ft.addToBackStack(null);
        }
        // Map
        else if(position == 5) {
            ft.replace(R.id.frame_layout, MapFragment.newInstance());
            ft.addToBackStack(null);
        }

        ft.commit();
    }

    // For Debug purposes
    private void makeToast(String o) {
        Toast toast = Toast.makeText(getApplicationContext(), o, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     *
     * @return A list of result names gotten from the in-app database
     */
    private ArrayList<String> getCacheList() {
        try {
            return (ArrayList<String>) new CacheAsyncTask(this).execute("1").get();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handles CustomDialog callback on BIN ID search
     * This happens when multiple results are found on the initial search
     *
     * @param str Search-word corresponding to the id in question
     * @param id The id of the selected search-word
     */
    @Override
    public void onDialogClick(String str, int id) {

        try {
            WordResult wR = new BinAsyncTask(this).execute(InputValidator.createUrl(id)).get();
            if(wR != null) {
                wR.setSearchWord(str);
                prepareSingleHitSearch(wR);
            }
            else {
                // TODO : manage string resource
                //makeToast("onDialogClick - Wordresult is null");
                showNotification("onDialogClick (id) - BinAsync returns null", true);
            }
        }
        catch (Exception e) {
            // TODO : manage string resource
            //makeToast("onDialogClick - Failed to create WordResult from id");
            showNotification("onDialogClick (id) - BinAsync crashed", true);
        }
    }

    /**
     * Handles CustomDialog callback on SKRAMBI correction search
     * This happens when the user selects a correction gotten from the SKRAMBI
     * POST call
     *
     * @param str The desired search-word
     */
    @Override
    public void onDialogClick(String str) {
        prepareSearchCallback(str, false);
    }

    /**
     * Handles a normal SearchFragment callback
     * Passes user input from a desired search
     *
     * @param input The desired search-word
     * @param extended control tag - TRUE for extended search, false otherwise
     */
    @Override
    public void onSearchCallback(String input, boolean extended) {

        prepareSearchCallback(input, extended);
    }

    /**
     * This serves no purpose what-so-ever
     * Is here if callback is needed from AboutFragment
     */
    @Override
    public void onAboutCallback() {
    }

    /**
     * Handles callback from CacheFragment
     * Open the desired result chosen by the user
     *
     * @param item item to be displayed by a ResultFragment
     */
    @Override
    public void onCacheCallback(Object item) {
        // TODO : implement
        makeToast("CacheCallback");
    }

    /**
     * Handles non-direct fragment transactions
     *
     * @param state Integer id for the desired fragment to be displayed
     */
    @Override
    public void onFragmentSwitch(int state) {
        selectItem(state);
    }

    /**
     * See function name
     *
     * @param debug String to be toasted
     */
    @Override
    public void onDebugCallback(String debug) {
        makeToast(debug);
    }

    /**
     * Saves states for CORRECT orientation changes
     * @param instanceState Activity state
     */
    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        // (Probably) Nothing needs to be saved/re-instantiated here
        // Is here as a rule of thumb
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
                // TODO : manage string resource
                //makeToast("prepareSkrambiErrorCheck - no corrections found");
                showNotification("prepareSkrambi.. - No corrections found", false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            showNotification("prepareSkrambi.. - SkrambiAsync crashed", true);
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
                    // TODO : manage string resource
                    //makeToast("prepareSearchCallback - wR is null");
                    showNotification("prepareSearchCallback - wR is null", true);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                // TODO : manage string resource
                //makeToast("prepareSearchCallback try-catch failed");
                showNotification("prepareSearchCallback - BinAsync crashed", true);
            }
        }
        else {
            // TODO : manage string resource
            //makeToast("prepareSearchCallback - input illegal");
            showNotification("Illegal input", false);
        }
    }

    private void showNotification(String message, boolean isError) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isError", isError);
        bundle.putString("message", message);

        String tag = isError ? "1" : "0";

        NotificationDialog dialog = new NotificationDialog();
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), tag);
    }

}
