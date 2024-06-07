package com.example.nike.Views.Profile.SettingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.nike.R;

public class TermOfSaleFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_term_of_sale, container, false);
        addControls(view);
        addEvents();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}