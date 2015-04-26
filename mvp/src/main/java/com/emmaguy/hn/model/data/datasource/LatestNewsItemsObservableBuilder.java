package com.emmaguy.hn.model.data.datasource;

import com.emmaguy.hn.common.Utils;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.HackerNewsApiService;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Creates an observable which retrieves the ids of the latest top stories and then fetches a list containing
 * {@link maxNumberStories} {@link NewsItem}s. Uses concatMap to preserve ordering, so we don't have to sort,
 * we can just show the news items in the order we're given
 *
 * @return Observable list of the top {@link NewsItem}s
 * <p/>
 * Created by emma on 29/03/15.
 */
public class LatestNewsItemsObservableBuilder {
    private static final String HACKER_NEWS_NEWS_ITEM_URL = "https://news.ycombinator.com/item?id=";

    private final int mMaxStories;
    private final HackerNewsApiService mApiService;

    public LatestNewsItemsObservableBuilder(int maxNumberStories, HackerNewsApiService hackerNewsApiService) {
        mMaxStories = maxNumberStories;
        mApiService = hackerNewsApiService;
    }

    public Observable<List<NewsItem>> build() {
        return mApiService.topStories()
                .lift(Utils.<String>flattenList())
                .limit(mMaxStories)
                .concatMap(new Func1<String, Observable<NewsItem>>() {
                    @Override
                    public Observable<NewsItem> call(String id) {
                        return mApiService.item(id);
                    }
                })
                .doOnEach(new Subscriber<NewsItem>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsItem newsItem) {
                        newsItem.setPermalink(HACKER_NEWS_NEWS_ITEM_URL);
                    }
                })
                .toList();
    }
}
