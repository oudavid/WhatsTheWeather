package com.williamhill.weather;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A fragment representing a single City detail screen.
 * This fragment is either contained in a {@link CityListActivity}
 * in two-pane mode (on tablets) or a {@link CityDetailActivity}
 * on handsets.
 */
public class CityDetailFragment extends Fragment {

    // ================================================================================
    // Properties
    // ================================================================================

    public static final String ARG_CITY = "city";
    public static final String ARG_CURRENT_TEMP = "current_temp";
    public static final String ARG_HUMIDITY = "humidity";
    public static final String ARG_PRESSURE = "pressure";
    public static final String ARG_MAX_TEMP = "max_temp";
    public static final String ARG_MIN_TEMP = "min_temp";

    private String city;
    private float currentTemp;
    private int humidity;
    private int pressure;
    private int maxTemp;
    private int minTemp;

    // ================================================================================
    // Constructors
    // ================================================================================

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityDetailFragment() {
    }

    // ================================================================================
    // Lifecycle
    // ================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        city = getArguments().getString(ARG_CITY);
        currentTemp = getArguments().getFloat(ARG_CURRENT_TEMP);
        humidity = getArguments().getInt(ARG_HUMIDITY);
        pressure = getArguments().getInt(ARG_PRESSURE);
        maxTemp = getArguments().getInt(ARG_MAX_TEMP);
        minTemp = getArguments().getInt(ARG_MIN_TEMP);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(city);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.city_detail, container, false);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout == null) {
            ((TextView) rootView.findViewById(R.id.city_name)).setText(city);
        }

        ((TextView) rootView.findViewById(R.id.current_temp)).setText("" + currentTemp + " K");
        ((TextView) rootView.findViewById(R.id.humidity)).setText("" + humidity);
        ((TextView) rootView.findViewById(R.id.pressure)).setText("" + pressure);
        ((TextView) rootView.findViewById(R.id.max_temp)).setText("" + maxTemp);
        ((TextView) rootView.findViewById(R.id.min_temp)).setText("" + minTemp);

        return rootView;
    }
}
