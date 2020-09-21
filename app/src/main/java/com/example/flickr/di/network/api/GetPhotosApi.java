package com.example.flickr.di.network.api;

import com.example.flickr.models.home.GetPhotosResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.example.flickr.di.network.NetworkUtil.FLICKR_API_KEY;

/**
 * Provides the list of available api's to fetch data.
 */
public interface GetPhotosApi {
    /**
     * Fetches the {@link GetPhotosResponse} for a given set of query params.
     *
     * @param queryParams {@link Map} of request params.
     * @return {@link Observable} stream of {@link GetPhotosResponse} response.
     */
    @GET("?method=flickr.photos.search&api_key=" + FLICKR_API_KEY)
    Observable<GetPhotosResponse> getPhotos(@QueryMap Map<String, String> queryParams);
}
