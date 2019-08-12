package com.pay.david.kakaopay.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.pay.david.kakaopay.contract.DetailSubItemViewContract;
import com.pay.david.kakaopay.dao.SearchData;
import com.pay.david.kakaopay.model.ApiService;

public class DetailSubItemViewModel {
    public static final String LOG = DetailSubItemViewModel.class.getSimpleName();
    public final ObservableInt progressBarVisibility = new ObservableInt(View.VISIBLE);
    public ObservableField<String> url = new ObservableField<>();

    public DetailSubItemViewContract detailSubView;
    public SearchData item;
    private final ApiService apiService;

    public DetailSubItemViewModel(DetailSubItemViewContract detailSubView, ApiService apiService) {
        this.detailSubView = detailSubView;
        this.apiService = apiService;
    }

    public void loadItem(SearchData item) {
        this.item = item;
        progressBarVisibility.set(View.GONE);
        url.set(item.snippet.thumbnails.defaults.url);
    }

    public void onItemClick(View itemView) {
        detailSubView.reload(item);
    }

}
