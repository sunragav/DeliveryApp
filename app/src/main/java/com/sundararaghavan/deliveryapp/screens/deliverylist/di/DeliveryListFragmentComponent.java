package com.sundararaghavan.deliveryapp.screens.deliverylist.di;


import com.sundararaghavan.deliveryapp.screens.deliverylist.DeliveryListFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;


@Subcomponent
public interface DeliveryListFragmentComponent extends AndroidInjector<DeliveryListFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DeliveryListFragment> {

        @Override
        public void seedInstance(DeliveryListFragment instance) {

        }
    }
}