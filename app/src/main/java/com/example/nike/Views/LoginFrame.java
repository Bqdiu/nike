package com.example.nike.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Auth;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.MainActivity;
import com.example.nike.Model.UserAccount;
import com.example.nike.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.security.NoSuchAlgorithmException;

public class LoginFrame extends AppCompatActivity {
    EditText username, password;
    TextView register,forgot_password;
    MaterialButton signinbtn;

    UserAccountHandler userAccountHandler = new UserAccountHandler();

    ImageView btn_google;

    private static final int REQUEST_CODE_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AddControls();
        loadGoogleSignIn();
        try {
            loadNormalSignIn();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        addEvents();
    }

    private void AddControls(){
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signinbtn = (MaterialButton) findViewById(R.id.signinbtn);
        btn_google = findViewById(R.id.btn_SignInGoogle);
        register = findViewById(R.id.regiter);
        forgot_password = findViewById(R.id.forgot_password);
    }

    private void addEvents(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFrame.this, RegisterFrame.class);
                startActivity(intent);
            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = username.getText().toString();
                Auth auth = new Auth();
                String pw = password.getText().toString();
                try {
                    pw = auth.hashPassword(pw);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                if(!us.isEmpty() && !pw.isEmpty())
                {
                    UserAccount userAccount = UserAccountHandler.checkLogin(us,pw);
                    if(userAccount != null)
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", userAccount.getEmail());
                        editor.putString("first_name",userAccount.getFirst_name());
                        editor.putString("login_type","normal");
                        editor.putString("password",pw);
                        editor.apply();
                        Intent intent = new Intent(LoginFrame.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(LoginFrame.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(LoginFrame.this, "Hãy nhập đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
            }
        });
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFrame.this, ForgotPasswordFrame.class);
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
                    Intent intent = new Intent(LoginFrame.this, MainActivity.class);
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
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // save login
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String email = account.getEmail();
            String first_name = account.getGivenName();
            String url = "";
            if(account.getPhotoUrl() != null)
                url = account.getPhotoUrl().toString();
            System.out.println(email + " " + first_name);
            if(!userAccountHandler.checkEmailExist(email))
                userAccountHandler.addUserGoogle(email,first_name,url);
            editor.putString("email", email);
            editor.putString("first_name",first_name);
            editor.putString("user_img",url);
            editor.putString("login_type","google");
            editor.apply();
            updateUI(account);
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Intent intent = new Intent(LoginFrame.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}