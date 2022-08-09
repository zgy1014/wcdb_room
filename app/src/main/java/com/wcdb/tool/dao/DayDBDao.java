package com.wcdb.tool.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.wcdb.tool.model.DayTable;

import java.util.List;

@Dao
public interface DayDBDao {

    @Query("SELECT * FROM DayTable")
    List<DayTable> getAll();

    @Query("SELECT * FROM DayTable WHERE subId = :subId")
    List<DayTable> getById(String subId);

    @Query("SELECT * FROM DayTable WHERE day = :day order by isTick,repeatTime,createTime asc")
    List<DayTable> getByDay(String day);

    @Query("SELECT * FROM DayTable WHERE subId = :subId and day = :day")
    DayTable getByIdAndDay(String subId,String day);

    @Insert
    void insert(DayTable... dayDBs);

    @Insert
    void insertAll(List<DayTable> dayDBList);

    @Delete
    void delete(DayTable dayDBModel);

    @Query("DELETE FROM DayTable WHERE fId = :fId")
    void deleteDayTableByFId(String fId);

    @Query("DELETE FROM DayTable WHERE subId = :subId")
    void deleteAllBySubId(String subId);

    @Query("DELETE FROM DayTable WHERE subId = :subId and day > :days")
    void deleteAllBySubIdOrDay(String subId,String days);

    @Query("DELETE FROM DayTable")
    void deleteAll();

    @Update
    void update(DayTable dayDBModel);
}
