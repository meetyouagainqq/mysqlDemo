package com.javasm.demo;

import com.javasm.demo.dao.StudentDao;
import com.javasm.demo.dao.impl.StudentDaoImpl;
import com.javasm.demo.entity.Student;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MysqlTest {
    public static void main(String[] args) {
        findStudentByPage();
        System.out.println("master");
        System.out.println("master123");
    }

    private static void findStudentByPage() {
        Scanner scanner = new Scanner(System.in);
        StudentDao studentDao = new StudentDaoImpl();
        final int size = 5;
        int count = studentDao.getStudentCount();
        int pageCount = count / size;
        pageCount = (count % size) == 0 ? pageCount : pageCount + 1;
        int curPage = 1;
        do {
            System.out.println("第" + curPage + "页的数据");
            List<Student> list = studentDao.getStudentListByPage(curPage, size);
            list.forEach(System.out::println);
            System.out.println("*****************");
            for (int i = 0; i < pageCount; i++) {
                System.out.print(i + 1 + "\t");
            }
            System.out.println("共" + pageCount + "页");
            System.out.println("是否要继续查询?y/n");
            String inputValue = scanner.next();
            if (Objects.equals("n", inputValue)) {
                  return;
            }
            System.out.println("请输入要查询的页数:");
            int inputCount=scanner.nextInt();
            curPage=inputCount;
        } while (true);
    }

    private static void getStudentCount() {
        StudentDao studentDao = new StudentDaoImpl();
        System.out.println(studentDao.getStudentCount());
    }

    private static void getStudentList() {
        StudentDao studentDao = new StudentDaoImpl();
        List<Student> list = studentDao.getStudentList();
        list.forEach(System.out::println);
    }

    private static void updateStudentTest() {
        Scanner scanner = new Scanner(System.in);
        StudentDao studentDao = new StudentDaoImpl();
        System.out.println("请输入用户id:");
        int id = scanner.nextInt();
        Student student = studentDao.getStudentById(id);
        System.out.println("用户信息如下:" + student);
        System.out.println();
        System.out.println("请输入要修改的列:1:姓名 2:年龄 3:地址");
        String next = scanner.next();
        String[] choiceArray = next.split(",");
        for (String str : choiceArray) {
            int choice = Integer.parseInt(str);
            switch (choice) {
                case 1:
                    System.out.println("请录入新的姓名:");
                    String name = scanner.next();
                    student.setName(name);
                    break;
                case 2:
                    System.out.println("请录入新的年龄:");
                    int age = scanner.nextInt();
                    student.setAge(age);
                    break;
                case 3:
                    System.out.println("请录入新的地址:");
                    String address = scanner.next();
                    student.setAddress(address);
                    break;
            }
        }
        System.out.println(studentDao.updateStudent(student));
    }
}
