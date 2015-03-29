package com.emmaguy.hn.newsitemdetail;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.emmaguy.hn.R;
import com.emmaguy.hn.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class NewsItemDetailActivity extends ActionBarActivity {
    public static final String EXTRA_NEWS_ITEM_KEY_URL = "key_news_item_url";

    @InjectView(R.id.news_item_detail_toolbar) Toolbar mToolbar;
    @InjectView(R.id.news_item_detail_web_view_content) WebView mWebView;
    @InjectView(R.id.news_item_detail_progress_bar_loading) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_item_detail);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);

        String url = getIntent().getStringExtra(EXTRA_NEWS_ITEM_KEY_URL);

        mWebView.loadUrl(url);

        // double tap to fit to screen
        mWebView.getSettings().setUseWideViewPort(true);

        // enable zooming
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                mProgressBar.setProgress(progress);
            }
        });
        // force loading in this WebView, not a new browser window
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                String url = mWebView.getUrl();
                Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
                startActivity(Utils.getShareIntent(url));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
