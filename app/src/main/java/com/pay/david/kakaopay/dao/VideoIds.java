package com.pay.david.kakaopay.dao;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoIds implements Parcelable {
    public final String videoId;

    public VideoIds(String videoId) {
        this.videoId = videoId;
    }

    protected VideoIds(Parcel in) {
        videoId = in.readString();
    }

    public static final Creator<VideoIds> CREATOR = new Creator<VideoIds>() {
        @Override
        public VideoIds createFromParcel(Parcel in) {
            return new VideoIds(in);
        }

        @Override
        public VideoIds[] newArray(int size) {
            return new VideoIds[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(videoId);
    }

}
