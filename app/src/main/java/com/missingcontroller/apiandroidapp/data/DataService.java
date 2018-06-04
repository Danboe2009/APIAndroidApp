package com.missingcontroller.apiandroidapp.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.missingcontroller.apiandroidapp.activites.AddReviewActivity;
import com.missingcontroller.apiandroidapp.activites.AddTruckActivity;
import com.missingcontroller.apiandroidapp.activites.FoodTrucksListActivity;
import com.missingcontroller.apiandroidapp.activites.ManageTruckActivity;
import com.missingcontroller.apiandroidapp.activites.ReviewsActivity;
import com.missingcontroller.apiandroidapp.constants.Constants;
import com.missingcontroller.apiandroidapp.model.FoodTruck;
import com.missingcontroller.apiandroidapp.model.FoodTruckReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataService {

    private static DataService instance = new DataService();

    private DataService() {
    }

    public static DataService getInstance() {
        return instance;
    }

    // Request all the FoodTrucks

    public ArrayList<FoodTruck> downloadAllFoodTrucks(Context context, final FoodTrucksListActivity.TrucksDownloaded listener) {
        String url = Constants.GET_FOOD_TRUCKS;
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

    // Request all the FoodTrucks Reviews

    public ArrayList<FoodTruckReview> downloadReviews(Context context, FoodTruck foodTruck, final ReviewsActivity.ReviewInterface listener) {
        String url = Constants.GET_REVIEWS + foodTruck.getId();
        final ArrayList<FoodTruckReview> reviewsList = new ArrayList<>();

        final JsonArrayRequest getReviews = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString());

                try {
                    JSONArray reviews = response;
                    for (int x = 0; x < reviews.length(); x++) {
                        JSONObject review = reviews.getJSONObject(x);
                        String title = review.getString("title");
                        String id = review.getString("_id");
                        String text = review.getString("text");

                        FoodTruckReview newFoodTruckReview = new FoodTruckReview(id, title, text);
                        reviewsList.add(newFoodTruckReview);
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

        Volley.newRequestQueue(context).add(getReviews);

        return reviewsList;
    }

    // AddReview Post
    public void addReview(String title, String text, FoodTruck foodTruck, Context context, final AddReviewActivity.AddReviewInterface listener, String authToken) {

        try {

            String url = Constants.ADD_REVIEW + foodTruck.getId();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("title", title);
            jsonBody.put("text", text);
            jsonBody.put("foodtruck", foodTruck.getId());
            final String mRequestBody = jsonBody.toString();
            final String bearer = "Bearer " + authToken;

            JsonObjectRequest reviewRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Log.i("JSON Message", message);
                    } catch (JSONException e) {
                        Log.v("JSON", "EXEC: " + e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding", mRequestBody, "utf-8");
                        return null;

                    }
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    if (response.statusCode == 200) {
                        listener.success(true);
                    }
                    return super.parseNetworkResponse(response);
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", bearer);
                    return headers;
                }
            };

            Volley.newRequestQueue(context).add(reviewRequest);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    // Add truck Post
    public void addTruck(String name, String foodType, Double avgCost, Double latitude, Double longitude, Context context, final AddTruckActivity.AddTruckInterface listener, String authToken) {

        try {

            String url = Constants.ADD_TRUCK;

            JSONObject geometry = new JSONObject();
            JSONObject coordinates = new JSONObject();
            coordinates.put("lat", latitude);
            coordinates.put("long", latitude);
            geometry.put("coordinates", coordinates);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("foodtype", foodType);
            jsonBody.put("avgcost", avgCost);
            jsonBody.put("geometry", geometry);

            final String mRequestBody = jsonBody.toString();
            final String bearer = "Bearer " + authToken;

            JsonObjectRequest addTruck = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Log.i("JSON Message", message);
                    } catch (JSONException e) {
                        Log.v("JSON", "EXEC: " + e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding", mRequestBody, "utf-8");
                        return null;

                    }
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    if (response.statusCode == 200) {
                        listener.success(true);
                    }
                    return super.parseNetworkResponse(response);
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", bearer);
                    return headers;
                }
            };

            Volley.newRequestQueue(context).add(addTruck);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    // Modify truck Post
    public void modifyTruck(String name, String foodType, Double avgCost, Double latitude, Double longitude, Context context, FoodTruck foodTruck, final ManageTruckActivity.ModifyTruckInterface listener, String authToken) {

        try {

            String url = Constants.MODIFY_TRUCK + foodTruck.getId();

            JSONObject geometry = new JSONObject();
            JSONObject coordinates = new JSONObject();
            coordinates.put("lat", latitude);
            coordinates.put("long", latitude);
            geometry.put("coordinates", coordinates);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("foodtype", foodType);
            jsonBody.put("avgcost", avgCost);
            jsonBody.put("geometry", geometry);

            final String mRequestBody = jsonBody.toString();
            final String bearer = "Bearer " + authToken;

            JsonObjectRequest addTruck = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Log.i("JSON Message", message);
                    } catch (JSONException e) {
                        Log.v("JSON", "EXEC: " + e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding", mRequestBody, "utf-8");
                        return null;

                    }
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    if (response.statusCode == 200) {
                        listener.success(true);
                    }
                    return super.parseNetworkResponse(response);
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", bearer);
                    return headers;
                }
            };

            Volley.newRequestQueue(context).add(addTruck);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
