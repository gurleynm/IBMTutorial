package com.android.arc;

import android.os.Bundle;

import com.android.arc.Pages.DefaultScreen;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            DefaultScreen landingFragment = new DefaultScreen();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, landingFragment).commit();
        }
    }
}
