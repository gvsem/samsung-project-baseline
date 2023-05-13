package com.example.mylovelyproject.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mylovelyproject.model.entity.Group;
import com.example.mylovelyproject.model.entity.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("select * from students order by id")
    List<Student> getStudents();

    @Query("select * from students where group_id = :groupId order by id")
    List<Student> getStudentsOfGroup(long groupId);

    @Query("select * from students where id = :id")
    Student getStudent(long id);

    @Query("select * from students where lastname like :lastname and group_id = :groupId")
    List<Student> findStudentsByLastname(String lastname, long groupId);

    @Insert
    long createStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    int removeStudent(Student student);
    
}
