package com.example.flickr.ui.home.results;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.example.flickr.AppConstants.DEBUG_TAG;

public class CustomScrollListener extends OnScrollListener {
    private final StaggeredGridLayoutManager layoutManager;
    private final Listener listener;
    private boolean fetchedData;

    public CustomScrollListener(StaggeredGridLayoutManager layoutManager, Listener listener) {
        this.layoutManager = layoutManager;
        this.listener = listener;
    }

    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) { //check for scroll down
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = 0;

            int[] firstVisibleItems = layoutManager.findFirstVisibleItemPositions(null);
            if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                pastVisibleItems = firstVisibleItems[0];
            }

            if (fetchedData) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    fetchedData = false;
                    Log.i(DEBUG_TAG, "last row reached....");
                    // Do pagination.. i.e. fetch new data
                    listener.reachedContainerEnd();
                }
            }
        }
    }

    public void fetchedData(boolean fetchData) {
        this.fetchedData = fetchData;
    }


    public interface Listener {
        /**
         * This is invoked when end of the list is reached and can be used for pagination
         * to fetch more photos.
         */
        void reachedContainerEnd();
    }
}
