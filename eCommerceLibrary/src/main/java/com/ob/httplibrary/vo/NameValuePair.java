package com.ob.httplibrary.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NameValuePair implements Parcelable {

    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;
    @SerializedName("shouldEncode")
    private boolean shouldEncode;

    /**
     * Constructs a new object with the given value and these values will be encoded by default
     *
     * @param name  Name
     * @param value Value
     */
    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
        this.shouldEncode = true;
    }

    /**
     * Constructs a new object with the given value and these values will be encoded by default
     *
     * @param name         Name
     * @param value        Value
     * @param shouldEncode flag to determine whether to encode these
     */
    public NameValuePair(String name, String value, boolean shouldEncode) {
        this.name = name;
        this.value = value;
        this.shouldEncode = shouldEncode;
    }

    /**
     * Constructs a new object with the given value and these values will be encoded by default
     *
     * @param name         Name
     * @param value        Value
     */
    public NameValuePair(String name, int value) {
        this.name = name;
        this.value = Integer.toString(value);
        this.shouldEncode = true;
    }

    /**
     * Constructs a new object with the given value and these values will be encoded by default
     *
     * @param name         Name
     * @param value        Value
     * @param shouldEncode flag to determine whether to encode these
     */
    public NameValuePair(String name, int value, boolean shouldEncode) {
        this.name = name;
        this.value = Integer.toString(value);
        this.shouldEncode = shouldEncode;
    }

    /**
     * method to get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Method to get whether to encode field or not
     *
     * @return whether to encode field or not
     */
    public boolean shouldEncode() {
        return shouldEncode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
        dest.writeByte(shouldEncode ? (byte) 1 : (byte) 0);
    }

    protected NameValuePair(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
        this.shouldEncode = in.readByte() != 0;
    }

    public static final Creator<NameValuePair> CREATOR = new Creator<NameValuePair>() {
        public NameValuePair createFromParcel(Parcel source) {
            return new NameValuePair(source);
        }

        public NameValuePair[] newArray(int size) {
            return new NameValuePair[size];
        }
    };

    @Override
    public String toString() {
        return "NameValuePair{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", shouldEncode=" + shouldEncode +
                '}';
    }
}
