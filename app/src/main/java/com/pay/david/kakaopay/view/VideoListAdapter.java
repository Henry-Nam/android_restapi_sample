package com.pay.david.kakaopay.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.pay.david.R;
import com.pay.david.kakaopay.contract.RepositoryListViewContract;
import com.pay.david.kakaopay.dao.ListSearchData;
import com.pay.david.kakaopay.dao.SearchData;
import com.pay.david.databinding.LayoutItemBinding;
import com.pay.david.kakaopay.viewmodel.RepositoryItemViewModel;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.RepoViewHolder> {
    public static final String LOG = VideoListAdapter.class.getSimpleName();

    private final RepositoryListViewContract view;
    private final Context context;
    private ListSearchData items;

    public VideoListAdapter(Context context, RepositoryListViewContract view) {
        this.context = context;
        this.view = view;

    }

    public void setItemsAndRefresh(ListSearchData items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public SearchData getItemAt(int position) {
        return items.items.get(position);
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG, "onCreateViewHolder");
        LayoutItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_item, parent, false);
        binding.setViewModel(new RepositoryItemViewModel(view));
        return new RepoViewHolder(binding.getRoot(), binding.getViewModel());
    }

    @Override
    public void onBindViewHolder(final RepoViewHolder holder, final int position) {
        Log.d(LOG, "onBindViewHolder");
        final SearchData item = getItemAt(position);
        holder.loadItem(item);

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.items.size();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {
        private final RepositoryItemViewModel viewModel;

        public RepoViewHolder(View itemView, RepositoryItemViewModel viewModel) {
            super(itemView);
            this.viewModel = viewModel;
        }

        public void loadItem(SearchData item) {
            viewModel.loadItem(item);
        }
    }

    public static class BindingAdapters {

        @BindingAdapter({"imageUrl"})
        public static void loadImage(final ImageView imageView, final String imageUrl) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(10f);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }
}
