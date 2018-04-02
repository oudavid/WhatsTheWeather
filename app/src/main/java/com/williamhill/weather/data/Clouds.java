
package com.williamhill.weather.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    @Expose
    public Integer all;

    @Override
    public String toString() {
        return "Clouds{" +
                "all=" + all +
                '}';
    }
}
