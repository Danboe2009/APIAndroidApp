package com.missingcontroller.apiandroidapp.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.missingcontroller.apiandroidapp.R;
import com.missingcontroller.apiandroidapp.adapter.ReviewAdapter;
import com.missingcontroller.apiandroidapp.data.DataService;
import com.missingcontroller.apiandroidapp.model.FoodTruck;
import com.missingcontroller.apiandroidapp.model.FoodTruckReview;
import com.missingcontroller.apiandroidapp.view.ItemDecorator;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    private FoodTruck foodTruck;
    private ArrayList<FoodTruckReview> reviews = new ArrayList<>();
    private ReviewAdapter adapter;

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
                    setUpRecycler();
                    if (reviews.size() == 0) {
                        Toast.makeText(getBaseContext(), "No reviews for this food truck", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        reviews = DataService.getInstance().downloadReviews(this, foodTruck, listener);
    }

    public interface ReviewInterface {
        void success(Boolean success);
    }

    private void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_reviews);
        recyclerView.setHasFixedSize(true);
        adapter = new ReviewAdapter(reviews);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ItemDecorator(0, 0, 0, 10));
    }
}
