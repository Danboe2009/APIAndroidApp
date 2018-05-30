package com.webappclouds.apiandroidapp.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.webappclouds.apiandroidapp.activites.FoodTrucksListActivity;
import com.webappclouds.apiandroidapp.model.FoodTruck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataService {

    private static DataService instance = new DataService();

    private DataService() {
    }

    public static DataService getInstance() {
        return instance;
    }

    // Request all the FoodTrucks

    public ArrayList<FoodTruck> downloadAllFoodTrucks(Context context, final FoodTrucksListActivity.TrucksDownloaded listener) {
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

                listener.success(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("API", "Error: " + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(context).add(getTrucks);

        return foodTruckList;

    }
}
