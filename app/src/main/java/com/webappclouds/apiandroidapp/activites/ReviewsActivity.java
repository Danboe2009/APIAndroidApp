package com.webappclouds.apiandroidapp.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.webappclouds.apiandroidapp.R;
import com.webappclouds.apiandroidapp.model.FoodTruck;

public class ReviewsActivity extends AppCompatActivity {

    private FoodTruck foodTruck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        foodTruck = getIntent().getParcelableExtra(FoodTrucksListActivity.EXTRA_ITEM_TRUCK);
        System.out.println(foodTruck.getName());
    }
}
