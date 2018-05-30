package com.webappclouds.apiandroidapp.activites;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        truckName = findViewById(R.id.detail_truck_name);
        foodType = findViewById(R.id.detail_food_type);
        avgCost = findViewById(R.id.detail_food_cost);

        foodTruck = getIntent().getParcelableExtra(FoodTrucksListActivity.EXTRA_ITEM_TRUCK);
        updateUI();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng foodTruckLocation = new LatLng(foodTruck.getLatitude(), foodTruck.getLongitude());
        mMap.addMarker(new MarkerOptions().position(foodTruckLocation).title(foodTruck.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(foodTruckLocation));
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
}
