package com.Final.Ending;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    StudentService StdService;

    @Test
    void shouldReturnAllStudent() throws Exception {

        List<Student> list = List.of(new Student("John", "30"));

        when(StdService.getAllStudents()).thenReturn(list);

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students[0].name").value("John"))
                .andExpect(jsonPath("$.students[0].clas").value("30"))
                .andExpect(jsonPath("$.message").value("Students retrieved successfully"));
    }

    @Test
    void shouldReturnEmptyStudentListWhenNoStudentsExist() throws Exception {

        when(StdService.getAllStudents()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students").isArray())
                .andExpect(jsonPath("$.students").isEmpty())
                .andExpect(jsonPath("$.message").value("Students retrieved successfully"));
    }

    @Test
    void shouldAddStudentWhenServiceSavesStudent() throws Exception {

        when(StdService.saveStudent(any(Student.class))).thenReturn(new Student("Sara", "12B"));

        mockMvc.perform(post("/api/v1/addStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Sara\",\"clas\":\"12B\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Student added successfully"))
                .andExpect(jsonPath("$.student.name").value("Sara"))
                .andExpect(jsonPath("$.student.clas").value("12B"));
    }

    @Test
    void shouldReturnBadRequestWhenServiceDoesNotSaveStudent() throws Exception {

        when(StdService.saveStudent(any(Student.class))).thenReturn(null);

        mockMvc.perform(post("/api/v1/addStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Sara\",\"clas\":\"12B\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Student not added"));
    }

    @Test
    void shouldUpdateStudentWhenServiceUpdatesStudent() throws Exception {

        when(StdService.UpdateStudent(any(Student.class))).thenReturn(new Student("Mona", "11A"));

        mockMvc.perform(put("/api/v1/updateStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mona\",\"clas\":\"11A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Student updated successfully"))
                .andExpect(jsonPath("$.student.name").value("Mona"))
                .andExpect(jsonPath("$.student.clas").value("11A"));
    }

    @Test
    void shouldReturnBadRequestWhenServiceDoesNotUpdateStudent() throws Exception {

        when(StdService.UpdateStudent(any(Student.class))).thenReturn(null);

        mockMvc.perform(put("/api/v1/updateStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mona\",\"clas\":\"11A\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Student not exist or an error occurs"));
    }

    @Test
    void shouldDeleteStudentWhenServiceReturnsPositiveResult() throws Exception {

        when(StdService.deleteStudent("John")).thenReturn(1);

        mockMvc.perform(delete("/api/v1/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Student deleted successfully"));
    }

    @Test
    void shouldReturnBadRequestWhenServiceCannotDeleteStudent() throws Exception {

        when(StdService.deleteStudent("Unknown")).thenReturn(0);

        mockMvc.perform(delete("/api/v1/Unknown"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Student not deleted"));
    }
}