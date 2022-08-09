package com.wcdb.tool.dao;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wcdb.tool.model.CalenderInfo;
import com.wcdb.tool.model.DayTable;
import com.wcdb.tool.model.FlagTable;
import com.wcdb.tool.model.SmallTable;

@Database(entities = {DayTable.class, FlagTable.class, SmallTable.class, CalenderInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DayDBDao dayDBDao();

    public abstract FlagInfoDao flagInfoDao();

    public abstract SubFlagModelDao subFlagDao();

    public abstract CalenderDao calenderDao();

}
