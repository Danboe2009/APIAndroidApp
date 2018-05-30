package com.webappclouds.apiandroidapp.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.webappclouds.apiandroidapp.R;
import com.webappclouds.apiandroidapp.adapter.FoodTruckAdapter;
import com.webappclouds.apiandroidapp.model.FoodTruck;
import com.webappclouds.apiandroidapp.view.ItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodTrucksListActivity extends AppCompatActivity {

    private FoodTruckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_trucks_list);

        String url = "https://missingcontroller.com/api/v1/foodtruck";
        final ArrayList<FoodTruck> foodTruckList = new ArrayList<>();

        final JsonArrayRequest getTrucks = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString());

                try {
                    JSONArray foodTrucks = response;
                    for (int x = 0; x < foodTrucks.length(); x++) {
                        JSONObject foodTruck = foodTrucks.getJSONObject(x);
                        String name = foodTruck.getString("name");
                        String id = foodTruck.getString("_id");
                        String foodType = foodTruck.getString("foodtype");
                        Double avgcost = foodTruck.getDouble("avgcost");

                        JSONObject geometry = foodTruck.getJSONObject("geometry");
                        JSONObject coordinates = geometry.getJSONObject("coordinates");

                        Double latitude = coordinates.getDouble("lat");
                        Double longitude = coordinates.getDouble("long");

                        FoodTruck newFoodTruck = new FoodTruck(id, name, foodType, avgcost, latitude, longitude);

                        foodTruckList.add(newFoodTruck);
                    }
                } catch (JSONException e) {
                    Log.e("JSON", "EXC" + e.getLocalizedMessage());
                }

                System.out.println("This is the first food truck name " + foodTruckList.get(0).getName());

                RecyclerView recyclerView = findViewById(R.id.recycler_foodtruck);
                recyclerView.setHasFixedSize(true);
                adapter = new FoodTruckAdapter(foodTruckList);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.addItemDecoration(new ItemDecorator(0, 0, 0, 10));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("API", "Error: " + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(this).add(getTrucks);
    }
}
