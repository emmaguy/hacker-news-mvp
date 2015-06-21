package com.emmaguy.hn.presenter;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.model.data.events.CommentEvents;
import com.emmaguy.hn.model.data.events.NewsItemEvents;
import com.emmaguy.hn.view.NewsItemsView;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemsPresenter {
    private final RxBus mNetworkBus;
    private final NewsDataSource mDataSource;
    private final CompositeSubscription mSubscription = new CompositeSubscription();

    private NewsItemsView mNewsItemsView;

    public NewsItemsPresenter(NewsDataSource dataSource, RxBus networkBus) {
        mNetworkBus = networkBus;
        mDataSource = dataSource;
    }

    public void onStart(NewsItemsView newsItemsView) {
        setView(newsItemsView);

        mSubscription.add(mNetworkBus.toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof NewsItemEvents.RequestSucceededEvent) {
                            NewsItemEvents.RequestSucceededEvent event = (NewsItemEvents.RequestSucceededEvent) o;

                            mNewsItemsView.hideLoadingIndicator();
                            mNewsItemsView.showNewsItems(event.getNewsItems());
                        } else if (o instanceof NewsItemEvents.RequestFailedEvent) {
                            mNewsItemsView.hideLoadingIndicator();
                            mNewsItemsView.showError();
                        }
                    }
                }));

        if (mNewsItemsView.isEmptyList()) {
            mNewsItemsView.hideError();
            mNewsItemsView.showLoadingIndicator();

            mDataSource.getLatestNewsItems(mNetworkBus);
        }
    }

    private void setView(NewsItemsView newsItemsView) {
        mNewsItemsView = newsItemsView;
    }

    public void onStop() {
        mSubscription.unsubscribe();

        setView(null);
    }
}
