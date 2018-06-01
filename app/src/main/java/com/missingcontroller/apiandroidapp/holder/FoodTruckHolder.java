package com.missingcontroller.apiandroidapp.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.missingcontroller.apiandroidapp.R;
import com.missingcontroller.apiandroidapp.model.FoodTruck;

public class FoodTruckHolder extends RecyclerView.ViewHolder {

    private TextView truckName;
    private TextView foodType;
    private TextView avgCost;

    public FoodTruckHolder(View itemView) {
        super(itemView);

        this.truckName = itemView.findViewById(R.id.food_truck_name);
        this.foodType = itemView.findViewById(R.id.food_truck_type);
        this.avgCost = itemView.findViewById(R.id.food_truck_cost);
    }

    public void updateUI(FoodTruck foodTruck) {
        truckName.setText(foodTruck.getName());
        foodType.setText(foodTruck.getFoodType());
        avgCost.setText("$" + foodTruck.getAvgCost());
    }
}
