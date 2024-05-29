package com.example.nike.Views.Profile.SettingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nike.R;

public class TermOfSaleFragment extends Fragment {

    public TermOfSaleFragment() {
    }

    public static TermOfSaleFragment newInstance() {
        return new TermOfSaleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_term_of_sale, container, false);
    }
}