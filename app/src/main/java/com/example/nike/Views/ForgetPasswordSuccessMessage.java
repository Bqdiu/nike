package com.example.nike.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nike.R;

public class ForgetPasswordSuccessMessage extends AppCompatActivity {

    private Button btn_login;
    private void addControls()
    {
        btn_login = findViewById(R.id.btn_login);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_success_message);
        addControls();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordSuccessMessage.this,LoginFrame.class);
                startActivity(intent);
            }
        });

    }
}