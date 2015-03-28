package com.emmaguy.hn.presenter;

import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.Events;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.presenter.comments.CommentsPresenterImpl;
import com.emmaguy.hn.view.CommentsView;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by emma on 21/03/15.
 */
public class CommentsPresenterImplTest {
    @Mock
    private Bus mMockNetworkBus;

    @Mock
    private CommentsView mMockView;

    @Mock
    private NewsDataSource mMockDataSource;

    private CommentsPresenterImpl mPresenter;

    private ArrayList<String> mIds;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new CommentsPresenterImpl(mMockView, mIds, mMockDataSource, mMockNetworkBus);
    }

    @Test
    public void test_onStart_registersNetworkBus() {
        mPresenter.onStart();

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
    public void test_onStart_showsLoadingIndicator() {
        mPresenter.onStart();

        verify(mMockView, times(1)).showLoadingIndicator();
        verifyNoMoreInteractions(mMockView);
    }

    @Test
    public void test_onStart_retrievedComments() {
        mPresenter.onStart();

        verify(mMockDataSource, times(1)).getComments(mIds);
        verifyNoMoreInteractions(mMockDataSource);
    }

    @Test
    public void test_onCommentsReceived_hidesLoadingIndicator() {
        mPresenter.onCommentsReceived(new Events.CommentsSuccessEvent(new ArrayList<Comment>()));

        verify(mMockView, times(1)).hideLoadingIndicator();
    }

    @Test
    public void test_onCommentsReceived_showsComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        mPresenter.onCommentsReceived(new Events.CommentsSuccessEvent(comments));

        verify(mMockView, times(1)).showComments(comments);
    }
}
