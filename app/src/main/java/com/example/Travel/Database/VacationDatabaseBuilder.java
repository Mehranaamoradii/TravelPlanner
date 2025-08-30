package com.example.Travel.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.Travel.DAO.ExcursionDAO;
import com.example.Travel.DAO.UserDAO;
import com.example.Travel.DAO.VacationDAO;
import com.example.Travel.entities.Excursion;
import com.example.Travel.entities.User;
import com.example.Travel.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class, User.class}, version= 7,exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    public abstract UserDAO userDAO();
    private static volatile VacationDatabaseBuilder INSTANCE;

    public static VacationDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE==null){
            synchronized (VacationDatabaseBuilder.class){
                if (INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),VacationDatabaseBuilder.class, "MyVacationDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}