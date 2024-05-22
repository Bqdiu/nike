package com.example.nike.Views.Favorites;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Model.ProductParent;
import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagClass;
import com.example.nike.Views.Favorites.Adapter.FavAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView recycleFav;
    private ArrayList<ProductParent> list;


    private void addControls(View view)
    {
        recycleFav = view.findViewById(R.id.recycleFav);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        addControls(view);
        list = ProductParentHandler.getData();
        FavAdapter favAdapter = new FavAdapter(list);
        recycleFav.setAdapter(favAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recycleFav.setLayoutManager(gridLayoutManager);

        return view;
    }


}