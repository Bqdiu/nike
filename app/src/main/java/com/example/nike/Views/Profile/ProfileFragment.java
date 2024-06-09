package com.example.nike.Views.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nike.FragmentUtils;
import com.example.nike.R;
import com.example.nike.Views.Profile.EditProfileFragment.EditProfileFragment;
import com.example.nike.Views.Profile.InboxFragment.InboxFragment;
import com.example.nike.Views.Profile.OrderFragment.DetailOrderFragment;
import com.example.nike.Views.Profile.OrderFragment.OrderFragment;
import com.example.nike.Views.Profile.SettingFragment.SettingFragment;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private SharedPreferences sharedPreferences;
    CardView cv_inbox;
    TextView user_name;
    CardView cv_setting,cv_orders;
    AppCompatButton btn_editProfile;
    ImageView user_img;
    private String login_type;

    public ProfileFragment() {
        // Required empty public constructor
    }

    private void addControls(View view)
    {
        user_name = view.findViewById(R.id.user_name);
        cv_inbox = view.findViewById(R.id.cs_inbox);
        cv_orders = view.findViewById(R.id.cv_orders);
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        cv_setting = view.findViewById(R.id.cv_setting);
        btn_editProfile = view.findViewById(R.id.btn_editProfile);
        user_img = view.findViewById(R.id.user_img);
    }

    private void addEvents()
    {
        cv_inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InboxFragment inboxFragment = new InboxFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,inboxFragment,R.id.frameLayout);
            }
        });
        cv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingFragment settingFragment = new SettingFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,settingFragment,R.id.frameLayout);
            }
        });

        cv_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFragment orderFragment = new OrderFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,orderFragment,R.id.frameLayout);
            }
        });
        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,editProfileFragment,R.id.frameLayout);
            }
        });

    }

    private void loadDataUser()
    {
        String us = sharedPreferences.getString("first_name",null);
        login_type = sharedPreferences.getString("login_type",null);
        if(sharedPreferences.getString("login_type",null).equals("google"))
        {
            String img_url = sharedPreferences.getString("user_img",null);
            if(!img_url.isEmpty())
            {
                Glide.with(this).load(img_url).into(user_img);
            }
        }

        user_name.setText(us);
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        addControls(view);
        loadDataUser();
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