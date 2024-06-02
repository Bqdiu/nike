package com.example.nike.Views.Favorites;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.nike.Controller.FavoriteProductHandler;
import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Model.ProductParent;
import com.example.nike.Model.UserAccount;
import com.example.nike.Model.UserFavoriteProducts;
import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagClass;
import com.example.nike.Views.Favorites.Adapter.FavAdapter;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;
import com.example.nike.Views.Shop.ShopFragment;
import com.example.nike.Views.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView recycleFav;
    private ArrayList<UserFavoriteProducts> list;
    private SharedPreferences sharedPreferences;

    private FavAdapter adapter;
    private ToggleButton toggleBtnFavorite;
    private Button btnShopNow;
    private RelativeLayout relativeNonData;

    private void addControls(View view)
    {

        recycleFav = view.findViewById(R.id.recycleFav);
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        toggleBtnFavorite = view.findViewById(R.id.toggleBtnFavorite);
        btnShopNow = view.findViewById(R.id.btnShopingNow);
        relativeNonData = view.findViewById(R.id.relativeNonData);
    }
    private void addEvent(){
        toggleBtnFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateFavoriteIconsVisibility(isChecked);
                if(isChecked == false){
                    Toast.makeText(getContext(),"Update Successful",Toast.LENGTH_LONG).show();
                    LoadRecycleView();
                }

            }
        });
        btnShopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadFragment(new ShopFragment());
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
                bottomNavigationView.setSelectedItemId(R.id.itemShop);
            }
        });
    }
    private void updateFavoriteIconsVisibility(boolean showFavoritesOnly) {
        for (int i = 0; i < recycleFav.getChildCount(); i++) {
            RecyclerView.ViewHolder holder = recycleFav.getChildViewHolder(recycleFav.getChildAt(i));
            if (holder instanceof FavAdapter.FavViewHolder) {
                FavAdapter.FavViewHolder favViewHolder = (FavAdapter.FavViewHolder) holder;
                favViewHolder.btnFavorite.setVisibility(showFavoritesOnly ? View.VISIBLE : View.GONE);
            }
        }

    }
    private void LoadFragment(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    private void LoadRecycleView(){
        list = new ArrayList<>();
        list = FavoriteProductHandler.getData(Util.getUserID(getContext()));
        if(list.size()>0){
          //  relativeNonData.setVisibility(View.GONE);
          //  btnShopNow.setVisibility(View.GONE);
            adapter = new FavAdapter(list);
            recycleFav.setAdapter(adapter);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            recycleFav.setLayoutManager(gridLayoutManager);
        }
        else{
           relativeNonData.setVisibility(View.VISIBLE);
           btnShopNow.setVisibility(View.VISIBLE);
            adapter = new FavAdapter(list);
            recycleFav.setAdapter(adapter);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            recycleFav.setLayoutManager(gridLayoutManager);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        addControls(view);

        LoadRecycleView();
        addEvent();
        return view;
    }


}