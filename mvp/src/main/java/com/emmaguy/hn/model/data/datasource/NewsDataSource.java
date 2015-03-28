package com.emmaguy.hn.model.data.datasource;

import java.util.List;

public interface NewsDataSource {
    void getLatestNewsItems();
    void getComments(List<String> ids);
}
