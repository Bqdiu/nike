package com.example.nike.Views.Profile.SettingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.nike.R;

public class TermsOfUserFragment extends Fragment {

    ImageButton btnBack;
    private void addControls(View view)
    {
        btnBack = view.findViewById(R.id.btnBack);
    }
    private void addEvents()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
    }

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
        View view = inflater.inflate(R.layout.fragment_terms_of_user, container, false);
        addControls(view);
        addEvents();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}