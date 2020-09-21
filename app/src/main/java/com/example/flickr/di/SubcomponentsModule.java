package com.example.flickr.di;

import com.example.flickr.di.home.HomeComponent;

import dagger.Module;

/**
 * The "subcomponents" attribute in the @Module annotation tells Dagger what
 * Subcomponents are children of the Component this module is included in.
 */
@Module(subcomponents = HomeComponent.class)
public class SubcomponentsModule {
}
