package com.webappclouds.apiandroidapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.webappclouds.apiandroidapp.R;
import com.webappclouds.apiandroidapp.model.FoodTruck;

public class FoodTruckDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FoodTruck foodTruck;
    private TextView truckName;
    private TextView foodType;
    private TextView avgCost;
    public static final String EXTRA_ITEM_TRUCK = "TRUCK";
    private Button addReviewBtn;
    private Button viewReviewsBtn;
    private Button modifyTrucksBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        truckName = findViewById(R.id.detail_truck_name);
        foodType = findViewById(R.id.detail_food_type);
        avgCost = findViewById(R.id.detail_food_cost);
        addReviewBtn = findViewById(R.id.add_review_bt);
        viewReviewsBtn = findViewById(R.id.view_reviews_bt);
        modifyTrucksBtn = findViewById(R.id.modify_truck_bt);

        foodTruck = getIntent().getParcelableExtra(FoodTrucksListActivity.EXTRA_ITEM_TRUCK);
        updateUI();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewReviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadReviews(foodTruck);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng foodTruckLocation = new LatLng(foodTruck.getLatitude(), foodTruck.getLongitude());
        mMap.addMarker(new MarkerOptions().position(foodTruckLocation).title(foodTruck.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(foodTruckLocation, 10));
        setUpMap();
    }

    private void updateUI() {
        truckName.setText(foodTruck.getName());
        foodType.setText(foodTruck.getFoodType());
        avgCost.setText("$" + foodTruck.getAvgCost());
    }

    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void loadReviews(FoodTruck truck) {
        Intent intent = new Intent(FoodTruckDetailActivity.this, ReviewsActivity.class);
        intent.putExtra(FoodTruckDetailActivity.EXTRA_ITEM_TRUCK, truck);
        startActivity(intent);
    }
}
