package com.emmaguy.hn.newsitemslist;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emmaguy.hn.R;
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
public class NewsItemsAdapter extends RecyclerView.Adapter<NewsItemsAdapter.StoryHolder> {
    private final List<NewsItem> mNewsItems = new ArrayList<>();
    private Context mContext;

    public NewsItemsAdapter(Context context) {
        mContext = context;
    }

    public void setNewsItems(List<NewsItem> items) {
        mNewsItems.clear();
        mNewsItems.addAll(items);

        notifyDataSetChanged();
    }

    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_story, parent, false);

        return new StoryHolder(v);
    }

    @Override
    public void onBindViewHolder(StoryHolder holder, int position) {
        NewsItem newsItem = mNewsItems.get(position);

        holder.mTitle.setText(newsItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    public class StoryHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.list_item_story_textview_title) TextView mTitle;

        public StoryHolder(View view) {
            super(view);

            ButterKnife.inject(this, view);

            setElevation();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void setElevation() {
            int elevation = mContext.getResources().getDimensionPixelSize(R.dimen.list_item_story_elevation);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTitle.setElevation(elevation);
            } else {
                ViewCompat.setElevation(mTitle, elevation);
            }
        }

        @OnClick(R.id.list_item_story_viewgroup_root)
        public void clickOnItem() {
            NewsItem newsItem = mNewsItems.get(getPosition());

            Intent intent = new Intent(mContext, NewsItemDetailActivity.class);
            intent.putExtra(NewsItemDetailActivity.EXTRA_NEWS_ITEM_KEY_URL, newsItem.getUrl());
            mContext.startActivity(intent);
        }
    }
}
