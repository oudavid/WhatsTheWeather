
package com.williamhill.weather.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("coord")
    @Expose
    public Coord coord;
    @SerializedName("main")
    @Expose
    public Main main;
    @SerializedName("dt")
    @Expose
    public Integer dt;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("sys")
    @Expose
    public Sys sys;
    @SerializedName("rain")
    @Expose
    public Object rain;
    @SerializedName("snow")
    @Expose
    public Object snow;
    @SerializedName("clouds")
    @Expose
    public Clouds clouds;
    @SerializedName("weather")
    @Expose
    public java.util.List<Weather> weather = null;

    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coord=" + coord +
                ", main=" + main +
                ", dt=" + dt +
                ", wind=" + wind +
                ", sys=" + sys +
                ", rain=" + rain +
                ", snow=" + snow +
                ", clouds=" + clouds +
                ", weather=" + weather +
                '}';
    }
}
