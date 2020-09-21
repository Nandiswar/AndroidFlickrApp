package com.example.flickr.ui.home.results;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.flickr.R;
import com.example.flickr.models.home.Photo;
import com.example.flickr.models.home.Photos;
import com.example.flickr.ui.details.PhotoDetailsActivity;
import com.example.flickr.ui.home.HomeActivity;
import com.example.flickr.ui.home.ResultsHandler;
import com.example.flickr.util.Resource;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.flickr.AppConstants.DEBUG_TAG;

public class PhotosResultsFragment extends Fragment implements PhotosResultAdapter.PhotoClickListener {
    @Inject
    ResultsHandler resultsHandler;

    @Nullable
    private Disposable resetDisposable;
    @Nullable
    private Disposable resultsDisposable;

    private RecyclerView recyclerView;
    private PhotosResultAdapter photoAdapter;
    private CustomScrollListener customListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() == null) {
            Log.w(DEBUG_TAG, "Unable to attach PhotosResultsFragment to HomeActivity");
        }
        ((HomeActivity) getActivity()).homeComponent.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        setupRecyclerView();

        // for recycler view infinite scroll listener
        customListener.fetchedData(false);

        // reset views
        resetDisposable = resultsHandler.subscribeForSearchClicks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> resetViews());

        // fetch photos and display
        resultsDisposable = resultsHandler.subscribeForResults()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResultsData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispose(resetDisposable);
        dispose(resultsDisposable);
    }

    @Override
    public void photoClicked(Photo photo) {
        Intent intent = new Intent(getActivity(), PhotoDetailsActivity.class);
        intent.putExtra(PhotoDetailsActivity.PHOTO_KEY, photo);
        startActivity(intent);
    }

    private void setupRecyclerView() {
        int cols = getResources().getInteger(R.integer.gallery_columns);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(cols, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        photoAdapter = new PhotosResultAdapter(this);
        recyclerView.setAdapter(photoAdapter);

        // set up custom listener for recycler view
        customListener = new CustomScrollListener(
                layoutManager, () -> resultsHandler.fetchMore());
        // infinite scrolling handling
        recyclerView.addOnScrollListener(customListener);
    }

    private void handleResultsData(Resource<Photos> listResource) {
        if (listResource == null) {
            return;
        }

        switch (listResource.status) {
            case LOADING:
                Toast.makeText(getActivity(), R.string.fetching_images, Toast.LENGTH_SHORT).show();
            case ERROR:
                Toast.makeText(getActivity(), R.string.error_fetching_images, Toast.LENGTH_SHORT).show();
                break;
            case SUCCESS:
                photoAdapter.addPhotos(listResource.data);
                customListener.fetchedData(true);
                break;
        }
    }

    private void resetViews() {
        photoAdapter.clearAll();
        resultsHandler.reset();
    }

    private void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
