package com.example.nike.Views.Shop.Product;

import static com.example.nike.Views.Util.formatCurrency;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nike.Controller.ImageHandler;
import com.example.nike.MainActivity;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductImage;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.PhotoProductAdapter;
import com.example.nike.Views.Shop.Adapter.PhotoRecycleViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailProduct extends Fragment implements PhotoRecycleViewAdapter.ItemClickListener {

    private ImageButton btnBack;
    private TextView tvNameFragment;
    private ArrayList<ProductImage> photoList = new ArrayList<>();
    private RecyclerView photoRecycleView;
    private PhotoRecycleViewAdapter photoReAdapter;
    private ArrayList<String> thumbnailString = new ArrayList<>();
    private ArrayList<Integer> idProductOfThumbnail = new ArrayList<>();
    private ViewPager imagePager;
    private CircleIndicator circleIndicator;
    private PhotoProductAdapter adapter;
    private TextView tvObject,tvNameProduct,tvPrice,tvDescription,tvStyle,tvShown;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "object_product";


    // TODO: Rename and change types of parameters
    private ArrayList<Product> mProduct;
    private int objectID;
    private int categoryID;

    public DetailProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list Parameter 1.
     * @param objectID Parameter 2.
     * @param categoryID Parameter 3.
     * @return A new instance of fragment DetailProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailProduct newInstance(int categoryID,int objectID,ArrayList<Product> list) {
        DetailProduct fragment = new DetailProduct();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, list);
        args.putSerializable("ObjectID",objectID);
        args.putSerializable("CategoryID",categoryID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (ArrayList<Product>) getArguments().getSerializable(ARG_PARAM1);
            objectID = getArguments().getInt("ObjectID");
            categoryID = getArguments().getInt("CategoryID");
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
            bindingDataOfProduct(mProduct.get(0));
            setDataRecycleViewPhotoList();
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
        tvNameFragment = currentActivity.findViewById(R.id.tvNameOfFragment);
        tvNameFragment.setText(mProduct.get(0).getName());
        //Image Product Slider
        imagePager = view.findViewById(R.id.viewPagerProduct_photo);
        circleIndicator = view.findViewById(R.id.circle_Indicator);
        //thumbnailRecycle View
        photoRecycleView = view.findViewById(R.id.recycleViewPhotoList);

        tvObject = view.findViewById(R.id.tvObject);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvNameProduct = view.findViewById(R.id.tvNameProduct);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvShown = view.findViewById(R.id.tvShown);
        tvStyle = view.findViewById(R.id.tvStyle);

    }
    private void setDataRecycleViewPhotoList(){
       
        photoReAdapter = new PhotoRecycleViewAdapter(mProduct,getContext(),this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        photoRecycleView.setLayoutManager(layoutManager);
        photoRecycleView.setAdapter(photoReAdapter);
    }
    private void setDataPhotoViewPager(Product product){
       photoList = ImageHandler.getPhotoByProductID(product.getProductID());
        adapter = new PhotoProductAdapter(getContext(),photoList);
        imagePager.setAdapter(adapter);

        circleIndicator.setViewPager(imagePager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }
    private String getFullObjectName(int categoryID,int objectID){
        String categoryName = "";
        if(categoryID == 1){
            categoryName = "Clothing";
        }
        else {
            categoryName = "Shoes";
        }
        String objectName = "";
        if(objectID == 1){
            objectName = "Men";
        }
        else if(objectID == 2){
            objectName = "Women";
        }
        else {
            objectName = "Kids";
        }
        return objectName +"'s "+categoryName;
    }
    private void bindingDataOfProduct(Product product){
        setDataPhotoViewPager(product);
        tvNameProduct.setText(product.getName());
        tvPrice.setText("đ"+formatCurrency(product.getPrice()).replace(",", ".")+".000");
        tvDescription.setText(product.getDescription());
        tvObject.setText(getFullObjectName(categoryID,objectID));
        tvStyle.setText("Style: "+product.getStyleCode());
        tvShown.setText("Shown: "+product.getColorShown());
    }
    private void addEvent(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.fragment.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("TabLayoutOfShop", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                btnBack.setVisibility(View.GONE);
                tvNameFragment.setText("Shop");
            }
        });

    }

    @Override
    public void onPhotoClick(Product product) {

        bindingDataOfProduct(product);
    }
}