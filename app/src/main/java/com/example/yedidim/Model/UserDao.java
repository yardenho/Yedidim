package com.example.yedidim.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from User")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... User);

    @Delete
    void delete(User u);

    @Update
    void editUser(User u);

    @Query("SELECT * FROM User WHERE userName=:uName")
    User getUserByUserName(String uName);

}
