package com.example.flickr.di.home;

import com.example.flickr.di.network.api.GetPhotosApi;
import com.example.flickr.ui.home.ResultsHandler;
import com.example.flickr.ui.home.SearchHandler;
import com.example.flickr.ui.home.results.PhotosResultsHandler;
import com.example.flickr.ui.home.search.PhotoSearchHandler;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Provide the dependencies for the upstream component that this module is linked to, in this case,
 * {@link HomeComponent}
 */
@Module
public class HomeModule {
    @HomeScope
    @Provides
    GetPhotosApi providesGetPhotosApi(Retrofit retrofit) {
        return retrofit.create(GetPhotosApi.class);
    }

    @HomeScope
    @Provides
    SearchHandler providesSearchHandler() {
        return new PhotoSearchHandler();
    }

    @HomeScope
    @Provides
    ResultsHandler providesResultsHandler(
            GetPhotosApi getPhotosApi,
            SearchHandler searchHandler) {
        return new PhotosResultsHandler(getPhotosApi, searchHandler);
    }
}
