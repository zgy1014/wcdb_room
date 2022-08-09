package com.wcdb.tool.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wcdb.tool.model.SmallTable;

import java.util.List;


@Dao
public interface SubFlagModelDao {

    @Query("SELECT * FROM SmallTable")
    List<SmallTable> getAll();

    @Query("SELECT * FROM SmallTable WHERE fId = :flagId")
    List<SmallTable> getAllByFlagId(String flagId);

//
//    @Query("SELECT * FROM SmallTable WHERE fId = :flagId and  status == :status")
//    List<SmallTable> getAllByFlagIdAndProgress(String flagId,String status);

    @Query("SELECT * FROM SmallTable WHERE fId = :flagId and (progress = :progress or status == :status)")
    List<SmallTable> getAllByFlagIdAndProgress(String flagId,float progress,String status);

    @Query("SELECT * FROM SmallTable WHERE fId = :flagId and progress != :progress and status != :status")
    List<SmallTable> getAllByFlagIdForProgress(String flagId,float progress,String status);

    /***
     * 延期，并且进度不是1.0，而且没有延期过的
     * @param status
     * @param progress
     * @param delayCount
     * @return
     */
    @Query("SELECT * FROM SmallTable WHERE status = :status and progress != :progress and delayCount = :delayCount")
    List<SmallTable> getAllByStatusForProgress(String status,float progress,int delayCount);

    @Query("SELECT * FROM SmallTable WHERE subId = :subId")
    SmallTable getBySubId(String subId);

    @Query("SELECT * FROM SmallTable WHERE  progress != :progress and (status = :status or status = :statu) order by repeatTime,createTime asc")
    List<SmallTable> getAllByStatusAndProgress(float progress,String status,String statu);

    @Query("SELECT * FROM SmallTable WHERE  subId != :subId  and  progress != :progress and (status = :status or status = :statu) order by repeatTime,createTime asc")
    List<SmallTable> getAllByStatusAndSb(String subId,float progress,String status,String statu);

    @Query("SELECT * FROM SmallTable WHERE status = :status or status = :statu order by repeatTime asc")
    List<SmallTable> getAllByStatus(String status,String statu);

    @Query("SELECT * FROM SmallTable WHERE status = :status")
    List<SmallTable> getAllSmallTableByStatus(String status);

    @Insert
    void insert(SmallTable... subFlagModels);

    @Delete
    void delete(SmallTable subFlagModels);

    @Query("DELETE FROM SmallTable")
    void deleteAll();

    @Query("DELETE FROM SmallTable WHERE fId = :flagId")
    void deleteAllByFId(String flagId);

    @Query("DELETE FROM SmallTable WHERE subId = :subId")
    void deleteSubFlagBySubId(String subId);

    @Update
    void update(SmallTable subFlagModels);






}
