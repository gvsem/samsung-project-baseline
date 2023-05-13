package com.example.mylovelyproject.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "groups")
public class Group {

    @PrimaryKey(autoGenerate = true)
    @Getter @Setter
    private Long id;

    @ColumnInfo(name = "no")
    @Getter @Setter
    private String no;

    @ColumnInfo(name = "faculty_name")
    @Getter @Setter
    private String facultyName;

}
