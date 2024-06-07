package com.example.nike.Views.Profile.SettingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.nike.R;

public class PrivacyPolicyFragment extends Fragment {
    TextView txt1, txt2, txt3, txt4, txt5, txt6;
    TextView link1, link2, link3, link4, link5, link6;
    ScrollView scrollView;
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
    public PrivacyPolicyFragment() {
    }

    public static PrivacyPolicyFragment newInstance(){
        return new PrivacyPolicyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);

        txt1 = view.findViewById(R.id.txt1);
        txt2 = view.findViewById(R.id.txt2);
        txt3 = view.findViewById(R.id.txt3);
        txt4 = view.findViewById(R.id.txt4);
        txt5 = view.findViewById(R.id.txt5);
        txt6 = view.findViewById(R.id.txt6);

        link1 = view.findViewById(R.id.link1);
        link2 = view.findViewById(R.id.link2);
        link3 = view.findViewById(R.id.link3);
        link4 = view.findViewById(R.id.link4);
        link5 = view.findViewById(R.id.link5);
        link6 = view.findViewById(R.id.link6);

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                link1.getLocationOnScreen(location);
                int y = location[1];

                scrollView = view.findViewById(R.id.scrollView);
                scrollView.smoothScrollTo(0, y);
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                link2.getLocationOnScreen(location);
                int y = location[1];

                scrollView = view.findViewById(R.id.scrollView);
                scrollView.smoothScrollTo(0, y);
            }
        });

        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                link3.getLocationOnScreen(location);
                int y = location[1];

                scrollView = view.findViewById(R.id.scrollView);
                scrollView.smoothScrollTo(0, y);
            }
        });

        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                link4.getLocationOnScreen(location);
                int y = location[1];

                scrollView = view.findViewById(R.id.scrollView);
                scrollView.smoothScrollTo(0, y);
            }
        });

        txt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                link5.getLocationOnScreen(location);
                int y = location[1];

                scrollView = view.findViewById(R.id.scrollView);
                scrollView.smoothScrollTo(0, y);
            }
        });

        txt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                link6.getLocationOnScreen(location);
                int y = location[1];

                scrollView = view.findViewById(R.id.scrollView);
                scrollView.smoothScrollTo(0, y);
            }
        });
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