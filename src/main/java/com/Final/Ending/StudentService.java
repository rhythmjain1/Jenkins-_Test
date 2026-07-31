package com.Final.Ending;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
        @Autowired
        private final StudentRepository StudentRepositor;

    public StudentService(StudentRepository StudentRepositor) {
        this.StudentRepositor = StudentRepositor;
    }
        public  List<Student> getAllStudents() {
            return StudentRepositor.getAllStudents();
        }

        public Student saveStudent(Student std){
            return StudentRepositor.saveStudent(std);
        }
        public Student UpdateStudent(Student std){
            return StudentRepositor.UpdateStudent(std);
        }

        public int deleteStudent(String name){
            return StudentRepositor.deleteStudent(name);
        }
}

