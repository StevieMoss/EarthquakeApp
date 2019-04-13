package com.example.smmobilecoursework;

/*Name: Stephen Moss
StudentID: S1716583
Program of Study: Computing*/

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class EarthquakeItems implements Parcelable {

    private String title, description, latitude, longtitude, date, depth, magnitude;

    public EarthquakeItems(String title, String description, String latitude, String longtitude, String date, String depth, String magnitude) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.date = date;
        this.depth = depth;
        this.magnitude = magnitude;
    }

    public EarthquakeItems() {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.date = date;
        this.depth = depth;
        this.magnitude = magnitude;
    }

    @Override
    public String toString() {
        return " Date: " + this.date + ", Depth: " + this.depth + ", Magnitude:" + this.magnitude;
    }


    protected EarthquakeItems(Parcel in) {
        title = in.readString();
        description = in.readString();
        latitude = in.readString();
        longtitude = in.readString();
        date = in.readString();
        depth = in.readString();
        magnitude = in.readString();
    }

    public static final Creator<EarthquakeItems> CREATOR = new Creator<EarthquakeItems>() {
        @Override
        public EarthquakeItems createFromParcel(Parcel in) {
            return new EarthquakeItems(in);
        }

        @Override
        public EarthquakeItems[] newArray(int size) {
            return new EarthquakeItems[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(latitude);
        dest.writeString(longtitude);
        dest.writeString(date);
        dest.writeString(depth);
        dest.writeString(magnitude);
    }

    /**
     * getters and setters
     * @return
     */
    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
         return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        int from = getDescription().indexOf("Lon: ");
         int to = getDescription().indexOf("; ", from);
        return getDescription().substring(from);
       //return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getDate() {
        int from = getDescription().indexOf("Depth:" );
        int to = getDescription().indexOf(": ",  from);
        return getDescription().substring(from);
        //return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepth() {

        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getMagnitude() {

        //int from = getDescription().indexOf("Depth: " );
        //int to = getDescription().indexOf(": ", from);
       // return getDescription().substring(from);
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

}