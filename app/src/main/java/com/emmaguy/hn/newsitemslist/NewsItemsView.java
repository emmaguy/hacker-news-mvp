package com.emmaguy.hn.newsitemslist;

import android.support.annotation.NonNull;

import com.emmaguy.hn.MVPView;
import com.emmaguy.hn.model.NewsItem;

import java.util.List;

/**
 * Created by emma on 21/03/15.
 */
public interface NewsItemsView extends MVPView {
    void showNewsItems(@NonNull List<NewsItem> newsItemList);

    void showError();
    void hideError();

    void showLoadingIndicator();
    void hideLoadingIndicator();

    boolean isEmptyList();
}
