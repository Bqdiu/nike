package com.example.nike.Views.Shop.FragmentOfTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nike.Controller.ProductHandler;
import com.example.nike.Model.Product;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Product.DetailProduct;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenVsWomen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenVsWomen extends Fragment implements ItemRecycleViewAdapter.ItemClickListener {


    RecyclerView recyclerViewNewRelease;
    ItemRecycleViewAdapter adapter;
    ArrayList<Product> productArrayList = new ArrayList<>();
    TextView tvNewRelease;
    int genderID;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenVsWomen(int GenderID) {
        // Required empty public constructor
        this.genderID = GenderID;
    }
    public MenVsWomen(){

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenVsWomen.
     */
    // TODO: Rename and change types and number of parameters
    public static MenVsWomen newInstance(String param1, String param2) {
        MenVsWomen fragment = new MenVsWomen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_men_vs_women, container, false);
        addControl(view);
        data();
        return view;
    }
    private void addControl(View view){
        recyclerViewNewRelease = view.findViewById(R.id.newReleaseRecycleView);
        tvNewRelease = view.findViewById(R.id.tvNewRelease);

    }
    private void data(){
        productArrayList = ProductHandler.getDataNewReleaseByGender(genderID);
        if(productArrayList.isEmpty()){
            tvNewRelease.setVisibility(View.GONE);
        }
        else {
            tvNewRelease.setVisibility(View.VISIBLE);
            adapter = new ItemRecycleViewAdapter(getContext(),productArrayList,this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
            recyclerViewNewRelease.setLayoutManager(layoutManager);
            recyclerViewNewRelease.setAdapter(adapter);
        }

    }


    @Override
    public void onItemClick(Product product) {
        Fragment fragment = DetailProduct.newInstance(product);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.addToBackStack("TabLayoutOfShop");
        ft.commit();
    }
}