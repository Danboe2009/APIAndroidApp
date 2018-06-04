package com.missingcontroller.apiandroidapp.activites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.missingcontroller.apiandroidapp.R;
import com.missingcontroller.apiandroidapp.model.FoodTruck;

public class ManageTruckActivity extends AppCompatActivity {

    private EditText modifyName;
    private EditText modifyFoodType;
    private EditText modifyAvgCost;
    private EditText modifyLatitude;
    private EditText modifyLongitude;
    private Button submitBtn;
    private Button cancelBtn;

    private FoodTruck foodTruck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_truck);

        modifyName = findViewById(R.id.modify_name_edit);
        modifyFoodType = findViewById(R.id.modify_foodtype_edit);
        modifyAvgCost = findViewById(R.id.modify_avgcost_edit);
        modifyLatitude = findViewById(R.id.modify_latitude);
        modifyLongitude = findViewById(R.id.modify_longitude);

        submitBtn = findViewById(R.id.modify_submit);
        cancelBtn = findViewById(R.id.modify_cancel);

        foodTruck = getIntent().getParcelableExtra(FoodTruckDetailActivity.EXTRA_ITEM_TRUCK);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
