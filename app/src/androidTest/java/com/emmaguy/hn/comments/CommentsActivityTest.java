package com.emmaguy.hn.comments;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.emmaguy.hn.R;
import com.emmaguy.hn.newsitems.NewsItemsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CommentsActivityTest {

    @Rule
    public ActivityTestRule<CommentsActivity> mActivityRule = new ActivityTestRule<>(CommentsActivity.class, true, false);

    @Test
    public void intentWithNoComments_showsNoCommentsText() {
        ArrayList<String> ids = new ArrayList<>();

        Intent intent = new Intent();
        intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_COMMENT_KEYS_ID, ids);

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.comments_textview_message)).check(matches(isCompletelyDisplayed()));
    }
}
