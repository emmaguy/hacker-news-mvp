package com.emmaguy.hn.newsitemdetail;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.emmaguy.hn.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class NewsItemDetailActivity extends ActionBarActivity {
    public static final String EXTRA_NEWS_ITEM_KEY_URL = "key_news_item_url";

    @InjectView(R.id.news_item_web_view_content) WebView mWebView;
    @InjectView(R.id.news_item_progress_bar_loading) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_item_detail);
        ButterKnife.inject(this);

        String url = getIntent().getStringExtra(EXTRA_NEWS_ITEM_KEY_URL);

        mWebView.loadUrl(url);
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
}
