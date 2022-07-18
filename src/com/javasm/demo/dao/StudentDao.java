package com.javasm.demo.dao;

import com.javasm.demo.entity.Student;

import java.util.List;

public interface StudentDao {

    public int addStudent(Student student);

    public int deleteStudentById(int id);

    public int deleteStudentsByIds(int[] ids);

    public int updateStudent(Student student);

    public Student getStudentById(int id);

    public List<Student> getStudentList();
    public int getStudentCount();
    public List<Student> getStudentListByPage(int page,int size);
}
