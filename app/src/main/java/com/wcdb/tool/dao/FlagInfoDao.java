package com.wcdb.tool.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.wcdb.tool.model.FlagTable;

import java.util.List;

@Dao
public interface FlagInfoDao {

    @Query("SELECT * FROM FlagTable")
    List<FlagTable> getAll();

    @Query("SELECT * FROM FlagTable WHERE flagId = :flagId")
    FlagTable getById(String flagId);

    @Query("SELECT * FROM FlagTable WHERE isComplete = :isComplete order by createTime asc")
    List<FlagTable> getAll(boolean isComplete);

    @Insert
    void insert(FlagTable... flagInfo);

    @Delete
    void delete(FlagTable flagInfo);

    @Query("DELETE FROM FlagTable WHERE flagId = :flagId")
    void deleteFlagById(String flagId);

    @Query("DELETE FROM FlagTable")
    void deleteAll();

    @Update
    void update(FlagTable flagInfo);
}
