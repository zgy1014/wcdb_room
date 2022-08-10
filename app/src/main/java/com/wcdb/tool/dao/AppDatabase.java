package com.wcdb.tool.dao;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.wcdb.tool.model.FlagTable;
import com.wcdb.tool.model.SmallTable;

@Database(entities = {FlagTable.class, SmallTable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FlagInfoDao flagInfoDao();

    public abstract SubFlagModelDao subFlagDao();

}
