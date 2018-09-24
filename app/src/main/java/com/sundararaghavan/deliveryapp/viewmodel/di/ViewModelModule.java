package com.sundararaghavan.deliveryapp.viewmodel.di;

import android.arch.lifecycle.ViewModel;

import com.sundararaghavan.deliveryapp.viewmodel.DeliveriesViewModel;
import com.sundararaghavan.deliveryapp.viewmodel.SelectedRepoViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DeliveriesViewModel.class)
    abstract ViewModel bindProductsViewModel(DeliveriesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectedRepoViewModel.class)
    abstract ViewModel bindSelectedRepoViewModel(SelectedRepoViewModel viewModel);

}
