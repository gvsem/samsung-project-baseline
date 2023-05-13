package com.example.mylovelyproject;

import android.app.Application;

import androidx.room.Room;

import com.example.mylovelyproject.model.AppDatabase;
import com.example.mylovelyproject.model.entity.Group;
import com.example.mylovelyproject.model.entity.Student;
import com.example.mylovelyproject.service.GroupService;
import com.example.mylovelyproject.service.StudentService;

import java.time.LocalDate;

public class MyLovelyApplication extends Application {

    public AppDatabase db;

    public GroupService groupService;

    public StudentService studentService;

    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-my-lovely").build();

        groupService = new GroupService(db);
        studentService = new StudentService(db);

    }
}
