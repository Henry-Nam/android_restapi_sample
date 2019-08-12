package com.pay.david.kakaopay.contract;

import com.pay.david.kakaopay.dao.ListSearchData;
import com.pay.david.kakaopay.dao.SearchData;

public interface RepositoryListViewContract {

    void showRepositories(ListSearchData response);

    void showError(String message);

    void showDetailFragment(SearchData response);


}


