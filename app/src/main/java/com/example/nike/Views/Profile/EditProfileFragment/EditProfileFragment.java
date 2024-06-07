package com.example.nike.Views.Profile.EditProfileFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Model.UserAccount;
import com.example.nike.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SharedPreferences sharedPreferences;
    TextView cancel,save;
    ImageView user_img;
    EditText first_name,last_name,phone_number,address;
    private String email,login_type;
    private static final String PHONE_NUMBER_PATTERN = "^0[0-9]{9,10}$";
    private boolean isValidPhoneNumber(String phoneNumber)
    {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void addControls(View view)
    {
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        cancel = view.findViewById(R.id.cancel);
        save = view.findViewById(R.id.save);
        user_img = view.findViewById(R.id.user_img);
        first_name = view.findViewById(R.id.first_name);
        last_name = view.findViewById(R.id.last_name);
        phone_number = view.findViewById(R.id.phone_number);
        address = view.findViewById(R.id.address);
        save.setEnabled(false);
    }
    private void addEvents()
    {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String old_phone_number = phone_number.getText().toString();
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                save.setTextColor(Color.BLACK);
                save.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        first_name.addTextChangedListener(textWatcher);
        last_name.addTextChangedListener(textWatcher);
        phone_number.addTextChangedListener(textWatcher);
        address.addTextChangedListener(textWatcher);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phone_number.getText().toString();
                if(!phone.isEmpty())
                {
                    if(!isValidPhoneNumber(phone))
                        Toast.makeText(getContext(), "Số điên thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
                    else if(UserAccountHandler.checkUserPhoneExists(phone))
                    {
                        if(old_phone_number.equals(phone))
                        {
                            UserAccountHandler.editUserProfile(email,first_name.getText().toString(),last_name.getText().toString(),phone,address.getText().toString());
                            onBackPressed();
                        }
                        else
                            Toast.makeText(getContext(), "Số điện thoại này đã được đăng kí", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        UserAccountHandler.editUserProfile(email,first_name.getText().toString(),last_name.getText().toString(),phone,address.getText().toString());
                        onBackPressed();
                    }
                }
                else
                {
                    UserAccountHandler.editUserProfile(email,first_name.getText().toString(),last_name.getText().toString(),phone,address.getText().toString());
                    onBackPressed();
                }
            }
        });
    }

    private void loadDataUser()
    {
        email = sharedPreferences.getString("email",null);
        login_type = sharedPreferences.getString("login_type",null);
        if(sharedPreferences.getString("login_type",null).equals("google"))
        {
            String img_url = sharedPreferences.getString("user_img",null);
            if(!img_url.isEmpty())
            {
                Glide.with(this).load(img_url).into(user_img);
            }
        }
        UserAccount userAccount = UserAccountHandler.getUserByEmail(email);
        first_name.setText(userAccount.getFirst_name());
        last_name.setText(userAccount.getLast_name());
        phone_number.setText(userAccount.getPhoneNumber());
        address.setText(userAccount.getAddress());

    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
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
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Animation sildeInBottom = AnimationUtils.loadAnimation(view.getContext(),R.anim.slide_in_bottom);
        view.startAnimation(sildeInBottom);
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