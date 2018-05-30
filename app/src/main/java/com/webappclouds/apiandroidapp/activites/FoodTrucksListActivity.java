package com.webappclouds.apiandroidapp.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.webappclouds.apiandroidapp.R;
import com.webappclouds.apiandroidapp.adapter.FoodTruckAdapter;
import com.webappclouds.apiandroidapp.data.DataService;
import com.webappclouds.apiandroidapp.model.FoodTruck;
import com.webappclouds.apiandroidapp.view.ItemDecorator;

import java.util.ArrayList;

public class FoodTrucksListActivity extends AppCompatActivity {

    private FoodTruckAdapter adapter;
    private ArrayList<FoodTruck> trucks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_trucks_list);

        TrucksDownloaded listener = new TrucksDownloaded() {
            @Override
            public void success(Boolean success) {
                if (success) {
                    setUpRecycler();
                }
            }
        };

        setUpRecycler();
        trucks = DataService.getInstance().downloadAllFoodTrucks(this, listener);
    }

    private void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_foodtruck);
        recyclerView.setHasFixedSize(true);
        adapter = new FoodTruckAdapter(trucks);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ItemDecorator(0, 0, 0, 10));
    }

    public interface TrucksDownloaded {
        void success(Boolean success);
    }
}
