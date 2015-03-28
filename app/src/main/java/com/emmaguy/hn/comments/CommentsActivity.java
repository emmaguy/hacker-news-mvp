package com.emmaguy.hn.comments;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.emmaguy.hn.R;
import com.emmaguy.hn.common.EventBusProvider;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.data.datasource.HackerNewsDataSource;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.presenter.comments.CommentsPresenter;
import com.emmaguy.hn.presenter.comments.CommentsPresenterImpl;
import com.emmaguy.hn.view.CommentsView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentsActivity extends ActionBarActivity implements CommentsView {
    public static final String EXTRA_NEWS_ITEM_ID = "key_news_item_id";
    public static final String EXTRA_NEWS_ITEM_TITLE = "key_news_item_title";
    public static final String EXTRA_NEWS_ITEM_COMMENT_KEYS_ID = "key_news_item_comment_ids";

    @InjectView(R.id.comments_toolbar) Toolbar mToolbar;
    @InjectView(R.id.comments_progress_bar_loading) ProgressBar mLoadingIndicator;
    @InjectView(R.id.activity_news_item_comments_root) ViewGroup mRootViewGroup;

    private String mNewsItemId;
    private CommentsPresenter mPresenter;
    private NewsDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comments);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);

        String title = getIntent().getStringExtra(EXTRA_NEWS_ITEM_TITLE);
        setTitle(title);

        mNewsItemId = getIntent().getStringExtra(EXTRA_NEWS_ITEM_ID);
        ArrayList<String> ids = getIntent().getStringArrayListExtra(EXTRA_NEWS_ITEM_COMMENT_KEYS_ID);

        mDataSource = HackerNewsDataSource.getInstance();
        mPresenter = new CommentsPresenterImpl(this,
                ids,
                mDataSource,
                EventBusProvider.getNetworkBusInstance());
    }

    @Override
    protected void onStart() {
        super.onStart();

        mPresenter.onStart();
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

        while (!comments.isEmpty()) {
            toRemoveComments.clear();

            for (Comment c : comments) {
                if (addedNodes.containsKey(c.getParent())) {
                    // add a reply to its parent comment, then remove it from the list of comments
                    addNode(addedNodes.get(c.getParent()), toRemoveComments, addedNodes, c);
                }
            }
            comments.removeAll(toRemoveComments);
        }

        addTreeView(root);
    }

    private void addNode(TreeNode root, ArrayList<Comment> toRemoveComments, HashMap<String, TreeNode> addedNodes, Comment c) {
        TreeNode node = new TreeNode(c);
        root.addChild(node);

        addedNodes.put(c.getId(), node);
        toRemoveComments.add(c);
    }

    private void addTreeView(TreeNode root) {
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
