package com.adityaamk.youniversity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class University implements Serializable {
    private String name;

    public University(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
