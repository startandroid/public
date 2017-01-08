package ru.startandroid.messagelist.data.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Message implements Parcelable {

    public Message() {
    }

    @SerializedName("id")
    private String id;

    @SerializedName("time")
    private long time;

    @SerializedName("text")
    private String text;

    @SerializedName("image")
    private String image;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return String.format("id = %s, time = %s, text = %s", getId(), getTime(), getText());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(time);
        dest.writeString(text);
        dest.writeString(image);
    }

    private Message(Parcel parcel) {
        setId(parcel.readString());
        setTime(parcel.readLong());
        setText(parcel.readString());
        setImage(parcel.readString());

    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {

        @Override
        public Message createFromParcel(Parcel parcel) {
            return new Message(parcel);
        }

        @Override
        public Message[] newArray(int i) {
            return new Message[i];
        }
    };
}
