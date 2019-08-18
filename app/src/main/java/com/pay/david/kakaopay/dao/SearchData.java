package com.pay.david.kakaopay.dao;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchData implements Parcelable {
    public VideoIds id;
    public Snippet snippet;

    public SearchData(Snippet snippet, VideoIds id) {
        super();
        this.snippet = snippet;
        this.id = id;
    }

    protected SearchData(Parcel in) {
        id = in.readParcelable(VideoIds.class.getClassLoader());
        snippet = in.readParcelable(Snippet.class.getClassLoader());
    }

    public static final Creator<SearchData> CREATOR = new Creator<SearchData>() {
        @Override
        public SearchData createFromParcel(Parcel in) {
            return new SearchData(in);
        }

        @Override
        public SearchData[] newArray(int size) {
            return new SearchData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(id, i);
        parcel.writeParcelable(snippet, i);
    }
}
