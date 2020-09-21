package com.example.flickr.ui.home.search;

import androidx.annotation.NonNull;

import com.example.flickr.ui.home.SearchHandler;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class PhotoSearchHandler implements SearchHandler {

    private BehaviorSubject<String> searchStream = BehaviorSubject.create();

    @Override
    public void handleSearchKey(@NonNull String searchKey) {
        searchStream.onNext(searchKey);
    }

    @Override
    public Observable<String> searchKeyStream() {
        return searchStream;
    }

}
