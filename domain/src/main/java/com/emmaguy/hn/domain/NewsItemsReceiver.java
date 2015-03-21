package com.emmaguy.hn.domain;

/**
 * Created by emma on 21/03/15.
 */
public interface NewsItemsReceiver extends Executor {
    void getLatestNewsItems();
}
