package com.example.yedidim.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ReportDao {

    @Query("select * from Report")
    List<Report> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Report... Report);

    @Delete
    void delete(Report report);

    @Query("SELECT * FROM Report WHERE reportID=:id")
    Report getReportByID(String id);

    @Query("SELECT * FROM Report WHERE userName=:u")
    List<Report> getMyReports(String u);
}
