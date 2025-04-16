package org.example.api_study_web2.controller;

import org.example.api_study_web2.data.StudentInput;
import org.example.api_study_web2.data.StudentOutput;
import org.example.api_study_web2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<StudentOutput> save(@RequestBody StudentInput studentInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(studentInput));
    }
}
