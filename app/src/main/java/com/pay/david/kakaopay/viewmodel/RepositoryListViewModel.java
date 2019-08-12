package com.pay.david.kakaopay.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;

import com.pay.david.kakaopay.contract.RepositoryListViewContract;
import com.pay.david.kakaopay.dao.ListSearchData;
import com.pay.david.kakaopay.dao.SearchData;
import com.pay.david.kakaopay.model.ApiService;

import java.util.ArrayList;
import java.util.List;

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
                                repositoryListView.showRepositories(makeMockData());
                                progressBarVisibility.set(View.GONE);
                            }

                        });
    }

    public ListSearchData makeMockData() {
        SearchData.Default defaults = new SearchData.Default("https://i.ytimg.com/vi/F9CrRG6j2SM/default.jpg");
        SearchData.High highs = new SearchData.High("https://i.ytimg.com/vi/F9CrRG6j2SM/default.jpg");
        SearchData.Thumbnail thumbnails = new SearchData.Thumbnail(defaults, highs);
        SearchData.Snippet snippet = new SearchData.Snippet("111", "111", "test", thumbnails, "test");
        SearchData.VideoIds videoIds = new SearchData.VideoIds("F9CrRG6j2SM");
        SearchData d = new SearchData(snippet, videoIds);
        List<SearchData> items = new ArrayList<>();
        items.add(d);
        ListSearchData data = new ListSearchData(items);
        return data;
    }
}

