package com.example.nike.Views.Profile.InboxFragment;

import android.animation.Animator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nike.Model.ProductEvent;
import com.example.nike.R;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment {

    private ListView listView;
    private List<ProductEvent> productEvents;
    private ArrayAdapter<ProductEvent> adapter;

    public InboxFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        Animation sildeInright = AnimationUtils.loadAnimation(view.getContext(),R.anim.slide_in_right);
        view.startAnimation(sildeInright);
        listView = view.findViewById(R.id.inbox_listview);

        productEvents = new ArrayList<>();
        adapter = new ArrayAdapter<ProductEvent>(getContext(), R.layout.row_inbox, productEvents) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View rowView = convertView;

                if (rowView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    rowView = inflater.inflate(R.layout.row_inbox, parent, false);
                }

                ProductEvent productEvent = productEvents.get(position);

                TextView textViewTitle = rowView.findViewById(R.id.inbox_Name);
                TextView textViewDescription = rowView.findViewById(R.id.inbox_Des);
                TextView textViewTime = rowView.findViewById(R.id.inbox_Time);

                textViewTitle.setText(productEvent.getDiscountEventName());
                textViewDescription.setText(productEvent.getDiscountEventDescription());
                textViewTime.setText(productEvent.getDiscountEventDateCreated().toString());

                return rowView;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click
            }
        });

        // Execute AsyncTask to fetch data from database
        new FetchProductEventsTask().execute();

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
    // AsyncTask to fetch data from database in background thread
    private class FetchProductEventsTask extends AsyncTask<Void, Void, List<ProductEvent>> {

        @Override
        protected List<ProductEvent> doInBackground(Void... voids) {
            // Perform database operations here
            return ProductEvent.getAllProductEventsWithMonthsSinceCreation();
        }

        @Override
        protected void onPostExecute(List<ProductEvent> result) {
            super.onPostExecute(result);
            if (result != null) {
                productEvents.clear();
                productEvents.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
