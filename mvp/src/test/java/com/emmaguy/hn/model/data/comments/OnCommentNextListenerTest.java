package com.emmaguy.hn.model.data.comments;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.events.CommentEvents;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by emma on 21/03/15.
 */
public class OnCommentNextListenerTest {
    @Mock private RxBus mMockNetworkBus;

    private OnCommentNextListener mNextListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mNextListener = new OnCommentNextListener(mMockNetworkBus);
    }

    @Test
    public void onNext_postsNewsItemsOnEventBus() {
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("blah"));

        mNextListener.call(comments);

        verify(mMockNetworkBus, times(1)).send(any(CommentEvents.RequestSucceededEvent.class));
    }
}
