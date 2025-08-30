package com.example.Travel.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.Travel.entities.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User getUser(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);
}