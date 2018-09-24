package com.sundararaghavan.deliveryapp.application.di;


import android.app.Application;

import com.sundararaghavan.deliveryapp.application.DeliveryApplication;
import com.sundararaghavan.deliveryapp.home.MainActivity;
import com.sundararaghavan.deliveryapp.home.di.ActivityBuilder;
import com.sundararaghavan.deliveryapp.network.di.NetworkModule;
import com.sundararaghavan.deliveryapp.screens.deliverylist.DeliveryListFragment;
import com.sundararaghavan.deliveryapp.screens.deliverylist.view.RepoListAdapter;
import com.sundararaghavan.deliveryapp.screens.di.FragmentBuilder;
import com.sundararaghavan.deliveryapp.screens.mapdetails.DetailsMapFragment;
import com.sundararaghavan.deliveryapp.viewmodel.di.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        ActivityBuilder.class,
        FragmentBuilder.class,
        AppModule.class,
        NetworkModule.class,
        ViewModelModule.class,
})
public interface DeliveryApplicationComponent {
    void inject(DeliveryApplication deliveryApplication);

    void inject(DeliveryListFragment listFragment);

    void inject(DetailsMapFragment detailsFragment);

    void inject(MainActivity mainActivity);

    void inject(RepoListAdapter repoListAdapter);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        DeliveryApplicationComponent build();
    }
}
