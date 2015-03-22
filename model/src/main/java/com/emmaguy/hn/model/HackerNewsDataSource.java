package com.emmaguy.hn.model;

import com.emmaguy.hn.common.EventBusProvider;

import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by emma on 21/03/15.
 */
public class HackerNewsDataSource {
    private static final int MAX_NUMBER_STORIES = 35;
    private static final String ENDPOINT_URL_HACKER_NEWS_API = "https://hacker-news.firebaseio.com";
    private static HackerNewsDataSource sDataSourceInstance = null;
    private final HackerNewsService mHackerNewsService;

    private HackerNewsDataSource() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT_URL_HACKER_NEWS_API)
                .build();

        mHackerNewsService = restAdapter.create(HackerNewsService.class);
    }

    public static HackerNewsDataSource getInstance() {
        if (sDataSourceInstance == null) {
            sDataSourceInstance = new HackerNewsDataSource();
        }

        return sDataSourceInstance;
    }

    private static <T> Observable.Operator<T, List<T>> flattenList() {
        return new Observable.Operator<T, List<T>>() {
            @Override
            public Subscriber<? super List<T>> call(final Subscriber<? super T> subscriber) {
                return new Subscriber<List<T>>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(List<T> list) {
                        for (T c : list) {
                            subscriber.onNext(c);
                        }
                    }
                };
            }
        };
    }

    public void getLatestNewsItems() {
        createLatestStoryListObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnNextListener(EventBusProvider.getNetworkBusInstance()),
                        new OnErrorListener(EventBusProvider.getNetworkBusInstance()));
    }

    private Observable<List<NewsItem>> createLatestStoryListObservable() {
        return mHackerNewsService.topStories()
                .lift(this.<String>flattenList())
                .limit(MAX_NUMBER_STORIES)
                .flatMap(new Func1<String, Observable<NewsItem>>() {
                    @Override
                    public Observable<NewsItem> call(String storyId) {
                        return mHackerNewsService.item(storyId);
                    }
                }).toList();
    }
}
