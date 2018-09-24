package com.sundararaghavan.deliveryapp.screens.deliverylist.view;

import android.arch.lifecycle.LifecycleOwner;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sundararaghavan.deliveryapp.R;
import com.sundararaghavan.deliveryapp.model.Repo;
import com.sundararaghavan.deliveryapp.viewmodel.DeliveriesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> {

    private final List<Repo> data = new ArrayList<>();
    private final RepoSelectedListener repoSelectedListener;

    public RepoListAdapter(DeliveriesViewModel viewModel, LifecycleOwner lifecycleOwner, RepoSelectedListener repoSelectedListener) {
        this.repoSelectedListener = repoSelectedListener;
        viewModel.getRepos().observe(lifecycleOwner, repos -> setRepo(repos));
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_item_layout, parent, false);
        return new RepoViewHolder(view, repoSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setRepo(List<Repo> repos) {
        if (repos == null) {
            data.clear();
            notifyDataSetChanged();
            return;
        }
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new RepoDiffCallback(data, repos));
        data.clear();
        data.addAll(repos);
        diffResult.dispatchUpdatesTo(this);
    }

    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        private final Drawable drawable;
        private final RequestOptions options;
        @BindView(R.id.item_desc_tv)
        TextView prodDescTextView;

        @BindView(R.id.product_img)
        ImageView productImage;

        private Repo repo;

        RepoViewHolder(View itemView, RepoSelectedListener repoSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_pin_drop_black_24dp);
            options = new RequestOptions().centerCrop().placeholder(drawable);

            itemView.setOnClickListener(v -> {
                if (repo != null) {
                    repoSelectedListener.onRepoSelected(repo);
                }
            });
        }

        void bind(Repo repo) {
            this.repo = repo;
            prodDescTextView.setText(String.format("%s at %s", repo.description(), repo.location().address()));

            Glide.with(productImage.getContext()).load(repo.imageUrl()).
                    apply(options).into(productImage);

        }
    }
}