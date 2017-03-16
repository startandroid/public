package ru.startandroid.vocabulary.data.verb;

import android.os.Parcel;
import android.os.Parcelable;

import ru.startandroid.vocabulary.data.record.Record;

public class Verb implements Parcelable {

    private long id;
    private String first;
    private String second;
    private String third;
    private String translate;
    private long rememberedCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public long getRememberedCount() {
        return rememberedCount;
    }

    public void setRememberedCount(long rememberedCount) {
        this.rememberedCount = rememberedCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(first);
        dest.writeString(second);
        dest.writeString(third);
        dest.writeString(translate);
        dest.writeLong(rememberedCount);
    }

    public Verb() {}

    public Verb(Parcel parcel) {
        setId(parcel.readLong());
        setFirst(parcel.readString());
        setSecond(parcel.readString());
        setThird(parcel.readString());
        setTranslate(parcel.readString());
        setRememberedCount(parcel.readLong());
    }

    public static final Parcelable.Creator<Verb> CREATOR = new Parcelable.Creator<Verb>() {

        @Override
        public Verb createFromParcel(Parcel parcel) {
            return new Verb(parcel);
        }

        @Override
        public Verb[] newArray(int i) {
            return new Verb[i];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Verb verb = (Verb) o;

        return id == verb.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
