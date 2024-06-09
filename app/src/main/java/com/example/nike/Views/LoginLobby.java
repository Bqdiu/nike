package com.example.nike.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.MainActivity;
import com.example.nike.Model.UserAccount;
import com.example.nike.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;

import java.security.NoSuchAlgorithmException;

public class LoginLobby extends AppCompatActivity {
    private ImageView imgView;
    private MaterialButton btnSignUp;
    private MaterialButton btnSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_lobby);
        addControl();
        addEvent();
        addAnimation();
        loadGoogleSignIn();
        try {
            loadNormalSignIn();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private void addControl(){
        imgView = findViewById(R.id.imgBg);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
    }
    private void addEvent(){
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginLobby.this,LoginFrame.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginLobby.this,RegisterFrame.class);
                startActivity(intent);
            }
        });
    }
    private void loadNormalSignIn() throws NoSuchAlgorithmException {
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email",null);
        UserAccount userAccount = UserAccountHandler.getUserByEmail(email);
        String login_type = sharedPreferences.getString("login_type",null);
        if(login_type != null && login_type.equals("normal"))
        {
            if(userAccount != null)
            {
                String storePassword = sharedPreferences.getString("password",null);
                String dbPassword = userAccount.getPassword();
                if(storePassword.equals(dbPassword))
                {
                    Intent intent = new Intent(LoginLobby.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
    private void loadGoogleSignIn()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // check login
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }
    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Intent intent = new Intent(LoginLobby.this, MainActivity.class);
            startActivity(intent);
        }
    }
    private void addAnimation(){
        AnimationDrawable animationDrawable = (AnimationDrawable) imgView.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(700);
        animationDrawable.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}