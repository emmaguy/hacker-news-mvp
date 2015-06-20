package com.emmaguy.hn.comments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.emmaguy.hn.R;
import com.emmaguy.hn.common.EventBusProvider;
import com.emmaguy.hn.common.PlainTextShareIntentBuilder;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.datasource.HackerNewsDataSource;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.presenter.CommentsPresenter;
import com.emmaguy.hn.view.CommentsView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentsActivity extends AppCompatActivity implements CommentsView {
    public static final String EXTRA_NEWS_ITEM_ID = "key_news_item_id";
    public static final String EXTRA_NEWS_ITEM_TITLE = "key_news_item_title";
    public static final String EXTRA_NEWS_ITEM_AUTHOR = "key_news_item_author";
    public static final String EXTRA_NEWS_ITEM_PERMALINK = "key_news_item_permalink";
    public static final String EXTRA_NEWS_ITEM_COMMENT_KEYS_ID = "key_news_item_comment_ids";

    @InjectView(R.id.comments_progress_bar_loading) ProgressBar mLoadingIndicator;
    @InjectView(R.id.comments_viewgroup_content) ViewGroup mRootViewGroup;
    @InjectView(R.id.comments_textview_message) TextView mMessage;
    @InjectView(R.id.comments_textview_title) TextView mTitle;
    @InjectView(R.id.comments_toolbar) Toolbar mToolbar;

    private final PlainTextShareIntentBuilder mIntentBuilder = new PlainTextShareIntentBuilder();

    private String mNewsItemAuthor;
    private String mNewsItemId;
    private String mPermalink;

    private CommentsPresenter mPresenter;
    private NewsDataSource mDataSource;

    private boolean mCommentsTreeViewAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comments);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);

        mTitle.setText(getIntent().getStringExtra(EXTRA_NEWS_ITEM_TITLE));

        mNewsItemId = getIntent().getStringExtra(EXTRA_NEWS_ITEM_ID);
        mNewsItemAuthor = getIntent().getStringExtra(EXTRA_NEWS_ITEM_AUTHOR);
        mPermalink = getIntent().getStringExtra(EXTRA_NEWS_ITEM_PERMALINK);
        ArrayList<String> ids = getIntent().getStringArrayListExtra(EXTRA_NEWS_ITEM_COMMENT_KEYS_ID);

        mDataSource = HackerNewsDataSource.getInstance();
        mPresenter = new CommentsPresenter(ids, mDataSource, EventBusProvider.getNetworkBusInstance());
        mLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(this, mPermalink, Toast.LENGTH_SHORT).show();
                startActivity(mIntentBuilder.build(mPermalink));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter.onStart(this);
    }

    @Override
    public void onStop() {
        mPresenter.onStop();

        super.onStop();
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void hideNoCommentsMessage() {
        mMessage.setVisibility(View.GONE);
    }

    @Override
    public void showNoCommentsMessage() {
        mMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showComments(List<Comment> comments) {
        TreeNode root = TreeNode.root();

        ArrayList<Comment> toRemoveComments = new ArrayList<>();
        HashMap<String, TreeNode> addedNodes = new HashMap<>();

        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);

            // add all the top level comments to the root node
            if (mNewsItemId.equals(c.getParent())) {
                addNode(root, toRemoveComments, addedNodes, c);
            }
        }

        comments.removeAll(toRemoveComments);

        boolean hasRemovedComments;
        do {
            toRemoveComments.clear();

            for (Comment c : comments) {
                if (addedNodes.containsKey(c.getParent())) {
                    // add a reply to its parent comment, then remove it from the list of comments
                    addNode(addedNodes.get(c.getParent()), toRemoveComments, addedNodes, c);
                }
            }
            comments.removeAll(toRemoveComments);
            hasRemovedComments = toRemoveComments.size() > 0;
        } while (hasRemovedComments);

        addTreeView(root);
    }

    @Override
    public boolean isEmpty() {
        return !mCommentsTreeViewAdded;
    }

    private void addNode(TreeNode root, ArrayList<Comment> toRemoveComments, HashMap<String, TreeNode> addedNodes, Comment c) {
        TreeNode node = new TreeNode(c);
        root.addChild(node);

        addedNodes.put(c.getId(), node);
        toRemoveComments.add(c);
    }

    private void addTreeView(TreeNode root) {
        mCommentsTreeViewAdded = true;

        int horizontal = getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
        int vertical = getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin);

        AndroidTreeView treeView = new AndroidTreeView(this, root);
        treeView.setDefaultAnimation(true);
        treeView.setDefaultViewHolder(CommentTreeViewHolder.class);
        treeView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);

        ScrollView view = (ScrollView) treeView.getView();
        view.setClipToPadding(false);
        view.setVerticalScrollBarEnabled(false);
        view.setPadding(0, vertical, horizontal, vertical);
        mRootViewGroup.addView(view);

        treeView.expandAll();
    }
}
