package com.webappclouds.apiandroidapp.data;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.webappclouds.apiandroidapp.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AuthService {

    private static AuthService instance = new AuthService();
    private String authToken;

    public AuthService() {

    }

    public static AuthService getInstance() {
        return instance;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void registerUser(String email, String password, Context context) {

        try {
            String url = Constants.REGISTER;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            final String mRequestBody = jsonBody.toString();

            StringRequest registerRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

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
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    if (response.statusCode == 200 || response.statusCode == 409) {
                        // Listener.success(true);
                    }

                    return super.parseNetworkResponse(response);
                }
            };

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }
}
