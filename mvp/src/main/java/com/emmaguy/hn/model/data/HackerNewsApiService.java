package com.emmaguy.hn.model.data;

import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.NewsItem;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by emma on 15/02/15.
 */
public interface HackerNewsApiService {
    @GET("/v0/topstories.json")
    Observable<List<String>> topStories();

    @GET("/v0/item/{itemId}.json")
    Observable<NewsItem> item(@Path("itemId") String itemId);

    @GET("/v0/item/{itemId}.json")
    Observable<Comment> comment(@Path("itemId") String itemId);
}
