package com.Final.Ending;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
    @RequestMapping("api/v1")
    public class StudentController {
    @Autowired
    StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public ResponseEntity<Map<String, Object>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        Map<String, Object> response = new HashMap<>();
        response.put("students", students);
        response.put("message", "Students retrieved successfully");
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Map<String, Object>> t(@RequestBody Student std) {
        int res = studentService.saveStudent(std) != null ? 1 : 0;
        Map<String, Object> response = new HashMap<>();
        if (res > 0) {
            response.put("message", "Student added successfully");
            response.put("student", std);
            return ResponseEntity.status(201).body(response);
        } else {
            response.put("message", "Student not added");
            return ResponseEntity.status(400).body(response);
        }
    }

    @PutMapping("/updateStudent")
    public ResponseEntity<Map<String, Object>> updateStudent(@RequestBody Student std) {
        int res = studentService.UpdateStudent(std) != null ? 1 : 0;
        Map<String, Object> response = new HashMap<>();
        if (res > 0) {
            response.put("message", "Student updated successfully");
            response.put("student", std);
            return ResponseEntity.status(200).body(response);
        } else {
            response.put("message", "Student not exist or an error occurs");
            return ResponseEntity.status(400).body(response);
        }
    }


    @DeleteMapping("/{name}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable String name) {
        int result = studentService.deleteStudent(name);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("message", "Student deleted successfully");
            return ResponseEntity.status(200).body(response);
        } else {
            response.put("message", "Student not deleted");
            return ResponseEntity.status(400).body(response);
        }
        //return empService.deleteEmployee(id);
    }
}

