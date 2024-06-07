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
import com.example.nike.R;
import com.example.nike.Model.DBConnection;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFrame extends AppCompatActivity {
    EditText usernameEditText, passwordEditText, emailEditText, first_name, last_name;
    TextView login;
    MaterialButton signUpButton;
    ImageView btn_google;
    private static final int REQUEST_CODE_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private SharedPreferences sharedPreferences;
    private DBConnection dbConnection = new DBConnection();
    private Connection conn = dbConnection.connectionClass();
    UserAccountHandler userAccountHandler = new UserAccountHandler();

    private void addControls()
    {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        emailEditText = findViewById(R.id.email);
        signUpButton = findViewById(R.id.signupbtn);
        btn_google = findViewById(R.id.btn_SignInGoogle);
        login = findViewById(R.id.login);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
    }

    private void addEvents()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterFrame.this, LoginFrame.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                Auth auth = new Auth();
                String password = passwordEditText.getText().toString();
                try {
                    password = auth.hashPassword(password);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                String fn = first_name.getText().toString();
                String ln = last_name.getText().toString();
                if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !fn.isEmpty() && !ln.isEmpty())
                {
                    if(UserAccountHandler.checkUserExist(username))
                        Toast.makeText(RegisterFrame.this, "Tên đăng nhập này đã tồn tại", Toast.LENGTH_SHORT).show();
                    else if (!checkEmailFormat(email)) {
                        Toast.makeText(RegisterFrame.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                    }
                    else if(UserAccountHandler.checkEmailExist(email))
                        Toast.makeText(RegisterFrame.this, "Email này đã được đăng kí", Toast.LENGTH_SHORT).show();
                    else
                    {
                        UserAccountHandler.addUser(username,password,email,fn,ln);
                        Toast.makeText(RegisterFrame.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        usernameEditText.setText("");
                        passwordEditText.setText("");
                        first_name.setText("");
                        last_name.setText("");
                        emailEditText.setText("");
                    }
                }
                else
                    Toast.makeText(RegisterFrame.this, "Vui lòng nhập đủ thông tin đăng kí", Toast.LENGTH_SHORT).show();
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
            String email = account.getEmail();
            String first_name = account.getGivenName();
            String url = account.getPhotoUrl().toString();
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
            // User logged
            Intent intent = new Intent(RegisterFrame.this, MainActivity.class);
            startActivity(intent);
        } else {
            // User not log in or is log out
        }
    }

    private boolean testUsername(String username) {
        // Kiểm tra xem tên người dùng có chứa dấu cách hoặc khoảng trắng không
        if (username.contains(" ") || username.contains("\t")) {
            return false;
        }
        return true;
    }


    private boolean checkEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean checkPassword(String password) {
        // Kiểm tra xem mật khẩu có ít nhất 6 ký tự không
        if (password.length() < 8) {
            return false;
        }

        // Kiểm tra xem mật khẩu có ít nhất một ký tự hoa không
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Kiểm tra xem mật khẩu có ít nhất một ký tự thường không
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Kiểm tra xem mật khẩu có ít nhất một số không
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        return true;
    }

    private boolean testFirstName(String firstName) {
        return !firstName.trim().isEmpty();
    }

    private boolean testLastName(String lastName) {
        return !lastName.trim().isEmpty();
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
