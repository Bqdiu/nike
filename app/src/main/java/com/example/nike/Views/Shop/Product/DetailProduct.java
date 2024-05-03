package com.example.nike.Views.Shop.Product;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.nike.MainActivity;
import com.example.nike.Model.Product;
import com.example.nike.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailProduct extends Fragment {
    ImageButton btnBack;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "object_product";


    // TODO: Rename and change types of parameters
    private Product mParam1;


    public DetailProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param product1 Parameter 1.
     * @return A new instance of fragment DetailProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailProduct newInstance(Product product1) {
        DetailProduct fragment = new DetailProduct();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, product1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Product) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        Activity currentActivity = getActivity();
        if (currentActivity instanceof MainActivity) {
            addControl(view,currentActivity);
            addEvent();
        }


        return view;
    }
    private void addControl(View view,Activity currentActivity){
        // Nếu Activity là loại Activity bạn mong muốn, bạn có thể tìm kiếm ImageButton từ đó
        btnBack = currentActivity.findViewById(R.id.btnBack);
        // Kiểm tra xem ImageButton có null hay không trước khi thực hiện thay đổi
        if (btnBack != null) {
            btnBack.setVisibility(View.VISIBLE);
        }


    }
    private void addEvent(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.fragment.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("TabLayoutOfShop", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                btnBack.setVisibility(View.GONE);
            }
        });
    }
}