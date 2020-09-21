package com.example.flickr.ui.home.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flickr.R;
import com.example.flickr.ui.home.HomeActivity;
import com.example.flickr.ui.home.SearchHandler;

import javax.inject.Inject;

import static com.example.flickr.AppConstants.DEBUG_TAG;

public class SearchFragment extends Fragment {
    @Inject
    SearchHandler searchHandler;

    private Button searchBtn;
    private EditText searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() == null) {
            Log.w(DEBUG_TAG, "Unable to attach SearchFragment to HomeActivity");
        }
        ((HomeActivity) getActivity()).homeComponent.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchBtn = view.findViewById(R.id.search_button);
        searchView = view.findViewById(R.id.search_text);

        subscribeForSearchBtnClicks();

        // load with initial key search
        searchView.setText("dogs");
        searchBtn.performClick();
    }

    private void subscribeForSearchBtnClicks() {
        // handle search string entered by user
        searchBtn.setOnClickListener(l -> {
            String text = searchView.getText().toString();
            searchHandler.handleSearchKey(text);
            hideKeyboard();
            searchView.clearFocus();
        });
    }

    private void hideKeyboard() {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        View view = activity.getCurrentFocus() == null ? new View(activity) : activity.getCurrentFocus();
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
