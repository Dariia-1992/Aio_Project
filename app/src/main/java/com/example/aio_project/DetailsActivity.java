package com.example.aio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {
    public static final String  ARG_ITEM_ID = "itemId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        int position = intent.getIntExtra(ARG_ITEM_ID, -1);
        if (position == -1){
            finish();
            return;
        }
    }
}