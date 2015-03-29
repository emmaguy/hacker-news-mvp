package com.emmaguy.hn.model.data.datasource;

import com.emmaguy.hn.common.EventBusProvider;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.HackerNewsApiService;
import com.emmaguy.hn.model.data.comments.OnCommentErrorListener;
import com.emmaguy.hn.model.data.comments.OnCommentNextListener;
import com.emmaguy.hn.model.data.newsitems.OnListNewsItemErrorListener;
import com.emmaguy.hn.model.data.newsitems.OnListNewsItemNextListener;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A {@link NewsDataSource} which retrieves the latest, top news items and comments from the Hacker News API
 * <p/>
 * Created by emma on 21/03/15.
 */
public class HackerNewsDataSource implements NewsDataSource {
    private static final int MAX_NUMBER_STORIES = 25;
    private static final String ENDPOINT_URL_HACKER_NEWS_API = "https://hacker-news.firebaseio.com";
    private static final String HACKER_NEWS_NEWS_ITEM_URL = "https://news.ycombinator.com/item?id=";

    private static NewsDataSource sDataSourceInstance = null;
    private final HackerNewsApiService mHackerNewsApiService;

    private HackerNewsDataSource() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT_URL_HACKER_NEWS_API)
                .build();

        mHackerNewsApiService = restAdapter.create(HackerNewsApiService.class);
    }

    public static NewsDataSource getInstance() {
        if (sDataSourceInstance == null) {
            sDataSourceInstance = new HackerNewsDataSource();
        }

        return sDataSourceInstance;
    }

    @Override
    public void getLatestNewsItems() {
        createLatestNewsItemsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnListNewsItemNextListener(EventBusProvider.getNetworkBusInstance()),
                        new OnListNewsItemErrorListener(EventBusProvider.getNetworkBusInstance()));
    }

    /**
     * Creates an observable which retrieves the ids of the latest top stories and then fetches a list containing
     * {@link MAX_NUMBER_STORIES} {@link NewsItem}s. Uses concatMap to preserve ordering, so we don't have to sort,
     * we can just show the news items in the order we're given
     *
     * @return Observable list of the top {@link NewsItem}s
     */
    private Observable<List<NewsItem>> createLatestNewsItemsObservable() {
        return mHackerNewsApiService.topStories()
                .lift(this.<String>flattenList())
                .limit(MAX_NUMBER_STORIES)
                .concatMap(new Func1<String, Observable<NewsItem>>() {
                    @Override
                    public Observable<NewsItem> call(String id) {
                        return mHackerNewsApiService.item(id);
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

    @Override
    public void getComments(List<String> ids) {
        createCommentsObservable(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnCommentNextListener(EventBusProvider.getNetworkBusInstance()),
                        new OnCommentErrorListener(EventBusProvider.getNetworkBusInstance()));
    }

    /**
     * Creates an observable which will fetch user comments made on a particular news item
     * <p/>
     * This method is recursive and will also fetch all replies down the tree
     * Any empty comments/replies will be removed
     *
     * @param ids Comment ids to fetch
     * @return Observable of a flattened list, combining all comments and replies in the tree
     */
    private Observable<List<Comment>> createCommentsObservable(List<String> ids) {
        return Observable.from(ids)
                .flatMap(new Func1<String, Observable<Comment>>() {
                    @Override
                    public Observable<Comment> call(String id) {
                        return mHackerNewsApiService.comment(id);
                    }
                })
                .flatMap(new Func1<Comment, Observable<List<Comment>>>() {
                    @Override
                    public Observable<List<Comment>> call(Comment comment) {
                        List<Comment> value = new ArrayList<>();
                        value.add(comment);

                        Observable<List<Comment>> o = Observable.just(value);
                        if (comment.getChildCommentIds().isEmpty()) {
                            return o;
                        }

                        return createCommentsObservable(comment.getChildCommentIds()).concatWith(o);
                    }
                })
                .lift(this.<Comment>flattenList())
                .filter(new Func1<Comment, Boolean>() {
                    @Override
                    public Boolean call(Comment comment) {
                        return !comment.getText().equals("");
                    }
                })
                .toList();
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
}
