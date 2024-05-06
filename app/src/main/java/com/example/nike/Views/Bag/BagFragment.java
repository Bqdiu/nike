package com.example.nike.Views.Bag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Model.Product;
import com.example.nike.Views.Bag.Adapter.BagAdapter;
import com.example.nike.R;

import java.util.ArrayList;
import java.util.List;

public class BagFragment extends Fragment {

    private RecyclerView recyclerView;
    private BagAdapter bagAdapter;
    private List<Product> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        recyclerView = view.findViewById(R.id.cartRecyclerView);
        productList = new ArrayList<>();

        // Khởi tạo lớp ProductAdapter với danh sách sản phẩm trống ban đầu
        bagAdapter = new BagAdapter(productList, getContext());
        recyclerView.setAdapter(bagAdapter);
        recyclerView.setAdapter(bagAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load danh sách sản phẩm từ cơ sở dữ liệu và cập nhật RecyclerView
        loadProductsFromDatabase();

        return view;
    }

    private void loadProductsFromDatabase() {
        Product product = new Product();
        //product.getAllProducts();

        // Cập nhật Adapter với danh sách sản phẩm mới
        //productAdapter.updateData(productList);
    }
}
