package com.andrien.technicaltest;

public class Photo {
    private int albumId;
    private int id;
    private String title;
    private String imageUrl;
    private String thumbnailUrl;

    public Photo(){

    }

    public Photo(int albumId, int id, String title, String imageUrl, String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;

    }


    public int getAlbumid() {
        return albumId;
    }

    public void setAlbumid(int albumid) {
        this.albumId = albumid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
