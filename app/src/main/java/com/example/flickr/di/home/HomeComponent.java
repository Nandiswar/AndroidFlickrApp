package com.example.flickr.di.home;

import com.example.flickr.ui.home.HomeActivity;
import com.example.flickr.ui.home.results.PhotosResultsFragment;
import com.example.flickr.ui.home.search.SearchFragment;

import dagger.Subcomponent;

/**
 * {@link HomeComponent} is a Dagger Subcomponent responsible for providing the dependencies for the
 * Activities and Fragments being inject. This component is confined to {@link HomeScope}.
 */
@HomeScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {
    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        HomeComponent create();
    }

    /**
     * This tells Dagger that HomeActivity requests injection from HomeComponent
     * so that this subcomponent graph needs to satisfy all the dependencies of the
     * fields that HomeActivity is injecting
     */
    void inject(HomeActivity homeActivity);

    /**
     * This tells Dagger that SearchFragment requests injection from HomeComponent
     * so that this subcomponent graph needs to satisfy all the dependencies of the
     * fields that SearchFragment is injecting
     */
    void inject(SearchFragment searchFragment);

    /**
     * This tells Dagger that PhotosResultsFragment requests injection from HomeComponent
     * so that this subcomponent graph needs to satisfy all the dependencies of the
     * fields that PhotosResultsFragment is injecting
     */
    void inject(PhotosResultsFragment resultsFragment);
}
