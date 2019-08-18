package com.pay.david.kakaopay.dao;

import android.os.Parcel;
import android.os.Parcelable;

public class Snippet implements Parcelable {
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

    protected Snippet(Parcel in) {
        channelId = in.readString();
        title = in.readString();
        description = in.readString();
        thumbnails = in.readParcelable(Thumbnail.class.getClassLoader());
        publishedAt = in.readString();
    }

    public static final Creator<Snippet> CREATOR = new Creator<Snippet>() {
        @Override
        public Snippet createFromParcel(Parcel in) {
            return new Snippet(in);
        }

        @Override
        public Snippet[] newArray(int size) {
            return new Snippet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(channelId);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeParcelable(thumbnails, i);
        parcel.writeString(publishedAt);
    }
}
