package com.example.nike.Views.Shop.Product;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nike.Controller.HistorySearchHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.FragmentUtils;
import com.example.nike.Model.HistorySearch;
import com.example.nike.Model.UserAccount;
import com.example.nike.R;

import java.util.ArrayList;

public class SearchProductFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private SearchView search_view;
    private ArrayAdapter<String> adapter;
    private ListView lv_history_search;
    private TextView clear_all;

    private ArrayList<String> list= new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private UserAccount userAccount;
    public SearchProductFragment() {
        // Required empty public constructor
    }

    public static SearchProductFragment newInstance(String param1, String param2) {
        SearchProductFragment fragment = new SearchProductFragment();
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

    private void addControls(View view)
    {
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        search_view = view.findViewById(R.id.search_view);
        lv_history_search = view.findViewById(R.id.lv_history_search);
        clear_all = view.findViewById(R.id.clear_all);

    }

    private void addEvents()
    {
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                HistorySearchHandler.addHistorySearch(userAccount.getId(),query);
                SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(query);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,searchResultFragment,R.id.frameLayout);
//                loadHistorySearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        search_view.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getParentFragmentManager().popBackStack();
                return false;
            }
        });

        clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistorySearchHandler.clearHistorySearch(userAccount.getId());
                loadHistorySearch();
            }
        });
        lv_history_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = (String) parent.getItemAtPosition(position);
                SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(query);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,searchResultFragment,R.id.frameLayout);
            }
        });
    }

    private void loadHistorySearch()
    {
        list.clear();
        ArrayList<HistorySearch> listHS = HistorySearchHandler.getHistorySearch(userAccount.getId());
        for (HistorySearch hs : listHS)
            list.add(hs.getText_search());
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,list);
        lv_history_search.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_product, container, false);
        Animation sildeInBottom = AnimationUtils.loadAnimation(view.getContext(),R.anim.slide_in_top);
        view.startAnimation(sildeInBottom);
        addControls(view);
        String email = sharedPreferences.getString("email",null);
        userAccount = UserAccountHandler.getUserByEmail(email);
        loadHistorySearch();
        addEvents();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}