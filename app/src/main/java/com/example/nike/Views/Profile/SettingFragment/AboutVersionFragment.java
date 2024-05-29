package com.example.nike.Views.Profile.SettingFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nike.R;

public class AboutVersionFragment extends Fragment {

    public AboutVersionFragment() {
        // Required empty public constructor
    }

    public static AboutVersionFragment newInstance() {
        return new AboutVersionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_version, container, false);
    }
}
