package com.williamhill.weather;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.williamhill.weather.data.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * An activity representing a list of Cities. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CityDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CityListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private SimpleItemRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent addCityActivityIntent = new Intent(CityListActivity.this, AddCityActivity.class);
            startActivityForResult(addCityActivityIntent, AddCityActivity.REQUEST_CODE);
        });

        if (findViewById(R.id.city_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        RecyclerView recyclerView = findViewById(R.id.city_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mAdapter = new SimpleItemRecyclerViewAdapter(this, new ArrayList<>(), mTwoPane);
        setupRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddCityActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    String city = uri.toString();


                    WeatherService.getWeatherByZipCode(city).subscribe(new Observer<CurrentWeather>() {

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(CurrentWeather value) {
                            runOnUiThread(() -> mAdapter.addCity(value));
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            }
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(mAdapter);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final CityListActivity mParentActivity;
        private final List<CurrentWeather> mCityList;
        private final boolean mTwoPane;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentWeather city = (CurrentWeather) view.getTag();

                Log.d("SFJL", city.toString());

                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(CityDetailFragment.ARG_CITY, city.name);
                    arguments.putFloat(CityDetailFragment.ARG_CURRENT_TEMP, city.main.temp);
                    arguments.putInt(CityDetailFragment.ARG_HUMIDITY, city.main.humidity);
                    arguments.putInt(CityDetailFragment.ARG_PRESSURE, city.main.pressure);
                    arguments.putInt(CityDetailFragment.ARG_MAX_TEMP, city.main.tempMax);
                    arguments.putInt(CityDetailFragment.ARG_MIN_TEMP, city.main.tempMin);
                    CityDetailFragment fragment = new CityDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.city_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CityDetailActivity.class);
                    intent.putExtra(CityDetailFragment.ARG_CITY, city.name);
                    intent.putExtra(CityDetailFragment.ARG_CURRENT_TEMP, city.main.temp);
                    intent.putExtra(CityDetailFragment.ARG_HUMIDITY, city.main.humidity);
                    intent.putExtra(CityDetailFragment.ARG_PRESSURE, city.main.pressure);
                    intent.putExtra(CityDetailFragment.ARG_MAX_TEMP, city.main.tempMax);
                    intent.putExtra(CityDetailFragment.ARG_MIN_TEMP, city.main.tempMin);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(CityListActivity parent,
                                      List<CurrentWeather> items,
                                      boolean twoPane) {
            mCityList = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.city_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mIdView.setText(mCityList.get(position).name);

            holder.itemView.setTag(mCityList.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
            holder.itemView.setOnLongClickListener(v -> {
                mCityList.remove(position);
                notifyItemChanged(position);
                notifyItemRangeChanged(position, mCityList.size());
                return true;
            });
        }

        @Override
        public int getItemCount() {
            return mCityList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.city);
            }
        }

        public void addCity(CurrentWeather city) {
            mCityList.add(city);
            notifyDataSetChanged();
        }
    }
}
