package com.wangxuhui.navigationdrawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsAboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_about_me);
        getSupportActionBar().setTitle("About Me");
    }
}
