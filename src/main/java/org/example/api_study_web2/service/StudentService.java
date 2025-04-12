package org.example.api_study_web2.service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.example.api_study_web2.dto.StudentDTO;
import org.example.api_study_web2.exception.StudentNotFoundException;
import org.example.api_study_web2.model.Student;
import org.example.api_study_web2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;
    private Logger logger = Logger.getLogger(StudentService.class.getName());

    public StudentDTO saveStudent(StudentDTO dto) {
        // Converte DTO para Entidade
        Student student = new Student();
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setAddress(dto.address());
        student.setGender(dto.gender());

        // Salva a entidade
        repository.save(student);

        // Converte de volta para DTO e retorna
        return new StudentDTO(student.getFirstName(), student.getLastName(),
                student.getAddress(), student.getGender());
    }

    public Student getStudentById(UUID id) {
        logger.info("Retornando um aluno");
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado com ID: " + id));
    }

    public List<Student> getAllStudents() {
        logger.info("Retornando todos os aluno");
        return repository.findAll();
    }

    public Student updateStudent(Student student) {
        logger.info("Salvando um aluno");
        Student entity = repository.findById(student.getId()).orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado com ID: " + student.getId()));
        entity.setFirstName(student.getFirstName());
        entity.setLastName(student.getLastName());
        entity.setGender(student.getGender());
        entity.setAddress(student.getAddress());
        return repository.save(entity);
    }

    public void deleteStudent(UUID id) {
        logger.info("Deletando um aluno");
        Student entity = repository.findById(id).orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado com ID: " + id));
        repository.delete(entity);
    }
}
