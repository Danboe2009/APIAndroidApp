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

public class AddReviewActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private EditText reviewTitle;
    private EditText reviewText;
    private Button addReviewBtn;
    private Button cancelBtn;
    private FoodTruck foodTruck;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        reviewTitle = findViewById(R.id.review_title);
        reviewText = findViewById(R.id.review_text);
        addReviewBtn = findViewById(R.id.add_review_btn);
        cancelBtn = findViewById(R.id.cancel_review_btn);

        foodTruck = getIntent().getParcelableExtra(FoodTruckDetailActivity.EXTRA_ITEM_TRUCK);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        authToken = prefs.getString(Constants.AUTH_TOKEN, "Does not exist");

        final AddReviewInterface listener = new AddReviewInterface() {
            @Override
            public void success(boolean success) {
                finish();
            }
        };

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = reviewTitle.getText().toString();
                final String text = reviewText.getText().toString();
                DataService.getInstance().addReview(title, text, foodTruck, getBaseContext(), listener, authToken);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public interface AddReviewInterface {
        void success(boolean success);
    }
}
