package com.example.nike.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nike.Model.UserAccount;
import com.example.nike.R;
import com.google.android.material.button.MaterialButton;

public class Login extends AppCompatActivity {
    EditText password, email;
    MaterialButton signinbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AddControls();
        addEvents();
    }

    private void AddControls(){
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signinbtn = (MaterialButton) findViewById(R.id.signinbtn);
    }

    private void addEvents(){
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = email.getText().toString();
                String inputPassword = password.getText().toString();

                UserAccount.checkLogin(inputEmail, inputPassword, new UserAccount.LoginCallback() {
                    @Override
                    public void onLoginResult(boolean isSuccess) {
                        if (isSuccess) {
                            Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Email hoặc mật khẩu không chính xác. Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}