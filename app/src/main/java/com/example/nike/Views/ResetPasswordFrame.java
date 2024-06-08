package com.example.nike.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nike.Auth;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.security.NoSuchAlgorithmException;

public class ResetPasswordFrame extends AppCompatActivity {


    private ImageView btn_back;
    private TextInputLayout textInputLayoutNewPassword;
    private TextInputLayout textInputLayoutComfirmPassword;
    private TextInputEditText newPassword;
    private TextInputEditText comfirmPassword;
    private Button btn_submit;
    private String get_email = "";

    private void addControls()
    {
        btn_back = findViewById(R.id.btn_back);
        textInputLayoutNewPassword = findViewById(R.id.textInputLayoutNewPassword);
        textInputLayoutComfirmPassword = findViewById(R.id.textInputLayoutComfirmPassword);
        newPassword = findViewById(R.id.newPassword);
        comfirmPassword = findViewById(R.id.comfirmPassword);
        btn_submit = findViewById(R.id.btn_submit);
    }

    private void addEvents()
    {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        comfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth auth = new Auth();
                String password = newPassword.getText().toString();
                try {
                    password = auth.hashPassword(password);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                if(UserAccountHandler.resetPasswordByEmail(get_email,password))
                {
                    Intent intent = new Intent(ResetPasswordFrame.this, ForgetPasswordSuccessMessage.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void validatePassword() {
        String password = newPassword.getText().toString();
        String confirmPassword = comfirmPassword.getText().toString();
        int minLength = 8;

        if (password.isEmpty()) {
            textInputLayoutNewPassword.setError("Please enter a password");
            btn_submit.setEnabled(false);
        } else if (password.length() < minLength) {
            textInputLayoutNewPassword.setError("Password must be at least " + minLength + " characters long");
            btn_submit.setEnabled(false);
        } else {
            textInputLayoutNewPassword.setError(null);
            if (!password.equals(confirmPassword)) {
                textInputLayoutComfirmPassword.setError("Passwords do not match");
                btn_submit.setEnabled(false);
            } else {
                textInputLayoutComfirmPassword.setError(null);
                btn_submit.setEnabled(true);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_frame);
        get_email = getIntent().getStringExtra("email");
        addControls();
        addEvents();
    }
}