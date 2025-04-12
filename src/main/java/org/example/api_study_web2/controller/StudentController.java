package org.example.api_study_web2.controller;

import org.example.api_study_web2.dto.StudentDTO;
import org.example.api_study_web2.model.Student;
import org.example.api_study_web2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO dto) {
        // Chama o serviço para salvar o estudante e retornar um DTO
        StudentDTO responseDTO = studentService.saveStudent(dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
