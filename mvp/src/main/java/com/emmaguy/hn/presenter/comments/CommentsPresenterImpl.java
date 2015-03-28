package com.emmaguy.hn.presenter.comments;

import android.support.annotation.NonNull;

import com.emmaguy.hn.model.data.Events;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.view.CommentsView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by emma on 22/03/15.
 */
public class CommentsPresenterImpl implements CommentsPresenter {
    private final Bus mNetworkBus;
    private final CommentsView mView;
    private final ArrayList<String> mIds;
    private final NewsDataSource mDataSource;

    public CommentsPresenterImpl(CommentsView view, ArrayList<String> ids, NewsDataSource dataSource, Bus bus) {
        mIds = ids;
        mView = view;
        mNetworkBus = bus;
        mDataSource = dataSource;
    }

    @Override
    public void onStart() {
        mNetworkBus.register(this);

        mView.showLoadingIndicator();
        mDataSource.getComments(mIds);
    }

    @Override
    public void onStop() {
        mNetworkBus.unregister(this);
    }

    @Subscribe
    public void onCommentsReceived(@NonNull Events.CommentsSuccessEvent event) {
        mView.hideLoadingIndicator();
        mView.showComments(event.getData());
    }
}
