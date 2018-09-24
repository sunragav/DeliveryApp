package com.sundararaghavan.deliveryapp.screens.deliverylist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sundararaghavan.deliveryapp.R;
import com.sundararaghavan.deliveryapp.model.Repo;
import com.sundararaghavan.deliveryapp.screens.deliverylist.view.RepoListAdapter;
import com.sundararaghavan.deliveryapp.screens.deliverylist.view.RepoSelectedListener;
import com.sundararaghavan.deliveryapp.screens.mapdetails.DetailsMapFragment;
import com.sundararaghavan.deliveryapp.viewmodel.DeliveriesViewModel;
import com.sundararaghavan.deliveryapp.viewmodel.SelectedRepoViewModel;
import com.sundararaghavan.deliveryapp.viewmodel.di.ViewModelFactory;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

import static android.support.constraint.Constraints.TAG;

public class DeliveryListFragment extends Fragment implements RepoSelectedListener {
    private final static int DATA_FETCHING_INTERVAL = 5 * 1000; //5 seconds
    private static final int ITEMS_COUNT = 20;
    boolean isLoadMore = false;
    @Inject
    ViewModelFactory viewModelFactory;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView listView;
    @BindView(R.id.tv_error)
    TextView errorTextView;
    @BindView(R.id.loading_view)
    View loadingView;
    private long mLastFetchedDataTimeStamp;
    private Unbinder unbinder;
    private DeliveriesViewModel viewModel;
    private int id = 0;
    private boolean isLoading;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delivery_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id", id);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("id", 0);
        }
        if (id != 0) isLoadMore = true;
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DeliveriesViewModel.class);
        viewModel.fetchRepos(id, ITEMS_COUNT);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (!isLoading) {
                if (System.currentTimeMillis() - mLastFetchedDataTimeStamp < DATA_FETCHING_INTERVAL) {
                    Timber.d("\tNot fetching from network because interval didn't reach");
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }
                viewModel.fetchRepos(id, ITEMS_COUNT);
            }
        });

        listView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new RepoListAdapter(viewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        isLoading = true;
                        Log.d(TAG, "fetchData called for id:" + id);
                        //Do pagination.. i.e. fetch new data
                        id += ITEMS_COUNT;
                        viewModel.fetchRepos(id, ITEMS_COUNT);
                    }
                }
            }
        });
        observeViewModel();
    }

    @Override
    public void onRepoSelected(Repo repo) {
        SelectedRepoViewModel selectedRepoViewModel = ViewModelProviders.of(getActivity(), viewModelFactory)
                .get(SelectedRepoViewModel.class);
        selectedRepoViewModel.setSelectedRepo(repo);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new DetailsMapFragment())
                .addToBackStack(null)
                .commit();
    }

    private void observeViewModel() {
        viewModel.getRepos().observe(this, this::onRepo);
        viewModel.getError().observe(this, this::onError);
        viewModel.getLoading().observe(this, this::onLoading);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    private void onError(Boolean isError) {
        isLoading = false;
        if (isError) {
            errorTextView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            errorTextView.setText(R.string.api_error_repos);
        } else {
            errorTextView.setVisibility(View.GONE);
            errorTextView.setText(null);
        }
    }

    private void onLoading(Boolean isLoading) {
        this.isLoading = isLoading;
        //noinspection ConstantConditions
        loadingView.setVisibility(isLoading && !isLoadMore ? View.VISIBLE : View.GONE);
        if (isLoading) {
            errorTextView.setVisibility(View.GONE);
            if (isLoadMore) listView.setVisibility(View.GONE);
        }
    }

    private void onRepo(List<Repo> repos) {
        isLoading = false;
        mLastFetchedDataTimeStamp = System.currentTimeMillis();
        mSwipeRefreshLayout.setRefreshing(false);
        if (repos != null) {
            Timber.d("Thread->" + Thread.currentThread().getName() + "\nData Size:" +
                    repos.size() + "\nAdapter Data Size:" + listView.getAdapter().getItemCount());

            listView.setVisibility(View.VISIBLE);
        }
    }
}
