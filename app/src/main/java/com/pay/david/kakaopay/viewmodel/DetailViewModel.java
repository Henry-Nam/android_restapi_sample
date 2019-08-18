package com.pay.david.kakaopay.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.pay.david.kakaopay.contract.DetailSubItemViewContract;
import com.pay.david.kakaopay.contract.DetailViewContract;
import com.pay.david.kakaopay.dao.ListSearchData;
import com.pay.david.kakaopay.dao.SearchData;
import com.pay.david.kakaopay.model.ApiService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.pay.david.kakaopay.util.Constants.API_KEY;

public class DetailViewModel {
    public static final String LOG = DetailViewModel.class.getSimpleName();
    public final ObservableInt progressBarVisibility = new ObservableInt(View.VISIBLE);

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> contents = new ObservableField<>();
    public ObservableField<String> url = new ObservableField<>();
    final DetailViewContract detailView;
    final DetailSubItemViewContract subView;
    private final ApiService apiService;

    public DetailViewModel(DetailViewContract detailView, DetailSubItemViewContract subView, ApiService apiService) {
        this.detailView = detailView;
        this.subView = subView;
        this.apiService = apiService;

    }

    public void loadItem(final SearchData data) {
        progressBarVisibility.set(View.GONE);
        this.url.set(data.snippet.thumbnails.defaults.url);
        this.title.set(data.snippet.title);
        this.contents.set(data.snippet.description);

        getChannelInfo(this.title.get());
    }

    public void getChannelInfo(String title) {
        Log.d(LOG, "getChannelInfo");
        progressBarVisibility.set(View.VISIBLE);
        Observable<ListSearchData> observable = apiService.getSearchList("id, snippet", title, API_KEY, "video", 10);
        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<ListSearchData>() {

                            @Override
                            public void onNext(ListSearchData response) {
                                progressBarVisibility.set(View.GONE);
                                Log.d(LOG, "onNext");
                                subView.showSubItemList(response);
                            }

                            @Override
                            public void onCompleted() {
                                Log.d(LOG, "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(LOG, "onError" + e.getMessage());
                                detailView.showError(e.getMessage());
                                progressBarVisibility.set(View.GONE);
                            }

                        });
    }

}

