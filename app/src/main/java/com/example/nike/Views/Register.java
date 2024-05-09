package com.example.nike.Views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nike.MainActivity;
import com.example.nike.R;
import com.example.nike.Model.DBConnection;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.sql.Connection;

public class Register extends AppCompatActivity {
    EditText usernameEditText, passwordEditText, emailEditText, numberPhoneEditText;
    MaterialButton signUpButton;
    ImageView btn_google;
    private static final int REQUEST_CODE_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private SharedPreferences sharedPreferences;
    private DBConnection dbConnection = new DBConnection();
    private Connection conn = dbConnection.connectionClass();
    private void addControls()
    {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        emailEditText = findViewById(R.id.email);
        numberPhoneEditText = findViewById(R.id.number_phone);
        signUpButton = findViewById(R.id.signupbtn);
        btn_google = findViewById(R.id.btn_SignInGoogle);
    }

    private void addEvents()
    {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String numberPhone = numberPhoneEditText.getText().toString();

                if (isValidInformation(username, email, password, numberPhone)) {

                    if (conn != null) {
                        boolean isSuccess = dbConnection.addUser(username, password, "", email, numberPhone, "", "", "", 0, 0);

                        if (isSuccess) {
                            Toast.makeText(Register.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Database connection failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Invalid information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        loadGoogleSignIn();
        addEvents();
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
        // Kết quả trả về từ launch của Intent từ GoogleSignInClient.getSignInIntent(...);
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
            editor.putString("email", account.getEmail());
            editor.putString("user_name",account.getGivenName());
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
            // User logged
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        } else {
            // User not log in or is log out
        }
    }

    private boolean isValidInformation(String username, String email, String password, String numberPhone) {
        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !numberPhone.isEmpty();
    }
}
