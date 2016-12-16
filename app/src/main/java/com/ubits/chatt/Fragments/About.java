package com.ubits.chatt.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ubits.chatt.R;

/**
 * Created by Abdullah
 */

public class About extends Fragment {


    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle savedInstanceState) {

        View Localview = paramLayoutInflater.inflate(R.layout.about,null);
        return Localview;
    }
}
