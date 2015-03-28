package com.emmaguy.hn.newsitems;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.emmaguy.hn.R;
import com.emmaguy.hn.common.DividerItemDecoration;
import com.emmaguy.hn.common.EventBusProvider;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.model.data.datasource.HackerNewsDataSource;
import com.emmaguy.hn.presenter.newsitems.NewsItemsPresenter;
import com.emmaguy.hn.presenter.newsitems.NewsItemsPresenterImpl;
import com.emmaguy.hn.view.NewsItemsView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class NewsItemsActivity extends ActionBarActivity implements NewsItemsView {
    @InjectView(R.id.news_items_toolbar) Toolbar mToolbar;
    @InjectView(R.id.news_items_textview_error) TextView mErrorTextView;
    @InjectView(R.id.news_items_recyclerview_list) RecyclerView mNewsItemsList;
    @InjectView(R.id.news_items_progress_bar_loading) ProgressBar mProgressBar;

    private NewsItemsAdapter mAdapter;
    private NewsItemsPresenter mPresenter;
    private NewsDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_items);

        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        initialiseList();
        mAdapter = new NewsItemsAdapter(this);
        mNewsItemsList.setAdapter(mAdapter);

        mDataSource = HackerNewsDataSource.getInstance();
        mPresenter = new NewsItemsPresenterImpl(this,
                mDataSource,
                EventBusProvider.getNetworkBusInstance());
    }

    private void initialiseList() {
        mNewsItemsList.setItemAnimator(new DefaultItemAnimator());
        mNewsItemsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNewsItemsList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.news_items_list_divider), true, true));
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();

        super.onStop();
    }

    @Override
    public void showNewsItems(@NonNull List<NewsItem> items) {
        mAdapter.setNewsItems(items);
        mNewsItemsList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorTextView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean isEmptyList() {
        return mAdapter.getItemCount() <= 0;
    }
}
