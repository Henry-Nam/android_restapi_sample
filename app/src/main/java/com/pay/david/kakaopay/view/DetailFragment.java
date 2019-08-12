package com.pay.david.kakaopay.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pay.david.R;
import com.pay.david.databinding.FragmentDetailBinding;
import com.pay.david.kakaopay.contract.DetailSubItemViewContract;
import com.pay.david.kakaopay.contract.DetailViewContract;
import com.pay.david.kakaopay.dao.ListSearchData;
import com.pay.david.kakaopay.dao.SearchData;
import com.pay.david.kakaopay.model.ApiManager;
import com.pay.david.kakaopay.model.ApiService;
import com.pay.david.kakaopay.viewmodel.DetailViewModel;

import static com.pay.david.kakaopay.util.Constants.CONTENT;
import static com.pay.david.kakaopay.util.Constants.TITLE;
import static com.pay.david.kakaopay.util.Constants.URL;
import static com.pay.david.kakaopay.util.Constants.VIDEO_ID;

public class DetailFragment extends Fragment implements DetailViewContract, DetailSubItemViewContract {
    public static final String LOG = DetailFragment.class.getSimpleName();

    private FragmentDetailBinding binding;
    private DetailViewModel detailViewModel;
    private SubItemListAdapter subItemListAdapter;

    public DetailFragment() {
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG, "onCreateView");

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        binding.setViewModel(new DetailViewModel(this, this, ApiManager.getInstance().create(ApiService.class)));
        detailViewModel = binding.getViewModel();
        if(detailViewModel != null) {
            Log.d(LOG, "not null");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(LOG, "onViewCreated");
        getActivity().findViewById(R.id.recycler_repos).setVisibility(View.GONE);
        String videoId = getArguments().getString(VIDEO_ID);
        String title = getArguments().getString(TITLE);
        String url = getArguments().getString(URL);
        String content = getArguments().getString(CONTENT);
        detailViewModel.loadItem(title, url, content);
        subItemListAdapter = new SubItemListAdapter(this);
        binding.subList.setAdapter(subItemListAdapter);
        binding.subList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDestroyView() {
        getActivity().findViewById(R.id.recycler_repos).setVisibility(View.VISIBLE);
        super.onDestroyView();
    }

    @Override
    public void reload(SearchData data) {
        if(data != null) {
            Bundle bundle = new Bundle(1);
            bundle.putString(TITLE, data.snippet.title);
            bundle.putString(URL, data.snippet.thumbnails.highs.url);
            bundle.putString(CONTENT, data.snippet.description);
            bundle.putString(VIDEO_ID, data.id.videoId);
            this.setArguments(bundle);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
            getActivity().findViewById(R.id.recycler_repos).setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSubItemList(ListSearchData response) {
        Log.d(LOG, "showSubItemList");
        subItemListAdapter.setItemsAndRefresh(response);
    }
}