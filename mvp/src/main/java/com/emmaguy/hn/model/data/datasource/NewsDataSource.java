package com.emmaguy.hn.model.data.datasource;

import com.emmaguy.hn.common.RxBus;

import java.util.List;

public interface NewsDataSource {
    void getLatestNewsItems(RxBus bus);
    void getComments(List<String> ids, RxBus bus);
}
