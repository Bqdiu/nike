package com.example.nike.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Mailer.MailAPI;
import com.example.nike.R;

public class VerifyOTP extends AppCompatActivity {

    private PinView otp_code;
    private Button btn_verify_code;
    private String get_email = "";
    private String get_otp = "";

    private void addControls()
    {
        otp_code = findViewById(R.id.otp_code);
        btn_verify_code = findViewById(R.id.btn_verify_code);
    }

    private void addEvents()
    {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    btn_verify_code.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    btn_verify_code.setEnabled(true);
                }
            }
        };
        otp_code.addTextChangedListener(textWatcher);
        btn_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otp_code.getText().toString();
                if(otp.equals(get_otp))
                {
                    Intent intent = new Intent(VerifyOTP.this, ResetPasswordFrame.class);
                    intent.putExtra("email",get_email);
                    startActivity(intent);
                }
                else
                    Toast.makeText(VerifyOTP.this, "OTP is incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        get_email = getIntent().getStringExtra("email");
        get_otp = getIntent().getStringExtra("otp");
        System.out.println("email: " + get_email);
        System.out.println("otp: " + get_otp);
        addControls();
        addEvents();
    }
}