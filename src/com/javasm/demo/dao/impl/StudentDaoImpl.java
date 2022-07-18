package com.javasm.demo.dao.impl;

import com.javasm.demo.dao.StudentDao;
import com.javasm.demo.entity.Student;
import com.javasm.demo.util.DBUtil;
import sun.security.pkcs11.Secmod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public int addStudent(Student student) {
        int result = 0;
        //获取连接
        Connection connection = DBUtil.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO student (name, age, address ) VALUES  (?,?,?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getAddress());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps);
        }
        return result;
    }

    @Override
    public int deleteStudentById(int id) {
        int result = 0;
        PreparedStatement ps = null;
        Connection connection = DBUtil.getConnection();
        String sql = "DELETE FROM student WHERE id=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps);
        }
        return result;
    }

    @Override
    public int deleteStudentsByIds(int[] ids) {
        int result = 0;
        PreparedStatement ps = null;
        Connection connection = DBUtil.getConnection();
        StringBuffer buffer = new StringBuffer();
        buffer.append("DELETE FROM student WHERE id IN (");
        int length = ids.length;
        for (int i = 1; i <= length; i++) {
            buffer.append("?");
            if (i == length) {
                buffer.append(")");
                break;
            }
            buffer.append(",");
        }
        try {
            ps = connection.prepareStatement(buffer.toString());
            for (int i = 1; i <= length; i++) {
                ps.setInt(i, ids[i - 1]);
            }
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps);
        }
        return result;
    }

    @Override
    public int updateStudent(Student student) {
        Connection connection = DBUtil.getConnection();
        PreparedStatement ps = null;
        int result = 0;
        String sql = "UPDATE student SET name=?,age=?,address=? WHERE id=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getAddress());
            ps.setInt(4, student.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps);
        }
        return result;
    }

    @Override
    public Student getStudentById(int id) {
        Student student = null;
        Connection connection = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        String sql = "SELECT * FROM student WHERE id=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setAge(resultSet.getInt("age"));
                student.setAddress(resultSet.getString("address"));
                student.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, resultSet);
        }
        return student;
    }

    @Override
    public List<Student> getStudentList() {
        Connection connection = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(getStudentInfo(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, resultSet);
        }
        return list;
    }

    @Override
    public int getStudentCount() {
        Connection connection = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        String sql = "select count(*) from student";
        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, resultSet);
        }
        return result;
    }

    @Override
    public List<Student> getStudentListByPage(int page, int size) {
        Connection connection = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        List<Student> list=new ArrayList<>();
        String sql = "SELECT * FROM student limit ?,?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (page - 1) * size);
            ps.setInt(2, size);
            resultSet = ps.executeQuery();
            while (resultSet.next()){
                list.add(getStudentInfo(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps,resultSet);
        }
        return list;
    }

    private Student getStudentInfo(ResultSet resultSet) {
        Student student = new Student();
        try {
            student.setId(resultSet.getInt("id"));
            student.setAddress(resultSet.getString("address"));
            student.setAge(resultSet.getInt("age"));
            student.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
}
