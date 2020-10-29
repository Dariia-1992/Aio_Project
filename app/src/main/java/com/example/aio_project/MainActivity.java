package com.example.aio_project;

import androidx.appcompat.app.AppCompatActivity;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import android.content.Context;
import android.os.Bundle;

import com.example.aio_project.model.DataRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);     // TODO: is it necessary?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataRepository.loadImages();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}