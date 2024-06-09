package com.example.nike.Views.Profile.SettingFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.FragmentUtils;
import com.example.nike.Model.UserAccount;
import com.example.nike.R;
import com.example.nike.Views.LoginFrame;
import com.example.nike.Views.LoginLobby;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SettingFragment extends Fragment {

    private static final String get_email = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sharedPreferences;
    CardView cs_email,cs_phone,cs_about_version,cs_terms_of_use,cs_terms_of_sale,cs_privacy_policy,cs_returns_policy,cs_logout;
    TextView email,phone;
    ImageButton btnBack;
    private void addControls(View view)
    {
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        cs_email = view.findViewById(R.id.cs_email);
        cs_phone = view.findViewById(R.id.cs_phone);
        cs_about_version = view.findViewById(R.id.cs_about_version);
        cs_terms_of_use = view.findViewById(R.id.cs_terms_of_use);
        cs_terms_of_sale = view.findViewById(R.id.cs_terms_of_sale);
        cs_privacy_policy = view.findViewById(R.id.cs_privacy_policy);
        cs_returns_policy = view.findViewById(R.id.cs_returns_policy);
        cs_logout = view.findViewById(R.id.cs_logout);
        btnBack = view.findViewById(R.id.btnBack);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        email.setText(sharedPreferences.getString("email",null));
        UserAccount us = UserAccountHandler.getUserByEmail(email.getText().toString());
        String get_phone = us.getPhoneNumber();
        if(get_phone != null)
            phone.setText(get_phone);
        else
            phone.setText("Add");
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
        cs_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(v);
            }
        });
        cs_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendEmail = sharedPreferences.getString("email",null);
                EditEmailFragment editEmailFragment = EditEmailFragment.newInstance(sendEmail);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,editEmailFragment,R.id.frameLayout);
            }
        });

        //About this version
        cs_about_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutVersionFragment aboutVersionFragment = AboutVersionFragment.newInstance();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm, aboutVersionFragment, R.id.frameLayout);
            }
        });

        //Terms of Use
        cs_terms_of_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermsOfUserFragment termsOfUserFragment = TermsOfUserFragment.newInstance();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm, termsOfUserFragment, R.id.frameLayout);
            }
        });

        //Terms of Sale
        cs_terms_of_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermOfSaleFragment termOfSaleFragment = TermOfSaleFragment.newInstance();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm, termOfSaleFragment, R.id.frameLayout);
            }
        });

        //Privacy Policy
        cs_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyPolicyFragment privacyPolicyFragment = PrivacyPolicyFragment.newInstance();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm, privacyPolicyFragment, R.id.frameLayout);
            }
        });

        cs_returns_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.nike.com/help/a/returns-policy");
            }
        });
    }

    private void showAlertDialog(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.custom_dialog_layout,null);
        builder.setView(dialogLayout);
        Button btn_cancel,btn_logout;
        btn_cancel = dialogLayout.findViewById(R.id.btn_cancel);
        btn_logout = dialogLayout.findViewById(R.id.btn_logout);
        AlertDialog alertDialog = builder.create();
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutGoogleAccount(v);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
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
                Intent intent = new Intent(view.getContext(), LoginLobby.class);
                startActivity(intent);
            }
        });
    }
    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(get_email, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(get_email);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    //Mở link từ app sang web nha
    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // Kiểm tra xem thiết bị có ứng dụng nào có thể xử lý Intent này hay không
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Mở trình duyệt web mặc định
            startActivity(intent);
        } else {
            // Nếu không tìm thấy trình duyệt web, bạn có thể cung cấp một thông báo cho người dùng
            Toast.makeText(getContext(), "Không tìm thấy trình duyệt web", Toast.LENGTH_SHORT).show();
        }
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