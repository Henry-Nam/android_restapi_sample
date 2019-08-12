package com.pay.david.kakaopay.dao;

import com.google.gson.annotations.SerializedName;

public class SearchData {
    public final VideoIds id;
    public final Snippet snippet;

    public SearchData(Snippet snippet, VideoIds id) {
        super();
        this.snippet = snippet;
        this.id = id;
    }

    public static class VideoIds {
        public final String videoId;

        public VideoIds(String videoId) {
            this.videoId = videoId;
        }
    }

    public static class Snippet {
        public final String channelId;
        public final String title;
        public final String description;
        public final Thumbnail thumbnails;
        public final String publishedAt;

        public Snippet(String channelId, String title, String description, Thumbnail thumbnails, String publishedAt) {
            super();
            this.channelId = channelId;
            this.title = title;
            this.description = description;
            this.thumbnails = thumbnails;
            this.publishedAt = publishedAt;
        }
    }

    public static class Thumbnail {
        @SerializedName("default")
        public final Default defaults;
        @SerializedName("high")
        public final High highs;

        public Thumbnail(Default defaults, High highs) {
            this.defaults = defaults;
            this.highs = highs;
        }
    }

    public static class Default {
        public final String url;

        public Default(String url) {
            this.url = url;
        }
    }

    public static class High {
        public final String url;

        public High(String url) {
            this.url = url;
        }
    }
}
