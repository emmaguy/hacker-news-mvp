package com.emmaguy.hn.presenter;

import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.model.data.events.CommentEvents;
import com.emmaguy.hn.view.CommentsView;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by emma on 21/03/15.
 */
public class CommentsPresenterTest {
    @Mock
    private Bus mMockNetworkBus;

    @Mock
    private CommentsView mMockView;

    @Mock
    private NewsDataSource mMockDataSource;

    private CommentsPresenter mPresenter;

    private ArrayList<String> mIds = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mIds.add("1");

        mPresenter = new CommentsPresenter(mIds, mMockDataSource, mMockNetworkBus);
    }

    @Test
    public void test_onStart_registersNetworkBus() {
        mPresenter.onStart(mMockView);

        verify(mMockNetworkBus, times(1)).register(mPresenter);
        verifyNoMoreInteractions(mMockNetworkBus);
    }

    @Test
    public void test_onStop_unregistersNetworkBus() {
        mPresenter.onStop();

        verify(mMockNetworkBus, times(1)).unregister(mPresenter);
        verifyNoMoreInteractions(mMockNetworkBus);
    }

    @Test
    public void test_onStartWithEmptyView_showsLoadingIndicator() {
        when(mMockView.isEmpty()).thenReturn(true);

        mPresenter.onStart(mMockView);

        verify(mMockView, times(1)).showLoadingIndicator();
    }

    @Test
    public void test_onStartWithEmptyView_retrievesComments() {
        when(mMockView.isEmpty()).thenReturn(true);

        mPresenter.onStart(mMockView);

        verify(mMockDataSource, times(1)).getComments(mIds);
        verifyNoMoreInteractions(mMockDataSource);
    }

    @Test
    public void test_onStartWithoutEmptyView_doesNotShowLoadingIndicator() {
        when(mMockView.isEmpty()).thenReturn(false);

        mPresenter.onStart(mMockView);

        verify(mMockView, times(0)).showLoadingIndicator();
    }

    @Test
    public void test_onStartWithoutEmptyView_doesNotRetrievesComments() {
        when(mMockView.isEmpty()).thenReturn(false);

        mPresenter.onStart(mMockView);

        verify(mMockDataSource, times(0)).getComments(mIds);
    }

    @Test
    public void test_onCommentsReceived_hidesLoadingIndicator() {
        mPresenter.onStart(mMockView);
        mPresenter.onCommentsReceived(new CommentEvents.RequestSucceededEvent(new ArrayList<Comment>()));

        verify(mMockView, times(1)).hideLoadingIndicator();
    }

    @Test
    public void test_onCommentsReceived_showsComments() {
        ArrayList<Comment> comments = new ArrayList<>();

        mPresenter.onStart(mMockView);
        mPresenter.onCommentsReceived(new CommentEvents.RequestSucceededEvent(comments));

        verify(mMockView, times(1)).showComments(comments);
    }

    @Test
    public void test_presenterWithEmptyViewAndWithEmptyIds_showsNoCommentsMessageAndDoesNotTryToRetrieveCommentsAndDoesNotShowLoadingIndicator() {
        when(mMockView.isEmpty()).thenReturn(true);
        ArrayList<String> ids = new ArrayList<>();

        CommentsPresenter p = new CommentsPresenter(ids, mMockDataSource, mMockNetworkBus);

        p.onStart(mMockView);

        verify(mMockDataSource, times(0)).getComments(ids);
        verify(mMockView, times(0)).showLoadingIndicator();
        verify(mMockView, times(1)).showNoCommentsMessage();
    }
}
