package org.example.api_study_web2.controller;

import jakarta.validation.Valid;
import org.example.api_study_web2.data.StudentInput;
import org.example.api_study_web2.data.StudentOutput;
import org.example.api_study_web2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<StudentOutput> save(@Valid @RequestBody StudentInput studentInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(studentInput));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentOutput> update(@PathVariable UUID id, @Valid @RequestBody StudentInput studentInput) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudent(id, studentInput));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentOutput> getStudent(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<StudentOutput>> getAllStudents() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
