package com.example.aio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailsActivity extends AppCompatActivity {
    public static final String  ARG_ITEM_ID = "itemId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView vip = findViewById(R.id.vip);
        vip.setOnClickListener(view -> getVip());

/*        MainActivity activity = (MainActivity) requireActivity();
        ImageView backButton = findViewById(R.id.back_icon);
        backButton.setOnClickListener(view1 -> activity.onBackPressed());*/
        Intent intent = getIntent();
        int position = intent.getIntExtra(ARG_ITEM_ID, 0);
        if (position == -1){
            finish();
            return;
        }
    }

    private void getVip() {
        Intent intent = new Intent(this, PremiumActivity.class);
        startActivity(intent);
    }
}