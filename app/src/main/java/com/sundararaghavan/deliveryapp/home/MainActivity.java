package com.sundararaghavan.deliveryapp.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sundararaghavan.deliveryapp.R;
import com.sundararaghavan.deliveryapp.screens.deliverylist.DeliveryListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_container, new DeliveryListFragment())
                    .commit();
        }
    }
}
