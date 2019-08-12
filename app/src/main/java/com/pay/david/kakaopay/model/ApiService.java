package com.pay.david.kakaopay.model;

import com.pay.david.kakaopay.dao.ListSearchData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {
    String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    @GET("search/")
    Observable<ListSearchData> getSearchList(@Query("part") String part,
                                             @Query("q") String query,
                                             @Query("key") String key,
                                             @Query("type") String type,
                                             @Query("maxResults") int results);
    @GET("videos/")
    Observable<ListSearchData> getChannelInfo(@Query("part") String part,
                                          @Query("id") String query,
                                          @Query("key") String key);

}