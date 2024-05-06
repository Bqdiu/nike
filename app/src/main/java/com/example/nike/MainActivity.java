package com.example.nike;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Views.Bag.BagFragment;
import com.example.nike.Views.Home.HomeFragment;
import com.example.nike.Views.Profile.InboxFragment.InboxFragment;
import com.example.nike.Views.Profile.ProfileFragment;
import com.example.nike.Views.Profile.ProfileFragmentInboxListener;
import com.example.nike.Views.Shop.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ProfileFragmentInboxListener {
    ImageButton btnBack;
    TextView tvNameOfFragment;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    RelativeLayout actionBar;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        loadDataUser();
        addEvent();
    }
    protected void addControl(){
        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        actionBar = findViewById(R.id.actionBar);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        tvNameOfFragment = findViewById(R.id.tvNameOfFragment);
        btnBack = findViewById(R.id.btnBack);

    }
    protected void addEvent(){
        LoadFragment(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemHome:
                        actionBar.setVisibility(View.VISIBLE);
                        LoadFragment(new HomeFragment());
                        tvNameOfFragment.setVisibility(GONE);
                        btnBack.setVisibility(GONE);
                        return true;
                    case R.id.itemShop:
                        actionBar.setVisibility(View.VISIBLE);
                        LoadFragment(new ShopFragment());
                        tvNameOfFragment.setText("Shop");
                        tvNameOfFragment.setVisibility(View.VISIBLE);
                        btnBack.setVisibility(GONE);
                        return true;
                    case R.id.itemFavorites:
                        actionBar.setVisibility(GONE);
                        tvNameOfFragment.setVisibility(GONE);
                        btnBack.setVisibility(GONE);
                        return true;
                    case R.id.itemBag:
                        actionBar.setVisibility(GONE);
                        tvNameOfFragment.setVisibility(GONE);
                        LoadFragment(new BagFragment());
                        return true;
                    case R.id.itemProfile:
                        actionBar.setVisibility(GONE);
                        LoadFragment(new ProfileFragment());
                        tvNameOfFragment.setVisibility(GONE);
                        btnBack.setVisibility(GONE);
                        return true;
                }
                return false;
            }
        });
    }
    private void LoadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.commit();
    }
    private void loadDataUser()
    {
        String us = sharedPreferences.getString("user_name",null);
        Toast.makeText(this, "Welcome " + us, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadInboxFragment()
    {
        LoadFragment(new InboxFragment());
    }

}