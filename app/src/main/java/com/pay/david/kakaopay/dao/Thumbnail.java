package com.pay.david.kakaopay.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Thumbnail implements Parcelable {
        @SerializedName("default")
        public final Default defaults;
        @SerializedName("high")
        public final High highs;

        public Thumbnail(Default defaults, High highs) {
            this.defaults = defaults;
            this.highs = highs;
        }

    protected Thumbnail(Parcel in) {
        defaults = in.readParcelable(Default.class.getClassLoader());
        highs = in.readParcelable(High.class.getClassLoader());
    }

    public static final Creator<Thumbnail> CREATOR = new Creator<Thumbnail>() {
        @Override
        public Thumbnail createFromParcel(Parcel in) {
            return new Thumbnail(in);
        }

        @Override
        public Thumbnail[] newArray(int size) {
            return new Thumbnail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(defaults, i);
        parcel.writeParcelable(highs, i);
    }


    public static class Default implements Parcelable {
        public final String url;

        public Default(String url) {
            this.url = url;
        }

        protected Default(Parcel in) {
            url = in.readString();
        }

        public static final Creator<Default> CREATOR = new Creator<Default>() {
            @Override
            public Default createFromParcel(Parcel in) {
                return new Default(in);
            }

            @Override
            public Default[] newArray(int size) {
                return new Default[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(url);
        }
    }

    public static class High implements Parcelable {
        public final String url;

        public High(String url) {
            this.url = url;
        }

        protected High(Parcel in) {
            url = in.readString();
        }

        public static final Creator<High> CREATOR = new Creator<High>() {
            @Override
            public High createFromParcel(Parcel in) {
                return new High(in);
            }

            @Override
            public High[] newArray(int size) {
                return new High[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(url);
        }
    }
}
