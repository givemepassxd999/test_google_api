package com.example.givemepass.bookapidemo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rick.wu on 2016/9/29.
 */

public class JsonData {
    @SerializedName("items")
    private List<Item> itemList;

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public static class Item{
        @SerializedName("volumeInfo")
        private Info info;

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }
    }

    public static class Info{
        @SerializedName("title")
        private String title;
        @SerializedName("publisher")
        private String publisher;
        @SerializedName("imageLinks")
        private ImageLink imageLink;

        public ImageLink getImageLink() {
            return imageLink;
        }

        public void setImageLink(ImageLink imageLink) {
            this.imageLink = imageLink;
        }
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }
    }

    public static class ImageLink{
        @SerializedName("smallThumbnail")
        private String smallThumbnail;
        @SerializedName("thumbnail")
        private String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getSmallThumbnail() {
            return smallThumbnail;
        }

        public void setSmallThumbnail(String smallThumbnail) {
            this.smallThumbnail = smallThumbnail;
        }
    }
}
