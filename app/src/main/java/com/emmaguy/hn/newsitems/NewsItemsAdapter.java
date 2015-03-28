package com.emmaguy.hn.newsitems;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.emmaguy.hn.R;
import com.emmaguy.hn.comments.CommentsActivity;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.newsitemdetail.NewsItemDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemsAdapter extends RecyclerView.Adapter<NewsItemsAdapter.NewsItemHolder> {
    private final List<NewsItem> mNewsItems = new ArrayList<>();
    private Context mContext;

    public NewsItemsAdapter(Context context) {
        mContext = context;
    }

    public void setNewsItems(@NonNull List<NewsItem> items) {
        mNewsItems.clear();
        mNewsItems.addAll(items);

        notifyDataSetChanged();
    }

    @Override
    public NewsItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_news_item, parent, false);

        return new NewsItemHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsItemHolder holder, int position) {
        NewsItem newsItem = mNewsItems.get(position);

        holder.mTitle.setText(newsItem.getTitle());
        formatPluralString(holder.mScore, R.plurals.points, newsItem.getScore());
    }

    private void formatPluralString(TextView textView, @PluralsRes int res, int number) {
        String text = mContext.getResources().getQuantityString(res, number, number);
        textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    public class NewsItemHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.row_news_item_textview_score) TextView mScore;
        @InjectView(R.id.row_news_item_textview_title) TextView mTitle;

        public NewsItemHolder(View view) {
            super(view);

            ButterKnife.inject(this, view);
        }

        @OnClick(R.id.row_news_item_textview_comments)
        public void viewComments() {
            NewsItem newsItem = mNewsItems.get(getPosition());

            Intent intent = new Intent(mContext, CommentsActivity.class);
            intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_ID, newsItem.getId());
            intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_TITLE, newsItem.getTitle());
            intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_PERMALINK, newsItem.getPermalink());
            intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_COMMENT_KEYS_ID, newsItem.getRootCommentIds());
            mContext.startActivity(intent);
        }

        @OnClick(R.id.row_news_item_viewgroup)
        public void viewLink() {
            NewsItem newsItem = mNewsItems.get(getPosition());

            Intent intent = new Intent(mContext, NewsItemDetailActivity.class);
            intent.putExtra(NewsItemDetailActivity.EXTRA_NEWS_ITEM_KEY_URL, newsItem.getUrl());
            mContext.startActivity(intent);
        }
    }
}
