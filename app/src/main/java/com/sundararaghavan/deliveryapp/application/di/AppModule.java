package com.sundararaghavan.deliveryapp.application.di;

import android.app.Application;

import com.sundararaghavan.deliveryapp.home.di.MainActivityComponent;
import com.sundararaghavan.deliveryapp.screens.deliverylist.di.DeliveryListFragmentComponent;
import com.sundararaghavan.deliveryapp.screens.mapdetails.di.DetailsMapFragmentComponent;

import dagger.Module;

@Module(subcomponents = {
        MainActivityComponent.class,
        DeliveryListFragmentComponent.class,
        DetailsMapFragmentComponent.class,
})
class AppModule {

    private final Application application;

    AppModule(Application application) {
        this.application = application;
    }

}