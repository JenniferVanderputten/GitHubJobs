package com.jpvander.githubjobs.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public BaseFragment() {
        // Required empty public constructor
    }

    public boolean shouldShowMenuAdd() { return false; }
    public boolean shouldShowMenuLocation() { return false; }
    public void setLocality(String locality) { }
}
