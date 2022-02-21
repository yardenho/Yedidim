package com.example.yedidim.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.yedidim.MyApplication;

@Database(entities = {Report.class}, version = 18)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ReportDao reportDao();
}

public class AppLocalDB {
    static public final AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
    private AppLocalDB(){}

}

