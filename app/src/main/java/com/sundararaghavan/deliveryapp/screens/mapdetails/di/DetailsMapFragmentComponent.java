package com.sundararaghavan.deliveryapp.screens.mapdetails.di;


import com.sundararaghavan.deliveryapp.screens.mapdetails.DetailsMapFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;


@Subcomponent
public interface DetailsMapFragmentComponent extends AndroidInjector<DetailsMapFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DetailsMapFragment> {

        @Override
        public void seedInstance(DetailsMapFragment instance) {

        }
    }
}