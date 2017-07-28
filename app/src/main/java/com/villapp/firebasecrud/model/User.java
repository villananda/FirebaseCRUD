package com.villapp.firebasecrud.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS-PC on 20/07/2017.
 */

public class User implements Parcelable{
    public String uid, name, email, createdDate, updatedDate;

    public User(){}
    protected User(Parcel in) {
        uid = in.readString();
        name = in.readString();
        email = in.readString();
        createdDate = in.readString();
        updatedDate = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(createdDate);
        dest.writeString(updatedDate);
    }
}
