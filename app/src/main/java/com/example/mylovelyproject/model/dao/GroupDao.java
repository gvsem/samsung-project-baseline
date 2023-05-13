package com.example.mylovelyproject.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mylovelyproject.model.entity.Group;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("select * from groups order by id")
    List<Group> getGroups();

    @Query("select * from groups where id = :id")
    Group getGroup(long id);

    @Insert
    long createGroup(Group group);

    @Update
    void updateGroup(Group group);

    @Delete
    int removeGroup(Group group);

}
