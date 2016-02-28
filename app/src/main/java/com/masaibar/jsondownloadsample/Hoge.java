package com.masaibar.jsondownloadsample;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by masaibar on 2016/02/28.
 */
public class Hoge {

    @SerializedName("hoge")
    private String mHoge;

    public Hoge(String hoge) {
        mHoge = hoge;
        Log.d("!!!", "hoge construct");
    }

    public String getHoge() {
        return mHoge;
    }
}
