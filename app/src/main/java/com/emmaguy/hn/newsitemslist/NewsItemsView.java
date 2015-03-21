package com.emmaguy.hn.newsitemslist;

import com.emmaguy.hn.MVPView;
import com.emmaguy.hn.model.NewsItem;

import java.util.List;

/**
 * Created by emma on 21/03/15.
 */
public interface NewsItemsView extends MVPView {
    void showNewsItems(List<NewsItem> newsItemList);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    boolean listIsEmpty();
}
