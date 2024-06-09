package com.example.nike;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nike.Views.Bag.BagFragment;
import com.example.nike.Views.Favorites.FavoriteFragment;
import com.example.nike.Views.Home.HomeFragment;
import com.example.nike.Views.Profile.ProfileFragment;
import com.example.nike.Views.Shop.Product.DetailProduct;
import com.example.nike.Views.Shop.Product.SearchProductFragment;
import com.example.nike.Views.Shop.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    ImageButton btnBack;
    TextView tvNameOfFragment;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    RelativeLayout actionBar;
    ImageButton btn_search;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding();
        addControl();
        loadDataUser();
        addEvent();
    }
    protected void binding(){
        Intent intent = getIntent();
        bottomNavigationView = findViewById(R.id.bottom_nav);
        if (intent != null && "ShopFragment".equals(intent.getStringExtra("navigateTo"))) {

            LoadFragment(new ShopFragment());

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
            bottomNavigationView.setSelectedItemId(R.id.itemShop);
        }else if(intent != null && "BagFragment".equals(intent.getStringExtra("navigateTo"))){
            LoadFragment(new BagFragment());

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
            bottomNavigationView.setSelectedItemId(R.id.itemBag);
        }else{
            LoadFragment(new HomeFragment());
        }
    }
    protected void addControl(){
        frameLayout = findViewById(R.id.frameLayout);

        actionBar = findViewById(R.id.actionBar);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        tvNameOfFragment = findViewById(R.id.tvNameOfFragment);
        btnBack = findViewById(R.id.btnBack);
        btn_search = findViewById(R.id.btn_search);

    }
    protected void addEvent(){

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_search.setVisibility(GONE);
                SearchProductFragment searchFragment = new SearchProductFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentUtils.addFragment(fm,searchFragment,R.id.frameLayout);
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                if(! (currentFragment instanceof HomeFragment ) && ! (currentFragment instanceof ShopFragment) && ! (currentFragment instanceof DetailProduct))
                {
                    btn_search.setVisibility(GONE);
                    actionBar.setVisibility(GONE);
                }
                else
                {
                    btn_search.setVisibility(View.VISIBLE);
                    actionBar.setVisibility(View.VISIBLE);
                }
            }
        });

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
                        LoadFragment(new FavoriteFragment());
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
        ft.addToBackStack(null);
        ft.commit();
    }
    private void loadDataUser()
    {
        String us = sharedPreferences.getString("first_name",null);
//        Toast.makeText(this, "Welcome " + us, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

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