package com.missingcontroller.apiandroidapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.missingcontroller.apiandroidapp.R;
import com.missingcontroller.apiandroidapp.adapter.FoodTruckAdapter;
import com.missingcontroller.apiandroidapp.data.DataService;
import com.missingcontroller.apiandroidapp.model.FoodTruck;
import com.missingcontroller.apiandroidapp.view.ItemDecorator;

import java.util.ArrayList;

public class FoodTrucksListActivity extends AppCompatActivity {

    private FoodTruckAdapter adapter;
    public static final String EXTRA_ITEM_TRUCK = "TRUCK";
    private static FoodTrucksListActivity foodTrucksListActivity;
    private ArrayList<FoodTruck> trucks = new ArrayList<>();

    public static FoodTrucksListActivity getFoodTrucksListActivity() {
        return foodTrucksListActivity;
    }

    public static void setFoodTrucksListActivity(FoodTrucksListActivity foodTrucksListActivity) {
        FoodTrucksListActivity.foodTrucksListActivity = foodTrucksListActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_trucks_list);

        setFoodTrucksListActivity(this);

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

    public void loadFoodTruckDetailActivity(FoodTruck truck) {
        Intent intent = new Intent(FoodTrucksListActivity.this, FoodTruckDetailActivity.class);
        intent.putExtra(FoodTrucksListActivity.EXTRA_ITEM_TRUCK, truck);
        startActivity(intent);
    }
}
