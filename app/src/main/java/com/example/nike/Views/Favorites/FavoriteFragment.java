package com.example.nike.Views.Favorites;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView recycleFav;
    private ArrayList<UserFavoriteProducts> list;
    private SharedPreferences sharedPreferences;

    private FavAdapter adapter;

    private void addControls(View view)
    {

        recycleFav = view.findViewById(R.id.recycleFav);
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        addControls(view);
        String email = sharedPreferences.getString("email",null).toString();
        int UserID = UserAccountHandler.getUserByEmail(email).getId();
        list = FavoriteProductHandler.getData(UserID);

        adapter = new FavAdapter(list);
        recycleFav.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recycleFav.setLayoutManager(gridLayoutManager);

        return view;
    }


}