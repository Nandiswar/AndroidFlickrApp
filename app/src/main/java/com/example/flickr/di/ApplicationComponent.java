package com.example.flickr.di;

import com.example.flickr.di.home.HomeComponent;
import com.example.flickr.di.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * This is an application level scoped component.
 */
@Singleton
@Component(modules = {NetworkModule.class, SubcomponentsModule.class})
public interface ApplicationComponent {
    /**
     * This function exposes the HomeComponent Factory out of the graph so consumers
     * can use it to obtain new instances of HomeComponent.
     *
     * @return {@link HomeComponent.Factory}
     */
    HomeComponent.Factory homeComponent();
}
