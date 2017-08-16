package ru.startandroid.messagelist.data.message;

import android.os.Parcel;
import android.os.Parcelable;

public class UiMessage implements Parcelable {
    private long id;
    private long time;
    private String text;
    private String image;
    private boolean favorite;

    public UiMessage() {

    }

    protected UiMessage(Parcel in) {
        id = in.readLong();
        time = in.readLong();
        text = in.readString();
        image = in.readString();
        favorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(time);
        dest.writeString(text);
        dest.writeString(image);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UiMessage> CREATOR = new Creator<UiMessage>() {
        @Override
        public UiMessage createFromParcel(Parcel in) {
            return new UiMessage(in);
        }

        @Override
        public UiMessage[] newArray(int size) {
            return new UiMessage[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
