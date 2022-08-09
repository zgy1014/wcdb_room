package com.wcdb.tool.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SmallTable {

//    public int id;

    public String fId;

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String subId;

    public String title;

    //开始时间 格式yyyy-MM-dd
    public String startTime;

    /// 重复天数 周一 周二
    public String repeatDay;

    /// 重复时间。19:00-20:00
    public String repeatTime;

    /// 结束时间 格式yyyy-MM-dd
    public String endTime;

    public long createTime;

    /// 是否开启提醒
    public Boolean isRemind ;

    /// 完成进度
    public Float progress = 0f;

    /// 当前完成进度
    public Float currentProgress = 0f;

    //case ing = "进行中"
    //    case stop = "暂停"
    //    case noStart = "暂未开始"
    //    case advance = "提前完成"
    //    case delay = "已延期"
    //   case noFinish = "未完成"
    public String status = "noStart";

    //子目标总得需要打卡次数
    public int tick ;

    //子目标延期次数
    public int delayCount;


    //打卡次数，每次+1
    public int tickNum ;

    //提前完成时间
    public String completeDay;

    @Override
    public String toString() {
        return "SubFlagModel{" +
                "subId='" + subId + '\'' +
                "fId='" + fId + '\'' +
                ", title='" + title + '\'' +
                ", startTime='" + startTime + '\'' +
                ", repeatDay='" + repeatDay + '\'' +
                ", repeatTime='" + repeatTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", isRemind=" + isRemind +
                ", progress=" + progress +
                ", createTime='" + createTime + '\'' +
                ", currentProgress=" + currentProgress +
                ", status=" + status +
                ", tick=" + tick +
                ", completeDay=" + completeDay +

                '}';
    }



}
