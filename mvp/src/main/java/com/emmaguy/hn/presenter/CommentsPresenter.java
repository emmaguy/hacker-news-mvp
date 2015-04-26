package com.emmaguy.hn.presenter;

import android.support.annotation.NonNull;

import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.model.data.events.CommentEvents;
import com.emmaguy.hn.view.CommentsView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by emma on 22/03/15.
 */
public class CommentsPresenter {
    private final Bus mNetworkBus;
    private final ArrayList<String> mIds;
    private final NewsDataSource mDataSource;

    private CommentsView mView;

    public CommentsPresenter(ArrayList<String> ids, NewsDataSource dataSource, Bus bus) {
        mIds = ids;
        mNetworkBus = bus;
        mDataSource = dataSource;
    }

    public void onStart(CommentsView view) {
        setView(view);

        mNetworkBus.register(this);

        if (mView.isEmpty()) {
            mView.showLoadingIndicator();
            mDataSource.getComments(mIds);
        }
    }

    private void setView(CommentsView view) {
        mView = view;
    }

    public void onStop() {
        mNetworkBus.unregister(this);

        setView(null);
    }

    @Subscribe
    public void onCommentsReceived(@NonNull CommentEvents.RequestSucceededEvent event) {
        mView.hideLoadingIndicator();
        mView.showComments(event.getComments());
    }
}
