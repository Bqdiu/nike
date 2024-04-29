package com.example.nike;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.nike.Views.Home.HomeFragment;
import com.example.nike.Views.Profile.InboxFragment.InboxFragment;
import com.example.nike.Views.Profile.ProfileFragment;
import com.example.nike.Views.Profile.ProfileFragmentInboxListener;
import com.example.nike.Views.Shop.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ProfileFragmentInboxListener {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    RelativeLayout actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
    }
    protected void addControl(){
        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        actionBar = findViewById(R.id.actionBar);

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
                        return true;
                    case R.id.itemShop:
                        actionBar.setVisibility(View.VISIBLE);
                        LoadFragment(new ShopFragment());
                        return true;
                    case R.id.itemFavorites:
                        actionBar.setVisibility(GONE);
                        return true;
                    case R.id.itemBag:
                        actionBar.setVisibility(GONE);
                        return true;
                    case R.id.itemProfile:
                        actionBar.setVisibility(GONE);
                        LoadFragment(new ProfileFragment());
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


    @Override
    public void loadInboxFragment()
    {
        LoadFragment(new InboxFragment());
    }

}