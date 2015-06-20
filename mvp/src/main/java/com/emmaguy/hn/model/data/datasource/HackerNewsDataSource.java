package com.emmaguy.hn.model.data.datasource;

import com.emmaguy.hn.common.EventBusProvider;
import com.emmaguy.hn.common.Utils;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.HackerNewsApiService;
import com.emmaguy.hn.model.data.comments.OnCommentErrorListener;
import com.emmaguy.hn.model.data.comments.OnCommentNextListener;
import com.emmaguy.hn.model.data.newsitems.OnListNewsItemErrorListener;
import com.emmaguy.hn.model.data.newsitems.OnListNewsItemNextListener;

import java.util.Collections;
import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
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
        new LatestNewsItemsObservableBuilder(MAX_NUMBER_STORIES, mHackerNewsApiService).build()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnListNewsItemNextListener(EventBusProvider.getNetworkBusInstance()),
                        new OnListNewsItemErrorListener(EventBusProvider.getNetworkBusInstance()));
    }

    @Override
    public void getComments(List<String> ids) {
        fetchComments(ids)
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
    private Observable<List<Comment>> fetchComments(List<String> ids) {
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
                        Observable<List<Comment>> o = Observable.just(Collections.singletonList(comment));
                        if (comment.getChildCommentIds().isEmpty()) {
                            return o;
                        }

                        return fetchComments(comment.getChildCommentIds()).concatWith(o);
                    }
                })
                .lift(Utils.<Comment>flattenList())
                .filter(new Func1<Comment, Boolean>() {
                    @Override
                    public Boolean call(Comment comment) {
                        return !comment.getText().equals("");
                    }
                })
                .toList();
    }
}
