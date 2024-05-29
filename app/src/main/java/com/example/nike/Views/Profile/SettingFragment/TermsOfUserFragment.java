package com.example.nike.Views.Profile.SettingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nike.R;

public class TermsOfUserFragment extends Fragment {



    public TermsOfUserFragment() {
        // Required empty public constructor
    }

    public static TermsOfUserFragment newInstance() {

        return new TermsOfUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms_of_user, container, false);
    }
}