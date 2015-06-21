package com.emmaguy.hn.presenter;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.model.data.events.CommentEvents;
import com.emmaguy.hn.view.CommentsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;

import rx.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by emma on 21/03/15.
 */
public class CommentsPresenterTest {
    @Mock private RxBus mMockNetworkBus;
    @Mock private CommentsView mMockView;
    @Mock private NewsDataSource mMockDataSource;

    private CommentsPresenter mPresenter;

    private ArrayList<String> mIds = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mIds.add("1");

        mPresenter = new CommentsPresenter(mIds, mMockDataSource, mMockNetworkBus);
        when(mMockNetworkBus.toObservable()).thenReturn(Observable.just(new Object()));
    }

    @Test
    public void onStart_registersNetworkBus() {
        mPresenter.onStart(mMockView);

        verify(mMockNetworkBus, times(1)).toObservable();
        verifyNoMoreInteractions(mMockNetworkBus);
    }

    @Test
    public void onStartWithEmptyView_showsLoadingIndicator() {
        when(mMockView.isEmpty()).thenReturn(true);

        mPresenter.onStart(mMockView);

        verify(mMockView, times(1)).showLoadingIndicator();
    }

    @Test
    public void onStartWithEmptyView_retrievesComments() {
        when(mMockView.isEmpty()).thenReturn(true);

        mPresenter.onStart(mMockView);

        verify(mMockDataSource, times(1)).getComments(mIds, mMockNetworkBus);
        verifyNoMoreInteractions(mMockDataSource);
    }

    @Test
    public void onStartWithoutEmptyView_doesNotShowLoadingIndicator() {
        when(mMockView.isEmpty()).thenReturn(false);

        mPresenter.onStart(mMockView);

        verify(mMockView, times(0)).showLoadingIndicator();
    }

    @Test
    public void onStartWithoutEmptyView_doesNotRetrievesComments() {
        when(mMockView.isEmpty()).thenReturn(false);

        mPresenter.onStart(mMockView);

        verify(mMockDataSource, times(0)).getComments(mIds, mMockNetworkBus);
    }

    @Test
    public void onCommentsReceived_hidesLoadingIndicator() {
        mPresenter.onStart(mMockView);
        Object value = new CommentEvents.RequestSucceededEvent(Collections.singletonList(new Comment("blah")));
        when(mMockNetworkBus.toObservable()).thenReturn(Observable.just(value));

        verify(mMockView, times(1)).hideLoadingIndicator();
    }

    @Test
    public void onCommentsReceived_showsComments() {
        ArrayList<Comment> comments = new ArrayList<>();

        mPresenter.onStart(mMockView);
        Object value = new CommentEvents.RequestSucceededEvent(Collections.singletonList(new Comment("blah")));
        when(mMockNetworkBus.toObservable()).thenReturn(Observable.just(value));

        verify(mMockView, times(1)).showComments(comments);
    }

    @Test
    public void onCommentsReceivedWithNoComments_showsNoCommentsMessage() {
        ArrayList<Comment> comments = new ArrayList<>();

        mPresenter.onStart(mMockView);
        Object value = new CommentEvents.RequestSucceededEvent(comments);
        when(mMockNetworkBus.toObservable()).thenReturn(Observable.just(value));

        verify(mMockView, times(1)).showNoCommentsMessage();
    }

    @Test
    public void presenterWithEmptyViewAndWithEmptyIds_showsNoCommentsMessageAndDoesNotTryToRetrieveCommentsAndDoesNotShowLoadingIndicator() {
        when(mMockView.isEmpty()).thenReturn(true);
        ArrayList<String> ids = new ArrayList<>();

        CommentsPresenter p = new CommentsPresenter(ids, mMockDataSource, mMockNetworkBus);

        p.onStart(mMockView);

        verify(mMockDataSource, times(0)).getComments(ids, mMockNetworkBus);
        verify(mMockView, times(1)).showNoCommentsMessage();
    }
}
