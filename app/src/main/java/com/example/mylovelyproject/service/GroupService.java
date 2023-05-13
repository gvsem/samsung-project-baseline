package com.example.mylovelyproject.service;

import android.content.Context;

import com.example.mylovelyproject.model.AppDatabase;
import com.example.mylovelyproject.model.dao.GroupDao;
import com.example.mylovelyproject.model.dao.StudentDao;
import com.example.mylovelyproject.model.entity.Group;

import java.util.List;

public class GroupService {

    private final GroupDao groupDao;

    private final StudentDao studentDao;

    public GroupService(AppDatabase database) {
        groupDao = database.groupDao();
        studentDao = database.studentDao();
    }

    public List<Group> getGroups() {
        return groupDao.getGroups();
    }

    public Group getGroup(long id) {
        return groupDao.getGroup(id);
    }

    public Group createGroup(Group group) {
        long id = groupDao.createGroup(group);
        return groupDao.getGroup(id);
    }

    public Group editGroup(Group group) {
        groupDao.updateGroup(group);
        return group;
    }

    public void deleteGroup(Group group) throws IllegalArgumentException {
        if (!studentDao.getStudentsOfGroup(group.getId()).isEmpty()) {
            throw new IllegalArgumentException("Can not delete group - there are students inside");
        }
        if (groupDao.removeGroup(group) != 1) {
            throw new IllegalArgumentException("Problemo");
        };
    }

}
