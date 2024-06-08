package com.example.nike.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Mailer.MailAPI;
import com.example.nike.Model.UserAccount;
import com.example.nike.OTPGenerator;
import com.example.nike.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.paypal.pyplcheckout.data.model.pojo.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordFrame extends AppCompatActivity {

    private ImageView btn_back;
    private Button btn_next;

    private TextInputLayout TextInputLayout;
    private TextInputEditText txt_forget_email;
    private String email;
    private String otp;
    private void addControls()
    {
        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        TextInputLayout = findViewById(R.id.TextInputLayout);
        txt_forget_email = findViewById(R.id.txt_forget_email);
    }

    private void addEvents()
    {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = String.valueOf(txt_forget_email.getText());
                UserAccount userAccount = UserAccountHandler.getUserByEmail(email);
                if (userAccount != null) {
                    String username = userAccount.getUsername();
                    if (UserAccountHandler.checkEmailExist(email) && UserAccountHandler.checkUserExist(username)) {
                        sendEmail(email);
                        Intent intent = new Intent(ForgotPasswordFrame.this, VerifyOTP.class);
                        intent.putExtra("email",email);
                        intent.putExtra("otp",otp);
                        startActivity(intent);
                    } else {
                        TextInputLayout.setError("This account was not found");
                    }
                } else {
                    TextInputLayout.setError("This account was not found");
                }

            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0) {
                    TextInputLayout.setError("Please enter your email");
                    btn_next.setEnabled(false);
                }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    TextInputLayout.setError("Invalid email format");
                    btn_next.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches())
                {
                    TextInputLayout.setError(null);
                    btn_next.setEnabled(true);
                }
            }
        };
        txt_forget_email.addTextChangedListener(textWatcher);
    }

    private void sendEmail(String email)
    {
        String mEmail = email;
        otp = OTPGenerator.generateOTP(6);
        String mSubject = "Reset your password";
        String mMessage = "Do not send OTP to anyone. Your OTP is " + otp;
        MailAPI mailAPI = new MailAPI(this,mEmail,mSubject,mMessage);
        mailAPI.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_frame);
        addControls();
        addEvents();
    }
}