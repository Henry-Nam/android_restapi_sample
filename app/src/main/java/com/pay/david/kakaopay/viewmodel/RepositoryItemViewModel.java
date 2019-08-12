package com.pay.david.kakaopay.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.pay.david.kakaopay.contract.RepositoryListViewContract;
import com.pay.david.kakaopay.dao.SearchData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class RepositoryItemViewModel {
  public ObservableField<String> title = new ObservableField<>();
  public ObservableField<String> description = new ObservableField<>();
  public ObservableField<String> url = new ObservableField<>();
  public ObservableField<String> publishedAt = new ObservableField<>();

  public RepositoryListViewContract view;
  public SearchData item;

  public RepositoryItemViewModel(RepositoryListViewContract view) {
    this.view = view;
  }

  public void loadItem(SearchData item) {
    this.item = item;
    title.set(item.snippet.title);
    description.set(item.snippet.description);
    url.set(item.snippet.thumbnails.defaults.url);
    publishedAt.set(convertTime(item.snippet.publishedAt));
  }

  public String convertTime(String publishedAt) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      Date time = Date.from(Instant.parse(publishedAt));
      return formatter.format(time);
    } else {
      return publishedAt;
    }
  }

  public void onItemClick(View itemView) {
    view.showDetailFragment(item);
  }
}
