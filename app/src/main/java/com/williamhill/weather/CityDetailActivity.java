package com.williamhill.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

/**
 * An activity representing a single City detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CityListActivity}.
 */
public class CityDetailActivity extends AppCompatActivity {

    // ================================================================================
    // Lifecycle
    // ================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CityDetailFragment.ARG_CITY, getIntent().getStringExtra(CityDetailFragment.ARG_CITY));
            arguments.putFloat(CityDetailFragment.ARG_CURRENT_TEMP, getIntent().getFloatExtra(CityDetailFragment.ARG_CURRENT_TEMP, 0f));
            arguments.putInt(CityDetailFragment.ARG_HUMIDITY, getIntent().getIntExtra(CityDetailFragment.ARG_HUMIDITY, 0));
            arguments.putInt(CityDetailFragment.ARG_PRESSURE, getIntent().getIntExtra(CityDetailFragment.ARG_PRESSURE, 0));
            arguments.putInt(CityDetailFragment.ARG_MAX_TEMP, getIntent().getIntExtra(CityDetailFragment.ARG_MAX_TEMP, 0));
            arguments.putInt(CityDetailFragment.ARG_MIN_TEMP, getIntent().getIntExtra(CityDetailFragment.ARG_MIN_TEMP, 0));
            CityDetailFragment fragment = new CityDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.city_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, CityListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
