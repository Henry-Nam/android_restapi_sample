package com.pay.david.kakaopay.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;

import com.pay.david.kakaopay.contract.RepositoryListViewContract;
import com.pay.david.kakaopay.dao.ListSearchData;
import com.pay.david.kakaopay.model.ApiService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.pay.david.kakaopay.util.Constants.API_KEY;
import static com.pay.david.kakaopay.util.Constants.COUNT;
import static com.pay.david.kakaopay.util.Constants.SNIPPET;


public class RepositoryListViewModel {

    public final ObservableInt progressBarVisibility = new ObservableInt(View.VISIBLE);
    private final RepositoryListViewContract repositoryListView;
    private final ApiService apiService;

    public RepositoryListViewModel(RepositoryListViewContract repositoryListView, ApiService apiService) {
        this.repositoryListView = repositoryListView;
        this.apiService = apiService;

        getSearchList(SNIPPET, null, API_KEY, COUNT);
    }

    public void getSearchList(String query) {
        getSearchList(SNIPPET, query, API_KEY, COUNT);
    }

    public void getSearchList(String part, String query, String key, int count) {
        progressBarVisibility.set(View.VISIBLE);
        Observable<ListSearchData> observable = apiService.getSearchList(part, query, key, "video", count);
        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<ListSearchData>() {

                            @Override
                            public void onNext(ListSearchData response) {
                                progressBarVisibility.set(View.GONE);
                                repositoryListView.showRepositories(response);
                            }

                            @Override
                            public void onCompleted() {
                                progressBarVisibility.set(View.GONE);
                            }

                            @Override
                            public void onError(Throwable e) {
                                repositoryListView.showError(e.getMessage());
//                                if(NetworkUtil.getInstance(get))
                                progressBarVisibility.set(View.GONE);
                            }

                        });
    }

}

