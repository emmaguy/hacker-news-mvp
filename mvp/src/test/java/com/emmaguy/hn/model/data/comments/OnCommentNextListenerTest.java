package com.emmaguy.hn.model.data.comments;

import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.Events;
import com.emmaguy.hn.model.data.comments.OnCommentNextListener;
import com.emmaguy.hn.model.data.newsitems.OnListNewsItemNextListener;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by emma on 21/03/15.
 */
public class OnCommentNextListenerTest {
    @Mock
    private Bus mMockNetworkBus;

    private OnCommentNextListener mNextListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mNextListener = new OnCommentNextListener(mMockNetworkBus);
    }

    @Test
    public void test_onNext_postsNewsItemsOnEventBus() {
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("blah"));

        mNextListener.call(comments);

        verify(mMockNetworkBus, times(1)).post(any(Events.CommentsSuccessEvent.class));
    }
}
