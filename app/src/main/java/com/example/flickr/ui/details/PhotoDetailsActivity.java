package com.example.flickr.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flickr.R;
import com.example.flickr.models.home.Photo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class PhotoDetailsActivity extends AppCompatActivity {

    public static final String PHOTO_KEY = "selected_photo";

    //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
    private static final String URL_FORMAT = "https://farm%d.staticflickr.com/%s/%s_%s_c.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        ImageView placeholderImg = findViewById(R.id.placeholder_img);
        ImageView img = findViewById(R.id.img);
        TextView title = findViewById(R.id.title);

        Bundle bundle = getIntent().getExtras();
        Photo photo = getPhoto(bundle);
        if (photo == null) {
            return;
        }

        placeholderImg.setVisibility(View.VISIBLE);
        // display the image banner
        String url = constructURL(photo);
        Picasso.get().load(url).into(img, new Callback() {
            @Override
            public void onSuccess() {
                placeholderImg.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                placeholderImg.setVisibility(View.GONE);
                Toast.makeText(PhotoDetailsActivity.this,
                        "Unable to load image......", Toast.LENGTH_SHORT).show();
            }
        });

        // display the photo title
        title.setText(photo.getTitle());
    }

    /**
     * Construct an url to fetch the banner image based on the properties of selected {@link Photo}
     * and the URL format {@link #URL_FORMAT}.
     *
     * @param photo selected {@link Photo}
     * @return image url to be loaded
     */
    private String constructURL(Photo photo) {
        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        int farmId = photo.getFarm();
        String serverId = photo.getServer();
        String id = photo.getId();
        String secret = photo.getSecret();
        return String.format(Locale.ENGLISH, URL_FORMAT, farmId, serverId, id, secret);
    }

    private Photo getPhoto(@Nullable Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        return (Photo) bundle.getSerializable(PHOTO_KEY);
    }
}
