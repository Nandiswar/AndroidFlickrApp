package com.example.flickr.models.home;

import java.io.Serializable;

public class GetPhotosResponse implements Serializable {
    Photos photos;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }
}
