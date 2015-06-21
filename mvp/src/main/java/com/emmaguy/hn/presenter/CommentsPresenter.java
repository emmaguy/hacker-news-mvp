package com.emmaguy.hn.presenter;

import android.support.annotation.NonNull;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.model.data.events.CommentEvents;
import com.emmaguy.hn.view.CommentsView;

import java.util.ArrayList;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by emma on 22/03/15.
 */
public class CommentsPresenter {
    private final RxBus mNetworkBus;
    private final ArrayList<String> mIds;
    private final NewsDataSource mDataSource;
    private final CompositeSubscription mSubscription = new CompositeSubscription();

    private CommentsView mView;

    public CommentsPresenter(@NonNull ArrayList<String> ids, NewsDataSource dataSource, RxBus bus) {
        mIds = ids;
        mNetworkBus = bus;
        mDataSource = dataSource;
    }

    public void onStart(CommentsView view) {
        setView(view);

        mSubscription.add(mNetworkBus.toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof CommentEvents.RequestSucceededEvent) {
                            CommentEvents.RequestSucceededEvent event = (CommentEvents.RequestSucceededEvent) o;
                            mView.showComments(event.getComments());
                            mView.hideLoadingIndicator();
                        }
                    }
                }));

        if (mView.isEmpty() && !mIds.isEmpty()) {
            mView.showLoadingIndicator();
            mDataSource.getComments(mIds, mNetworkBus);
        } else {
            mView.showNoCommentsMessage();
        }
    }

    private void setView(CommentsView view) {
        mView = view;
    }

    public void onStop() {
        mSubscription.unsubscribe();

        setView(null);
    }
}
