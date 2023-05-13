package com.example.mylovelyproject.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mylovelyproject.model.dao.GroupDao;
import com.example.mylovelyproject.model.dao.StudentDao;
import com.example.mylovelyproject.model.entity.Group;
import com.example.mylovelyproject.model.entity.Student;

@Database(entities = {Group.class, Student.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GroupDao groupDao();
    public abstract StudentDao studentDao();
}
