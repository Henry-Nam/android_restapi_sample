package com.pay.david.kakaopay.contract;

import com.pay.david.kakaopay.dao.ListSearchData;
import com.pay.david.kakaopay.dao.SearchData;

public interface DetailSubItemViewContract {

  void reload(SearchData data);

  void showSubItemList(ListSearchData response);
}


