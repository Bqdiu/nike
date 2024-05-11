package com.example.nike.Views.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.R;
import com.example.nike.Views.Login;
import com.example.nike.Views.Register;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;
import java.util.zip.Inflater;
public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private SharedPreferences sharedPreferences;
    CardView cv_inbox;
    TextView user_name;

    //test logout
    CardView cv_setting;
    private String login_type;

    public ProfileFragment() {
        // Required empty public constructor
    }

    private void addControls(View view)
    {
        user_name = view.findViewById(R.id.user_name);
        cv_inbox = view.findViewById(R.id.cs_inbox);
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        cv_setting = view.findViewById(R.id.cv_setting);
    }

    private void addEvents()
    {
        cv_inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileFragmentInboxListener) getActivity()).loadInboxFragment();
            }
        });
        cv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutGoogleAccount(v);

            }
        });
    }

    private void loadDataUser()
    {
        String us = sharedPreferences.getString("first_name",null);
        login_type = sharedPreferences.getString("login_type",null);
        user_name.setText(us);
    }

    private void signOutGoogleAccount(View view)
    {
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(view.getContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build());
        googleSignInClient.signOut().addOnCompleteListener(ActivityCompat.getMainExecutor(view.getContext()), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Delete user info from SharePreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
        });
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
     
}