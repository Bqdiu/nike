package com.example.nike.Views.Bag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagAdapter;
import com.example.nike.Views.Bag.Adapter.BagClass;

import java.util.ArrayList;

public class BagFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        ListView lvBag;
        ArrayList<BagClass> arrayListBag = new ArrayList<>();
        BagAdapter adapterBag;

        lvBag = view.findViewById(R.id.cartListView);

        arrayListBag.add(new BagClass("Nike", "abc", "White", 41, 1, 799, R.drawable.adidas_questar_shoes));
        arrayListBag.add(new BagClass("Nike 1", "abcd", "Black", 40, 2, 1598, R.drawable.adidas_questar_shoes1));
        arrayListBag.add(new BagClass("Nike 2", "abcdeefghi", "White", 41, 1, 899, R.drawable.adidas_questar_shoes2));
        arrayListBag.add(new BagClass("Nike 3", "abc", "White", 41, 1, 799, R.drawable.adidas_questar_shoes));
        arrayListBag.add(new BagClass("Nike 4", "abcd", "Black", 40, 2, 1598, R.drawable.adidas_questar_shoes1));
        arrayListBag.add(new BagClass("Nike 5", "abcdeefghi", "White", 41, 1, 899, R.drawable.adidas_questar_shoes2));

        adapterBag = new BagAdapter(requireContext(), R.layout.row_product_bag, arrayListBag);
        lvBag.setAdapter(adapterBag);

        return view;
    }
}
