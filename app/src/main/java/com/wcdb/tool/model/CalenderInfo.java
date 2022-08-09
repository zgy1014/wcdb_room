package com.wcdb.tool.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CalenderInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String subId;

    public String eventId;

    public String flagId;

    public String startTime;


    @Override
    public String toString() {
        return "CalenderInfo{" +
                "id=" + id +
                "startTime=" + startTime +
                ", eventId='" + eventId + '\'' +
                ", subId='" + subId + '\'' +
                '}';
    }


}
