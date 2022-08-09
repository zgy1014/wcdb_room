package com.wcdb.tool.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DayTable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String subId;

    public String title;

    public String repeatTime;

    //格式yyyy-MM-dd
    public String day;

    public String fId;

    public boolean isTick;

    public long createTime;


    @Override
    public String toString() {
        return "DayDBModel{" +
                "id=" + id +
                "subId=" + subId +
                ", title='" + title + '\'' +
                ", repeatTime='" + repeatTime + '\'' +
                ", day=" + day +
                '}';
    }


}
