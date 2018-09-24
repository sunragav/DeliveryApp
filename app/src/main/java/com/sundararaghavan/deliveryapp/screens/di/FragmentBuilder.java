package com.sundararaghavan.deliveryapp.screens.di;

import android.support.v4.app.Fragment;

import com.sundararaghavan.deliveryapp.screens.deliverylist.DeliveryListFragment;
import com.sundararaghavan.deliveryapp.screens.deliverylist.di.DeliveryListFragmentComponent;
import com.sundararaghavan.deliveryapp.screens.mapdetails.DetailsMapFragment;
import com.sundararaghavan.deliveryapp.screens.mapdetails.di.DetailsMapFragmentComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class FragmentBuilder {

    @Binds
    @IntoMap
    @SupportFragmentKey(DeliveryListFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindDeliveryFragment(DeliveryListFragmentComponent.Builder builder);

    @Binds
    @IntoMap
    @SupportFragmentKey(DetailsMapFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindDetailsFragment(DetailsMapFragmentComponent.Builder builder);

}
