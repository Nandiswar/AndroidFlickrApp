package com.example.flickr.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.flickr.BaseApplication;
import com.example.flickr.R;
import com.example.flickr.di.home.HomeComponent;
import com.example.flickr.ui.home.results.PhotosResultsFragment;
import com.example.flickr.ui.home.search.SearchFragment;

public class HomeActivity extends AppCompatActivity {
    public HomeComponent homeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set up dagger components
        homeComponent = ((BaseApplication) getApplicationContext())
                .appComponent().homeComponent().create();
        homeComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add fragments
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.search_fragment, new SearchFragment());
            fragmentTransaction.replace(R.id.content_fragment, new PhotosResultsFragment());
            fragmentTransaction.commit();
        }
    }
}
