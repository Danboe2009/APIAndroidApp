package com.missingcontroller.apiandroidapp.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.missingcontroller.apiandroidapp.R;
import com.missingcontroller.apiandroidapp.adapter.FoodTruckAdapter;
import com.missingcontroller.apiandroidapp.constants.Constants;
import com.missingcontroller.apiandroidapp.data.DataService;
import com.missingcontroller.apiandroidapp.model.FoodTruck;
import com.missingcontroller.apiandroidapp.view.ItemDecorator;

import java.util.ArrayList;

public class FoodTrucksListActivity extends AppCompatActivity {

    private FoodTruckAdapter adapter;
    public static final String EXTRA_ITEM_TRUCK = "TRUCK";
    private static FoodTrucksListActivity foodTrucksListActivity;
    SharedPreferences prefs;
    private ArrayList<FoodTruck> trucks = new ArrayList<>();
    private FloatingActionButton addTruckBtn;

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
        addTruckBtn = findViewById(R.id.add_truck_btn);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        addTruckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAddTruck();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        TrucksDownloaded listener = new TrucksDownloaded() {
            @Override
            public void success(Boolean success) {
                if (success) {
                    setUpRecycler();
                }
            }
        };

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

    public void loadAddTruck() {
        if (prefs.getBoolean(Constants.IS_LOGGED_IN, false)) {
            Intent intent = new Intent(FoodTrucksListActivity.this, AddTruckActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(FoodTrucksListActivity.this, LoginActivity.class);
            Toast.makeText(getBaseContext(), "Please login to add food truck.", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
}
