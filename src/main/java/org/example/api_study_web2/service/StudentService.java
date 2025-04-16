package org.example.api_study_web2.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import org.example.api_study_web2.data.DataMapper;
import org.example.api_study_web2.data.StudentInput;
import org.example.api_study_web2.data.StudentOutput;
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

    public StudentOutput createStudent(StudentInput studentInput) {
        logger.info("Salvando Usúario");

        var entity = DataMapper.parseObject(studentInput, Student.class);

        var student = repository.save(entity);
        return DataMapper.parseObject(student, StudentOutput.class);
    }

    public StudentOutput findById(UUID id) {
        logger.info("Procurando aluno");
        var student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado com id " + id));
        return DataMapper.parseObject(student, StudentOutput.class);
    }

    public List<StudentOutput> getAllStudents() {
        logger.info("Retornando Lista de Studentes");

        List<Student> students = repository.findAll();
        return DataMapper.parseListObjects(students, StudentOutput.class);
    }

    public StudentOutput updateStudent(UUID id, StudentInput studentInput) {
        logger.info("Atualizando aluno");

        var entity = repository.findById(id).orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado com id " + id));

        entity.setFirstName(studentInput.firstName());
        entity.setLastName(studentInput.lastName());
        entity.setGender(studentInput.gender());
        entity.setAddress(studentInput.address());

        var student = repository.save(entity);
        return DataMapper.parseObject(student, StudentOutput.class);

    }

    public void deleteStudent(UUID id) {
        logger.info("Deletando aluno");
        var entity = repository.findById(id).orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado com id " + id));
        repository.delete(entity);
    }
}
