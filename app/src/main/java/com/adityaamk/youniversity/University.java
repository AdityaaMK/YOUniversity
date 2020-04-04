package com.adityaamk.youniversity;

import android.os.Parcel;
import android.os.Parcelable;

public class University implements Parcelable {
    String name;
    double satHigh, satLow, actHigh, actLow;

    public University(String name, double satHigh, double satLow, double actHigh, double actLow){
        this.name = name;
        this.satHigh = satHigh;
        this.satLow = satLow;
        this.actHigh = actHigh;
        this.actLow = actLow;
    }

    public String getName() {
        return name;
    }

    public double getSatHigh() {
        return satHigh;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSatHigh(double satHigh) {
        this.satHigh = satHigh;
    }

    public void setSatLow(double satLow) {
        this.satLow = satLow;
    }

    public void setActHigh(double actHigh) {
        this.actHigh = actHigh;
    }

    public void setActLow(double actLow) {
        this.actLow = actLow;
    }

    public double getSatLow() {
        return satLow;
    }

    public double getActHigh() {
        return actHigh;
    }

    public double getActLow() {
        return actLow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(satHigh);
        dest.writeDouble(satLow);
        dest.writeDouble(actHigh);
        dest.writeDouble(actLow);
    }
}
