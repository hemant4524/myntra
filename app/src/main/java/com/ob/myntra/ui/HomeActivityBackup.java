package com.ob.myntra.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ob.myntra.R;
import com.ob.myntra.ui.fragment.MainMenuFragment;
import com.ob.myntra.ui.lib.search.MaterialSearchView;

import java.util.ArrayList;

public class HomeActivityBackup extends AppCompatActivity {
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /***
         * Change navigation bar color
         */
       /* Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(getResources().getColor(R.color.navBarColor));
        }*/

        setContentView(R.layout.activity_home);

        // Set a Toolbar to replace the ActionBar.
      //  setToolBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewOpened() {
                // Do something once the view is open.
            }

            @Override
            public void onSearchViewClosed() {
                // Do something once the view is closed.
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Do something when the suggestion list is clicked.
                String suggestion = searchView.getSuggestionAtPosition(position);

                searchView.setQuery(suggestion, false);
            }
        });



//        searchView.setTintAlpha(200);
        searchView.adjustTintAlpha(0.8f);

        final Context context = this;
        searchView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "Long clicked position: " + i, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        searchView.setOnVoiceClickedListener(new MaterialSearchView.OnVoiceClickedListener() {
            @Override
            public void onVoiceClicked() {
                Toast.makeText(context, "Voice clicked!", Toast.LENGTH_SHORT).show();
            }
        });






        // Set drawer menu adapter and list
        setDrawerMenuAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle toolbar item clicks here. It'll
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                // Open the search view on the menu item click.

                searchView.openSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        searchView.clearSuggestions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.activityResumed();
        String[] arr = getResources().getStringArray(R.array.suggestions);

        searchView.addSuggestions(arr);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isOpen()) {
            // Close the search on the back button press.
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setDrawerMenuAdapter() {

        // Add fragment in drawer menu

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        fragmentTransaction.add(R.id.ah_flDrawerContainer, mainMenuFragment);
        fragmentTransaction.commit();

    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setTitle("Myntra");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.menu_font_color, null));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.menu_font_color));
        }
        setSupportActionBar(toolbar);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = null;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            // Navigation drawer change icon
            // Reference link : http://stackoverflow.com/questions/29558303/change-icon-of-navigation-drawer
            //
            drawerToggle = new ActionBarDrawerToggle(HomeActivityBackup.this, drawerLayout, R.string.app_name, R.string.app_name) {


                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);

                }

                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
            drawerToggle.setDrawerIndicatorEnabled(false);

            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_black_24dp, getTheme());

            drawerToggle.setHomeAsUpIndicator(drawable);


            // Click on drawer icon
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            // Add search icon in toolbar

        }

    }



}
