package com.example.mylovelyproject.service;

import com.example.mylovelyproject.model.AppDatabase;
import com.example.mylovelyproject.model.dao.StudentDao;
import com.example.mylovelyproject.model.entity.Student;

import java.util.List;

public class StudentService {

    private final StudentDao studentDao;

    public StudentService(AppDatabase database) {
        studentDao = database.studentDao();
    }

    public List<Student> getStudents() {
        return studentDao.getStudents();
    }

    public List<Student> getStudents(long groupId) {
        return studentDao.getStudentsOfGroup(groupId);
    }

    public Student getStudent(long studentId) {
        return studentDao.getStudent(studentId);
    }

    public Student createStudent(Student Student) {
        long id = studentDao.createStudent(Student);
        return studentDao.getStudent(id);
    }

    public List<Student> searchStudentsByLastname(String lastname, long groupId) {
        return studentDao.findStudentsByLastname(lastname, groupId);
    }

    public Student editStudent(Student Student) {
        studentDao.updateStudent(Student);
        return Student;
    }

    public void deleteStudent(Student student) throws IllegalArgumentException {
        if (studentDao.removeStudent(student) != 1) {
            throw new IllegalArgumentException("Problemo");
        };
    }

}
