package com.pay.david.kakaopay.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.pay.david.R;
import com.pay.david.databinding.ActivityMainListBinding;
import com.pay.david.kakaopay.contract.RepositoryListViewContract;
import com.pay.david.kakaopay.dao.ListSearchData;
import com.pay.david.kakaopay.dao.SearchData;
import com.pay.david.kakaopay.model.ApiManager;
import com.pay.david.kakaopay.model.ApiService;
import com.pay.david.kakaopay.viewmodel.RepositoryListViewModel;

import static com.pay.david.kakaopay.util.Constants.API_KEY;
import static com.pay.david.kakaopay.util.Constants.CONTENT;
import static com.pay.david.kakaopay.util.Constants.COUNT;
import static com.pay.david.kakaopay.util.Constants.SNIPPET;
import static com.pay.david.kakaopay.util.Constants.TITLE;
import static com.pay.david.kakaopay.util.Constants.URL;
import static com.pay.david.kakaopay.util.Constants.VIDEO_ID;

public class VideoListActivity extends AppCompatActivity implements RepositoryListViewContract {
    public static final String LOG = VideoListActivity.class.getSimpleName();

    private CoordinatorLayout coordinatorLayout;
    private VideoListAdapter videoListAdapter;
    private RepositoryListViewModel repositoryListViewModel;
    private InputMethodManager imm;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main_list);
        binding.setViewModel(new RepositoryListViewModel(this, ApiManager.getInstance().create(ApiService.class)));
        repositoryListViewModel = binding.getViewModel();
        setupViews();
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        RecyclerView recyclerView = findViewById(R.id.recycler_repos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoListAdapter = new VideoListAdapter(this, this);
        recyclerView.setAdapter(videoListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) && newState == RecyclerView.FOCUS_DOWN) {
                    Toast.makeText(VideoListActivity.this, "Last", Toast.LENGTH_SHORT).show();
                    repositoryListViewModel.getSearchList(SNIPPET, null, API_KEY, COUNT);
                }
            }
        });
        coordinatorLayout = findViewById(R.id.coordinator_layout);

    }

    public void setSearchView(Menu menu) {
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(detailFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(detailFragment).commit();
                }
                repositoryListViewModel.getSearchList(Uri.encode(query));
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                searchView.setIconified(false);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dashboard_content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        findViewById(R.id.recycler_repos).setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setSearchView(menu);
        return true;
    }

    @Override
    public void showDetailFragment(SearchData data) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("data", data);
        detailFragment = DetailFragment.newInstance();
        detailFragment.setArguments(bundle);
        replaceFragment(detailFragment);
    }

    @Override
    public void showRepositories(ListSearchData repositories) {
        videoListAdapter.setItemsAndRefresh(repositories);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction(message, null).show();
    }

}
