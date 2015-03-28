package com.emmaguy.hn.view;

import com.emmaguy.hn.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emma on 21/03/15.
 */
public interface NewsItemsView {
    void showNewsItems(List<NewsItem> newsItemList);

    void showError();
    void hideError();

    void showLoadingIndicator();
    void hideLoadingIndicator();

    boolean isEmptyList();
}
