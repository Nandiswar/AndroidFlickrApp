package com.example.flickr.ui.home;

import com.example.flickr.models.home.Photos;
import com.example.flickr.util.Resource;

import io.reactivex.Observable;

/**
 * The list of methods to be implemented by any class (Activity or Fragment or ...) that
 * would like to handle results layout use cases,
 * that is, to replace {@link com.example.flickr.ui.home.results.PhotosResultsFragment}.
 */
public interface ResultsHandler {

    /**
     * Trigger to reset views and any cleanup data.
     */
    void reset();

    /**
     * Trigger to fetch more photos for the use case of pagination.
     */
    void fetchMore();

    /**
     * Provides the stream of photos that can be used by consumers to display to users.
     *
     * @return {@link Observable} Stream of {@link Resource<Photos>}.
     */
    Observable<Resource<Photos>> subscribeForResults();

    /**
     * Provides the stream of search string entered by user.
     *
     * @return {@link Observable} Stream of search strings.
     */
    Observable<String> subscribeForSearchClicks();
}
