package com.emmaguy.hn.newsitems;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.emmaguy.hn.common.RelativeTimeFormatter;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.datasource.HackerNewsDataSource;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.presenter.NewsItemsPresenter;
import com.emmaguy.hn.view.NewsItemsView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class NewsItemsActivity extends AppCompatActivity implements NewsItemsView {
    @InjectView(R.id.news_items_toolbar) Toolbar mToolbar;
    @InjectView(R.id.news_items_textview_title) TextView mTitle;
    @InjectView(R.id.news_items_textview_error) TextView mErrorTextView;
    @InjectView(R.id.news_items_recyclerview_list) RecyclerView mNewsItemsList;
    @InjectView(R.id.news_items_progress_bar_loading) ProgressBar mLoadingIndicator;

    private final RelativeTimeFormatter mRelativeTimeFormatter = new RelativeTimeFormatter();

    private NewsItemsPresenter mPresenter;
    private NewsItemsAdapter mAdapter;
    private NewsDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_items);

        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        mTitle.setText(mToolbar.getTitle());

        initialiseList();
        mAdapter = new NewsItemsAdapter(this, mRelativeTimeFormatter);
        mNewsItemsList.setAdapter(mAdapter);

        mDataSource = HackerNewsDataSource.getInstance();
        mPresenter = new NewsItemsPresenter(mDataSource, EventBusProvider.getNetworkBusInstance());
        mLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main), PorterDuff.Mode.SRC_IN);
    }

    private void initialiseList() {
        mNewsItemsList.setItemAnimator(new DefaultItemAnimator());
        mNewsItemsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNewsItemsList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.news_items_list_divider), true, true));
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter.onStart(this);
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
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public boolean isEmptyList() {
        return mAdapter.getItemCount() <= 0;
    }
}
