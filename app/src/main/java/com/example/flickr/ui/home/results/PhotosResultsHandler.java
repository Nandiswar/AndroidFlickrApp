package com.example.flickr.ui.home.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import com.example.flickr.di.network.api.GetPhotosApi;
import com.example.flickr.models.home.GetPhotosResponse;
import com.example.flickr.models.home.Photos;
import com.example.flickr.ui.home.ResultsHandler;
import com.example.flickr.ui.home.SearchHandler;
import com.example.flickr.util.Resource;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

import static com.example.flickr.AppConstants.NUM_IMAGES;

public class PhotosResultsHandler implements ResultsHandler {

    private final GetPhotosApi getPhotosApi;
    private final SearchHandler searchHandler;

    private BehaviorSubject<Integer> pagesStream = BehaviorSubject.createDefault(1);

    private int page = 1;
    private boolean endOfPagination;

    public PhotosResultsHandler(
            GetPhotosApi getPhotosApi,
            SearchHandler searchHandler) {
        this.getPhotosApi = getPhotosApi;
        this.searchHandler = searchHandler;
    }


    private Observable<Integer> pages() {
        return pagesStream.hide();
    }

    @Override
    public void fetchMore() {
        if (endOfPagination) {
            return;
        }

        pagesStream.onNext(page);
    }

    /**
     * Fetch photos associated with the search key and page number.
     * Page number is to support pagination.
     *
     * @param key  search key entered by user.
     * @param page result page for pagination.
     * @return {@link Observable} Stream of {@link Photos}.
     */
    private Observable<Resource<Photos>> fetchPhotos(String key, int page) {
        return getPhotosApi.getPhotos(constructQueryParams(key, page))
                .onErrorReturn(throwable -> null)
                .map((Function<GetPhotosResponse, Resource<Photos>>) response -> {
                    if (response == null) {
                        return Resource.error("Something went wrong", null);
                    }

                    return Resource.success(transform(response.getPhotos()));
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void reset() {
        page = 1;
    }

    @Override
    public Observable<Resource<Photos>> subscribeForResults() {
        // fetch photos and display
        return Observable
                .combineLatest(
                        searchHandler.searchKeyStream().distinct(),
                        pages(),
                        Pair::create)
                .switchMap(pair -> fetchPhotos(pair.first, pair.second));
    }

    @Override
    public Observable<String> subscribeForSearchClicks() {
        return searchHandler.searchKeyStream().distinct();
    }

    private Photos transform(@Nullable Photos photos) {
        if (photos == null) {
            return null;
        }

        page = photos.getPage();
        // check end of pagination
        if (page == photos.getPages()) {
            endOfPagination = true;
        }

        page++;
        return photos;
    }

    private Map<String, String> constructQueryParams(@NonNull String key, int page) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("text", key);
        queryParams.put("format", "json");
        queryParams.put("extras", "url_s");
        queryParams.put("nojsoncallback", "1");
        queryParams.put("per_page", NUM_IMAGES);
        queryParams.put("page", String.valueOf(page));
        return queryParams;
    }
}
