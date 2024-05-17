package com.example.nike.Views.Favorites;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagClass;
import com.example.nike.Views.Favorites.Adapter.FavAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView rcFav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rcFav = view.findViewById(R.id.recycleFav);  // Gán rcFav trước khi sử dụng

        FavAdapter favAdapter = new FavAdapter(getListFav());
        rcFav.setAdapter(favAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);  // Sử dụng getContext() thay vì this
        rcFav.setLayoutManager(gridLayoutManager);

        return view;
    }

    private List<BagClass> getListFav() {
        List<BagClass> listFavBag = new ArrayList<>();

        listFavBag.add(new BagClass(R.drawable.adidas_questar_shoes, "Nike 1", 888));
        listFavBag.add(new BagClass(R.drawable.adidas_questar_shoes1, "Nike 2", 999));
        listFavBag.add(new BagClass(R.drawable.adidas_questar_shoes2, "Nike 3", 699));

        return listFavBag;
    }
}