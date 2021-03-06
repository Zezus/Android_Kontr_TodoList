package com.example.kontr_todolist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by СадвакасовР on 19.04.2018.
 */

public class ItemList implements Parcelable {

    public static final Creator<ItemList> CREATOR = new Creator<ItemList>() {
        @Override
        public ItemList createFromParcel(Parcel in) {
            return new ItemList(in);
        }

        @Override
        public ItemList[] newArray(int size) {
            return new ItemList[size];
        }
    };
    private int id;
    private String name;
    private String title;

    public ItemList() {
    }

    public ItemList(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        data[0] = String.valueOf(id);
        data[1] = name;
        data[2] = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{String.valueOf(id), name, title});
    }
}
