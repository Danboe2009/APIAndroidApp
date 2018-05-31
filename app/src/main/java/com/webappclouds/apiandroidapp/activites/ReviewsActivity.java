package com.webappclouds.apiandroidapp.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.webappclouds.apiandroidapp.R;
import com.webappclouds.apiandroidapp.data.DataService;
import com.webappclouds.apiandroidapp.model.FoodTruck;
import com.webappclouds.apiandroidapp.model.FoodTruckReview;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    private FoodTruck foodTruck;
    private ArrayList<FoodTruckReview> reviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        foodTruck = getIntent().getParcelableExtra(FoodTrucksListActivity.EXTRA_ITEM_TRUCK);
        System.out.println(foodTruck.getName());

        ReviewInterface listener = new ReviewInterface() {
            @Override
            public void success(Boolean success) {
                if (success) {
                    System.out.println(reviews);
                }
            }
        };

        reviews = DataService.getInstance().downloadReviews(this, foodTruck, listener);
    }

    public interface ReviewInterface {
        void success(Boolean success);
    }
}
