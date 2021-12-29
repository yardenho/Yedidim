package com.example.yedidim.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MalfunctionDao {

    @Query("select * from Malfunction")
    List<Malfunction> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Malfunction... Malfunction);

    @Delete
    void delete(Malfunction m);

    @Update
    void editMalfunction(Malfunction m);

    @Query("SELECT * FROM Malfunction WHERE malfunctionID=:id")
    Malfunction getMalfunctionByID(String id);

}
