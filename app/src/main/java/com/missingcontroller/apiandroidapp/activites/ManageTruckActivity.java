package com.missingcontroller.apiandroidapp.activites;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.missingcontroller.apiandroidapp.R;
import com.missingcontroller.apiandroidapp.constants.Constants;
import com.missingcontroller.apiandroidapp.data.DataService;
import com.missingcontroller.apiandroidapp.model.FoodTruck;

public class ManageTruckActivity extends AppCompatActivity {

    private EditText modifyName;
    private EditText modifyFoodType;
    private EditText modifyAvgCost;
    private EditText modifyLatitude;
    private EditText modifyLongitude;
    private Button submitBtn;
    private Button cancelBtn;

    SharedPreferences prefs;
    String authToken;

    private FoodTruck foodTruck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_truck);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        authToken = prefs.getString(Constants.AUTH_TOKEN, "Doesnt Matter");

        modifyName = findViewById(R.id.modify_name_edit);
        modifyFoodType = findViewById(R.id.modify_foodtype_edit);
        modifyAvgCost = findViewById(R.id.modify_avgcost_edit);
        modifyLatitude = findViewById(R.id.modify_latitude);
        modifyLongitude = findViewById(R.id.modify_longitude);

        submitBtn = findViewById(R.id.modify_submit);
        cancelBtn = findViewById(R.id.modify_cancel);

        foodTruck = getIntent().getParcelableExtra(FoodTruckDetailActivity.EXTRA_ITEM_TRUCK);

        modifyName.setText(foodTruck.getName());
        modifyFoodType.setText(foodTruck.getFoodType());
        modifyAvgCost.setText("" + foodTruck.getAvgCost());
        modifyLatitude.setText("" + foodTruck.getLatitude());
        modifyLongitude.setText("" + foodTruck.getLongitude());

        final ModifyTruckInterface listener = new ModifyTruckInterface() {
            @Override
            public void success(Boolean success) {
                finish();
            }
        };

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = modifyName.getText().toString();
                final String type = modifyFoodType.getText().toString();
                final Double cost = Double.parseDouble(modifyAvgCost.getText().toString());
                final Double lat = Double.parseDouble(modifyLatitude.getText().toString());
                final Double longi = Double.parseDouble(modifyLongitude.getText().toString());

                DataService.getInstance().modifyTruck(name, type, cost, lat, longi, getBaseContext(), foodTruck, listener, authToken);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public interface ModifyTruckInterface {
        void success(Boolean success);
    }
}
