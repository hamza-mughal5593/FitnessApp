package com.mtechsoft.fitmy.v1.common;

import android.location.Location;

import java.util.Date;

public class LocationTs {
    private Location location;
    private Date timestamp;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
