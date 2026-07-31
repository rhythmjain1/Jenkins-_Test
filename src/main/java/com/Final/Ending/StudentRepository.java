package com.Final.Ending;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> getAllStudents(){
        String sql = "Select * from Student";

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Student.class));
    }

    public Student saveStudent(Student std){
       String sql = "Insert into Student(name, clas) values(?,?)";
       int result = jdbcTemplate.update(sql,std.getName(), std.getClas());
        if(result>0){
            System.out.println("Student added successfully");
            return std;

        }else  {
            System.out.println("Student not added");
            return null;
        }
    }

    public Student UpdateStudent(Student std){
        String sql ="Update Student set clas= ? where name = ? ";
        int res = jdbcTemplate.update(sql, std.getClas(), std.getName());
        if(res>0){
            System.out.println("Student updated successfully");
            return std;
        }
        else{
            System.out.println("Student not exist or an error occurs");
            return null;
        }
    }

    public int deleteStudent(String name){
        String sql = "Delete from Student where name = ? ";
        int res = jdbcTemplate.update(sql,name);
        if(res>0){
            System.out.println("Student deleted successfully");
        }
        else{
            System.out.println("Student not exist or an error occurs");
        }
        return res;
    }
}
