package com.williamhill.weather;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

public class AddCityActivity extends AppCompatActivity {

    // ================================================================================
    // Properties
    // ================================================================================

    static final int REQUEST_CODE = 101;

    SearchView searchView;

    // ================================================================================
    // Lifecycle
    // ================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        searchView = findViewById(R.id.add_city_searchview);
        enableEntireSearchViewToBeClickable();
        searchView.setOnQueryTextListener(queryTextListener);
    }

    // ================================================================================
    // Private Methods
    // ================================================================================

    /**
     * https://stackoverflow.com/questions/30455723/android-make-whole-search-bar-clickable
     */
    private void enableEntireSearchViewToBeClickable() {
        searchView.setOnClickListener(v -> searchView.setIconified(false));
    }

    private void finishActivityWithResult(String city) {
        Intent data = new Intent();
        data.setData(Uri.parse(city));
        setResult(RESULT_OK, data);
        finish();
    }

    // ================================================================================
    // Listeners
    // ================================================================================

    private final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            AddCityActivity.this.finishActivityWithResult(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };
}
