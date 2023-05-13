package com.example.mylovelyproject.model.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GroupWithStudents {
    @Embedded
    public Group group;
    @Relation(
         parentColumn = "id",
         entityColumn = "group_id"
    )
    public List<Student> students;
}
