package com.example.mijin.hue.LoginTab.Tab3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mijin on 2017-10-22.
 */

public class FriendViewItem implements Parcelable{

    private int profile;
    private String id;
    private String name;
    private String email;
    private String phone;

    public FriendViewItem() {
    }

    public FriendViewItem(int profile, String id, String name, String email, String phone) {
        this.profile = profile;
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(profile);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(phone);
    }

    public static final Parcelable.Creator<FriendViewItem> CREATOR = new Creator<FriendViewItem>(){
        @Override
        public FriendViewItem createFromParcel(Parcel parcel) {
            int profile = parcel.readInt();
            String id = parcel.readString();
            String name = parcel.readString();
            String email = parcel.readString();
            String phone = parcel.readString();

            return new FriendViewItem(profile, id, name, email, phone);

        }

        @Override
        public FriendViewItem[] newArray(int i) {
            return new FriendViewItem[i];
        }
    };
}
