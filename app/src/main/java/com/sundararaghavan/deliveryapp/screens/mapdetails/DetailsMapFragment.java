package com.sundararaghavan.deliveryapp.screens.mapdetails;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sundararaghavan.deliveryapp.R;
import com.sundararaghavan.deliveryapp.viewmodel.SelectedRepoViewModel;
import com.sundararaghavan.deliveryapp.viewmodel.di.ViewModelFactory;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public class DetailsMapFragment extends Fragment {
    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.details_tv)
    TextView repoDescriptionTextView;
    @BindView(R.id.detail_map_tv_error)
    TextView errorTextView;

    private Unbinder unbinder;
    private SelectedRepoViewModel selectedRepoViewModel;
    private SupportMapFragment supportMapFragment;
    @BindView(R.id.product_img)
    ImageView productImage;
    private Context context;
    private RequestOptions options;
    private Drawable drawable;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        drawable = ContextCompat.getDrawable(context, R.drawable.ic_pin_drop_black_24dp);
        options = new RequestOptions().centerCrop().placeholder(drawable);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delivery_detail_map_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        selectedRepoViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), viewModelFactory)
                .get(SelectedRepoViewModel.class);
        selectedRepoViewModel.restoreFromBundle(savedInstanceState);
        displayRepo();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        selectedRepoViewModel.saveToBundle(outState);
    }


    private void displayRepo() {
        selectedRepoViewModel.getSelectedRepo().observe(this, repo -> {
            final LatLng latLng = new LatLng(Double.valueOf(repo.location().lat()), Double.valueOf(repo.location().lng()));
            final MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            supportMapFragment.getMapAsync(googleMap -> {
                googleMap.addMarker(markerOptions);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            });

            Glide.with(context).load(repo.imageUrl()).
                    apply(options).into(productImage);
            repoDescriptionTextView.setText(repo.description() + " at " + repo.location().address());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
