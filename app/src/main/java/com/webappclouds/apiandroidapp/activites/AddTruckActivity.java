package com.webappclouds.apiandroidapp.activites;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.webappclouds.apiandroidapp.R;
import com.webappclouds.apiandroidapp.constants.Constants;

public class AddTruckActivity extends AppCompatActivity {

    String authToken;
    SharedPreferences prefs;
    private EditText truckName;
    private EditText foodType;
    private EditText avgCost;
    private EditText latitude;
    private EditText longitude;
    private Button addBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_truck);

        truckName = findViewById(R.id.new_food_truck_name);
        foodType = findViewById(R.id.new_food_type);
        avgCost = findViewById(R.id.new_avg_cost);
        latitude = findViewById(R.id.new_latitude);
        longitude = findViewById(R.id.new_longitude);

        addBtn = findViewById(R.id.add_truck);
        cancelBtn = findViewById(R.id.cancel_truck);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        authToken = prefs.getString(Constants.AUTH_TOKEN, "Does not exist.");

        AddTruckInterface listener = new AddTruckInterface() {
            @Override
            public void success(Boolean success) {

            }
        };

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public interface AddTruckInterface {
        void success(Boolean success);
    }
}
