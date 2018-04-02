package com.williamhill.weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class AddCityActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    // ================================================================================
    // Properties
    // ================================================================================

    static final int REQUEST_CODE = 102;

    SearchView searchView;
    Button useCurrentLocationButton;

    private Location mLocation;
    private GoogleApiClient mGoogleApiClient;

    protected boolean permissionToAccessLocationAccepted = false;

    // ================================================================================
    // Lifecycle
    // ================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        useCurrentLocationButton = findViewById(R.id.use_current_location);
        useCurrentLocationButton.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },200);
                return;
            }
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                finishActivityWithResult(mLocation.toString());
            } else {
                Toast.makeText(this, "Can't find your location!", Toast.LENGTH_SHORT).show();
            }
        });

        searchView = findViewById(R.id.add_city_searchview);
        enableEntireSearchViewToBeClickable();
        searchView.setOnQueryTextListener(queryTextListener);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)               //calls back to this class when connects
                .addOnConnectionFailedListener(this)        //calls back to this class when failed
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(AddCityActivity.class.getSimpleName(), "GoogleApiClient connection has been established");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(AddCityActivity.class.getSimpleName(), "GoogleApiClient connection has been suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(AddCityActivity.class.getSimpleName(), "GoogleApiClient connection has failed");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0) {
            return;
        }
        switch (requestCode){
            case 200:
                permissionToAccessLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
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
