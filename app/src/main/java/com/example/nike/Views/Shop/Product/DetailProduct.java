package com.example.nike.Views.Shop.Product;

import static com.example.nike.Views.Util.formatCurrency;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Controller.FavoriteProductHandler;
import com.example.nike.Controller.ImageHandler;
import com.example.nike.Controller.ProductHandler;
import com.example.nike.Controller.ProductSizeHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.MainActivity;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductImage;
import com.example.nike.Model.ProductSize;
import com.example.nike.Model.ShopByIcons;
import com.example.nike.Model.UserAccount;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.IconsItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.PhotoProductAdapter;
import com.example.nike.Views.Shop.Adapter.PhotoRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.SizeItemAdapter;

import org.w3c.dom.Text;

import java.sql.SQLException;
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

    // Select Size
    ProductSize productSize;
    private FrameLayout container;
    private Button btnSpinnerSize;
    Dialog dialog;
    private ListView listViewSize;
    private TextView btnClose;

    private SizeItemAdapter sizeAdapter;

    private ArrayList<ProductSize> listSize;

    private Product CurrentProduct;
    private UserAccount user;
    private Button btnAddToWhistList;
    private SharedPreferences sharedPreferences;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "object_product";
    private static final String CurPro = "CurrentProduct";

    // TODO: Rename and change types of parameters
    private ArrayList<Product> mProduct;
    private Button btnAddToBag;
    private int objectID;
    private int categoryID;
    private int selectedItem = -1;
    public DetailProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list Parameter 1.
     * @return A new instance of fragment DetailProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailProduct newInstance(ArrayList<Product> list) {
        DetailProduct fragment = new DetailProduct();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, list);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list Parameter 1.
     * @param curPro Parameter 2.
     * @return A new instance of fragment DetailProduct.
     */
    public static DetailProduct newInstance(Product curPro,ArrayList<Product> list) {
        DetailProduct fragment = new DetailProduct();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, list);
        args.putSerializable(CurPro,curPro);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (ArrayList<Product>) getArguments().getSerializable(ARG_PARAM1);
            if(getArguments().getSerializable("CurrentProduct")!=null){
                CurrentProduct = (Product) getArguments().getSerializable("CurrentProduct");

            }
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
            if(CurrentProduct!=null){
                setDefaultVALUEOfCurrentProduct(CurrentProduct);
            }

            setDataRecycleViewPhotoList();
            dialog = new Dialog(getContext());

        }


        return view;
    }
    private void addControl(View view,Activity currentActivity) {
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

        btnAddToBag = view.findViewById(R.id.btnAddToBag);
        btnSpinnerSize = view.findViewById(R.id.btnSpinner);
        listSize = ProductSizeHandler.getDataByProductID(mProduct.get(0).getProductID());

        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        btnAddToWhistList = view.findViewById(R.id.btnFavorite);
        String email = sharedPreferences.getString("email",null).toString();
        int UserID = UserAccountHandler.getUserByEmail(email).getId();
        if(FavoriteProductHandler.CheckProductFavorite(UserID,mProduct.get(0).getProductID())){
            mProduct.get(0).setFavorite(true);
            btnAddToWhistList.setText("Favorited");
        }
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
    private void bindingDataOfProduct(Product product){
        setDataPhotoViewPager(product);
        tvNameProduct.setText(product.getName());
        tvPrice.setText("đ"+formatCurrency(product.getPrice()).replace(",", ".")+".000");
        tvDescription.setText(product.getDescription());
        tvObject.setText(product.getObjectName()+"'s "+product.getCategoryName());
        tvStyle.setText("Style: "+product.getStyleCode());
        tvShown.setText("Shown: "+product.getColorShown());
        listSize = ProductSizeHandler.getDataByProductID(product.getProductID());
        String email = sharedPreferences.getString("email",null).toString();
        int UserID = UserAccountHandler.getUserByEmail(email).getId();
        if(FavoriteProductHandler.CheckProductFavorite(UserID,product.getProductID())){
            product.setFavorite(true);
            btnAddToWhistList.setText("Favorited");
        }else {
            product.setFavorite(false);
            btnAddToWhistList.setText("Favorite");
        }

    }
    private void CustomToast(View currentView, String msg){


        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast,currentView.findViewById(R.id.customToast));
        TextView msgTv = view.findViewById(R.id.msgToast);



        Toast toast = new Toast(currentView.getContext());
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 120);
        msgTv.setText(msg);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);

       //ViewGroup.LayoutParams params = view.getLayoutParams();
        //if (params == null) {
          //  params = new ViewGroup.LayoutParams(
             //       ViewGroup.LayoutParams.MATCH_PARENT,
               //    ViewGroup.LayoutParams.WRAP_CONTENT
            //);
        //} else {
         //   params.width = ViewGroup.LayoutParams.MATCH_PARENT; // hoặc chiều rộng mong muốn khác
        //}
        //view.setLayoutParams(params);
        toast.show();


    }
    private void addEvent(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.fragment.app.FragmentManager fm = getActivity().getSupportFragmentManager();
//                fm.popBackStack("TabLayoutOfShop", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack();
                btnBack.setVisibility(View.GONE);
                tvNameFragment.setText("Shop");
            }
        });
        btnSpinnerSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup();

            }
        });
        btnAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
        btnAddToWhistList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sharedPreferences.getString("email",null).toString();
                int UserID = UserAccountHandler.getUserByEmail(email).getId();
                if(CurrentProduct != null && CurrentProduct.isFavorite() == false){
                    btnAddToWhistList.setText("Favorited");
                    CurrentProduct.setFavorite(true);
                    CustomToast(v,"Added to Favorites");
                    FavoriteProductHandler.insertFavoriteProduct(UserID,CurrentProduct.getProductID());
                } else if (CurrentProduct != null && CurrentProduct.isFavorite() == true){
                    btnAddToWhistList.setText("Favorite");
                    CurrentProduct.setFavorite(false);
                    CustomToast(v,"Removed from Favorites");
                    FavoriteProductHandler.removeFavoriteProduct(UserID,CurrentProduct.getProductID());
                }
                if (CurrentProduct == null && mProduct.get(0).isFavorite() == false){
                    btnAddToWhistList.setText("Favorited");
                    mProduct.get(0).setFavorite(true);
                    CustomToast(v,"Added to Favorites");
                    FavoriteProductHandler.insertFavoriteProduct(UserID,mProduct.get(0).getProductID());
                } else if(CurrentProduct == null && mProduct.get(0).isFavorite() == true){
                    btnAddToWhistList.setText("Favorite");
                    mProduct.get(0).setFavorite(false);
                    CustomToast(v,"Removed from Favorites");
                    FavoriteProductHandler.removeFavoriteProduct(UserID,mProduct.get(0).getProductID());
                }

            }
        });

    }
    protected void addControlOfPopupMenu(View view){
        //Layout
        container = getActivity().findViewById(R.id.frameLayout);
                //Component of PopupMenu
        listViewSize = view.findViewById(R.id.listViewSize);
        btnClose = view.findViewById(R.id.btnExit);
        //Set Data ListView of Size

        sizeAdapter = new SizeItemAdapter(getContext(),R.layout.row_item_size,listSize);
        listViewSize.setAdapter(sizeAdapter);
    }
    protected void addEventOfPopupMenu(View view){

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        listViewSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < listSize.size(); i++) {
                    ProductSize size = listSize.get(i);
                    if (size.isSelect()) {
                        // Nếu có, hủy chọn (xóa dấu check) cho size đó
                        size.setSelect(false);
                        // Cập nhật lại giao diện của ListView
                        View itemView = listViewSize.getChildAt(i - listViewSize.getFirstVisiblePosition());
                        if (itemView != null) {
                            ImageView checkedImageView = itemView.findViewById(R.id.itemCheck);
                            checkedImageView.setVisibility(View.GONE);
                        }
                        break;
                    }
                }
                productSize = listSize.get(position);
                productSize.setSelect(true);
                sizeAdapter.notifyDataSetChanged();
                btnSpinnerSize.setText("Size "+productSize.getSize().getName());
                dialog.dismiss();

            }
        });
    }
    protected void showPopup(){

        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.popup_menu_size,null);
        addControlOfPopupMenu(convertView);
        addEventOfPopupMenu(convertView);
        dialog.setContentView(convertView);
        //dialog.setCancelable(true); // Cho phép đóng Dialog khi chạm ra ngoài


        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(true); // Cho phép đóng Dialog khi chạm ra ngoài
        dialog.show();
    }
    private void bindingDataOfCurrentProduct(Product product){
        bindingDataOfProduct(product);
        CurrentProduct = product;
    }
    private void setDefaultVALUEOfCurrentProduct(Product cur){
        bindingDataOfCurrentProduct(cur);
    }
    @Override
    public void onPhotoClick(Product product) {

      bindingDataOfCurrentProduct(product);

    }
}