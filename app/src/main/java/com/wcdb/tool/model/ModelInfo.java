package com.wcdb.tool.model;


import com.wcdb.tool.event.IBus;

/**
 * 
*
 */
public class ModelInfo extends IBus.AbsEvent{

    public String token;

    //更新flag
    public Boolean updateFlag = false;

    public String subId;

    public String title ;
    public int type ;


    @Override
    public int getTag() {
        return 1000;
    }

    @Override
    public String toString() {
        return "ModelInfo{" +
                "token='" + token + '\'' +
                ", updateFlag=" + updateFlag +
                ", subId='" + subId + '\'' +
                '}';
    }
}
