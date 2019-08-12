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
import com.pay.david.databinding.LayoutSubItemBinding;
import com.pay.david.kakaopay.contract.DetailSubItemViewContract;
import com.pay.david.kakaopay.contract.DetailViewContract;
import com.pay.david.kakaopay.dao.ListSearchData;
import com.pay.david.kakaopay.dao.SearchData;
import com.pay.david.kakaopay.model.ApiManager;
import com.pay.david.kakaopay.model.ApiService;
import com.pay.david.kakaopay.viewmodel.DetailSubItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubItemListAdapter extends RecyclerView.Adapter<SubItemListAdapter.RepoViewHolder> {
    public static final String LOG = SubItemListAdapter.class.getSimpleName();

    private final DetailSubItemViewContract view;
    private DetailSubItemViewModel viewModel;
    private ListSearchData items;
    LayoutSubItemBinding binding;
    private boolean isStarted = false;

    public void mockData() {
        SearchData.Default defaults = new SearchData.Default("https://i.ytimg.com/vi/F9CrRG6j2SM/default.jpg");
        SearchData.High highs = new SearchData.High("https://i.ytimg.com/vi/F9CrRG6j2SM/default.jpg");
        SearchData.Thumbnail thumbnails = new SearchData.Thumbnail(defaults, highs);
        SearchData.Snippet snippet = new SearchData.Snippet("111", "111", "test", thumbnails, "test");
        SearchData.VideoIds videoIds = new SearchData.VideoIds("F9CrRG6j2SM");
        SearchData d = new SearchData(snippet, videoIds);
        List<SearchData> items = new ArrayList<>();
        items.add(d);
        ListSearchData data = new ListSearchData(items);
        this.items = data;
    }

    public SubItemListAdapter(DetailSubItemViewContract view) {
        this.view = view;
        Log.d(LOG, "SubItemListAdapter");
    }

    public void setItemsAndRefresh(ListSearchData items) {
        this.items = items;
        if(items != null) {
            Log.d(LOG, "items.size" + items.items.size());
        }
        notifyDataSetChanged();
    }

    public SearchData getItemAt(int position) {
        return items.items.get(position);
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG, "onCreateViewHolder");
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_sub_item, parent, false);
        binding.setViewModel(new DetailSubItemViewModel(view, ApiManager.getInstance().create(ApiService.class)));
        viewModel = binding.getViewModel();
        return new RepoViewHolder(binding.getRoot(), binding.getViewModel());
    }

    @Override
    public void onBindViewHolder(final RepoViewHolder holder, final int position) {
        Log.d(LOG, "onBindViewHolder");
        final SearchData item = getItemAt(position);
        if(isStarted) {
            holder.loadItem(item);
        } else {
            isStarted = true;
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            Log.d(LOG, "mockData()");
            mockData();
        }
        return items.items.size();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {
        private final DetailSubItemViewModel viewModel;

        public RepoViewHolder(View itemView, DetailSubItemViewModel viewModel) {
            super(itemView);
            this.viewModel = viewModel;
        }

        public void loadItem(SearchData item) {
            Log.d(LOG, "loadItem");
            viewModel.loadItem(item);

        }
    }

    public static class BindingAdapters {

        @BindingAdapter({"subImageUrl"})
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
