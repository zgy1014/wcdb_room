package com.wcdb.tool.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.wcdb.tool.model.CalenderInfo;

import java.util.List;

@Dao
public interface CalenderDao {

    @Query("SELECT * FROM CalenderInfo")
    List<CalenderInfo> getAll();

    @Query("SELECT * FROM CalenderInfo WHERE subId = :subId")
    CalenderInfo getById(String subId);

    @Insert
    void insert(CalenderInfo... flagInfo);

    @Delete
    void delete(CalenderInfo flagInfo);

    @Query("DELETE FROM CalenderInfo WHERE subId = :subId")
    void deleteAllCalenderBySubId(String subId);

    @Query("DELETE FROM CalenderInfo WHERE eventId = :eventId")
    void deleteAllByEventId(String eventId);

    @Query("DELETE FROM CalenderInfo WHERE startTime != :startTime")
    void deleteAllByTime(String startTime);

    @Query("DELETE FROM CalenderInfo")
    void deleteAll();

    @Update
    void update(CalenderInfo flagInfo);
}
