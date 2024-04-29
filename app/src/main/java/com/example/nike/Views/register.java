package com.example.nike.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nike.R;
import com.example.nike.Model.DBConnection;
import com.google.android.material.button.MaterialButton;

import java.sql.Connection;

public class register extends AppCompatActivity {
    EditText usernameEditText, passwordEditText, emailEditText, numberPhoneEditText;
    MaterialButton signUpButton;

    private void addControls()
    {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        emailEditText = findViewById(R.id.email);
        numberPhoneEditText = findViewById(R.id.number_phone);
        signUpButton = findViewById(R.id.signupbtn);
    }

    private void addEvents()
    {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các trường EditText
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String numberPhone = numberPhoneEditText.getText().toString();

                // Kiểm tra thông tin đăng ký hợp lệ
                if (isValidInformation(username, email, password, numberPhone)) {
                    // Thực hiện kết nối và đăng ký người dùng
                    DBConnection dbConnection = new DBConnection();
                    Connection conn = dbConnection.connectionClass();

                    if (conn != null) {
                        boolean isSuccess = dbConnection.addUser(username, password, "", email, numberPhone, "", "", "", 0, 0);

                        if (isSuccess) {
                            Toast.makeText(register.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(register.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(register.this, "Database connection failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(register.this, "Invalid information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addEvents();
    }

    private boolean isValidInformation(String username, String email, String password, String numberPhone) {
        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !numberPhone.isEmpty();
    }
}
