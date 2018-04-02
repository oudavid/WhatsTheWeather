package com.williamhill.weather;

import com.williamhill.weather.data.CurrentWeather;


import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by davidou on 4/1/18.
 */

//http://openweathermap.org/data/2.5/weather?q=98012&
// appid=b6907d289e10d714a6e88b30761fae22

public class WeatherService {
    private static final String APP_ID = "b6907d289e10d714a6e88b30761fae22";
    private static final String BASE_URL = "http://openweathermap.org/data/2.5/";

    private static class RetrofitFactory {

        private static Retrofit retrofit;

        private static Retrofit get() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                        .build();
            }
            return retrofit;
        }
    }

    public static Observable<CurrentWeather> getWeatherByZipCode(String zipCode) {
        return RetrofitFactory.get().create(WeatherApiService.class).getCurrentWeather(zipCode + ",us", APP_ID);
    }

    private interface WeatherApiService {
        @GET("weather")
        Observable<CurrentWeather> getCurrentWeather(@Query("q") String location, @Query("appid") String id);
    }
}
