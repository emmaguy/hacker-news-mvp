package com.emmaguy.hn.newsitems;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emmaguy.hn.R;
import com.emmaguy.hn.comments.CommentsActivity;
import com.emmaguy.hn.common.RelativeTimeFormatter;
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
    private final Context mContext;
    private final RelativeTimeFormatter mRelativeTimeFormatter;

    public NewsItemsAdapter(Context context, RelativeTimeFormatter relativeTimeFormatter) {
        mContext = context;
        mRelativeTimeFormatter = relativeTimeFormatter;
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

        if (TextUtils.isEmpty(newsItem.getUrl())) {
            holder.mUrl.setVisibility(View.GONE);
        } else {
            holder.mUrl.setVisibility(View.VISIBLE);
            holder.mUrl.setText(newsItem.getUrl());
        }

        holder.mTitle.setText(newsItem.getTitle());
        String text = mContext.getResources().getQuantityString(R.plurals.news_item_description,
                newsItem.getScore(),
                newsItem.getScore(),
                newsItem.getAuthor(),
                mRelativeTimeFormatter.format(newsItem.getTime()));
        holder.mDescription.setText(text);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    public class NewsItemHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.row_news_item_textview_url) TextView mUrl;
        @InjectView(R.id.row_news_item_textview_title) TextView mTitle;
        @InjectView(R.id.row_news_item_textview_description) TextView mDescription;

        public NewsItemHolder(View view) {
            super(view);

            ButterKnife.inject(this, view);
        }

        @OnClick(R.id.row_news_item_textview_comments)
        public void viewComments() {
            NewsItem newsItem = mNewsItems.get(getPosition());

            Intent intent = new Intent(mContext, CommentsActivity.class);
            intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_ID, newsItem.getId());
            intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_AUTHOR, newsItem.getAuthor());
            intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_TITLE, newsItem.getTitle());
            intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_PERMALINK, newsItem.getPermalink());
            intent.putExtra(CommentsActivity.EXTRA_NEWS_ITEM_COMMENT_KEYS_ID, newsItem.getRootCommentIds());
            mContext.startActivity(intent);
        }

        @OnClick(R.id.row_news_item_viewgroup)
        public void viewLink() {
            NewsItem newsItem = mNewsItems.get(getPosition());

            if (TextUtils.isEmpty(newsItem.getUrl())) {
                viewComments();
            } else {
                Intent intent = new Intent(mContext, NewsItemDetailActivity.class);
                intent.putExtra(NewsItemDetailActivity.EXTRA_NEWS_ITEM_KEY_URL, newsItem.getUrl());
                intent.putExtra(NewsItemDetailActivity.EXTRA_NEWS_ITEM_KEY_TITLE, newsItem.getTitle());
                mContext.startActivity(intent);
            }
        }
    }
}
