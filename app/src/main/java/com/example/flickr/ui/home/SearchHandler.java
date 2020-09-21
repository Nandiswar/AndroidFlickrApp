package com.example.flickr.ui.home;

import androidx.annotation.NonNull;

import io.reactivex.Observable;

/**
 * The list of methods to be implemented by any class (Activity or Fragment or ...) that
 * would like to handle search layout use cases,
 * that is, to replace {@link com.example.flickr.ui.home.search.SearchFragment}.
 */
public interface SearchHandler {
    /**
     * Handle the search key
     *
     * @param searchKey search key.
     */
    void handleSearchKey(@NonNull String searchKey);

    /**
     * Provide the stream oif search keys entered by user.
     *
     * @return {@link Observable}
     */
    Observable<String> searchKeyStream();
}
