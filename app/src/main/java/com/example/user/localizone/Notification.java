package com.example.user.localizone;

/**
 * Created by amandine on 12/25/15.
 */
public class Notification{
    private String latitude;
    private String longitude;
    private String date;

        public Notification(String latitude, String longitude, String date) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.date = date;
        }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


}
