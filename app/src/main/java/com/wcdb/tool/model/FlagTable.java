package com.wcdb.tool.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FlagTable {

//    @PrimaryKey(autoGenerate = true)
//    public int id;

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String flagId;

    public String title;

    public String subTitle;

    public int color;

    public String completeDay;

    public boolean isComplete ;

    public long createTime;


    @Override
    public String toString() {
        return "FlagTable{" +
                "flagId=" + flagId +
                ", title='" + title + '\'' +
                ", createTime='" + createTime + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", color=" + color +
                '}';
    }


}
