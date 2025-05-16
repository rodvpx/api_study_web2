package org.example.api_study_web2.service;

import org.example.api_study_web2.data.DataMapper;
import org.example.api_study_web2.data.StudentInput;
import org.example.api_study_web2.data.StudentOutput;
import org.example.api_study_web2.exception.StudentNotFoundException;
import org.example.api_study_web2.model.Student;
import org.example.api_study_web2.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private DataMapper mapper;

    @InjectMocks
    private StudentService service;

    private StudentInput input;
    private StudentOutput output;
    private Student entity;
    private UUID anyId;

    @BeforeEach
    void setUp() {
        anyId = UUID.randomUUID();
        input = new StudentInput("Mario", "De Luca", "Rua 24 - Italia", "Masculino");
        entity = new Student();
        entity.setId(anyId);
        entity.setFirstName(input.firstName());
        entity.setLastName(input.lastName());
        entity.setGender(input.gender());
        entity.setAddress(input.address());

        output = new StudentOutput(anyId, input.firstName(), input.lastName(), input.address(), input.gender());
    }

    @Test
    void testCreateStudent() {

        when(mapper.toEntity(input)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toOutput(entity)).thenReturn(output);

        StudentOutput result = service.createStudent(input);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(output);
        verify(mapper).toEntity(input);
        verify(repository).save(entity);
        verify(mapper).toOutput(entity);
    }

    @Test
    void findById() {
        when(repository.findById(anyId)).thenReturn(Optional.of(entity));
        when(mapper.toOutput(entity)).thenReturn(output);

        StudentOutput result = service.findById(anyId);

        assertThat(result).isEqualTo(output);
        verify(repository).findById(anyId);
        verify(mapper).toOutput(entity);
    }

    @Test
    void findByIdNotFound() {
        when(repository.findById(anyId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(anyId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Aluno não encontrado com id " + anyId);

        verify(repository).findById(anyId);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void getAllStudents() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(mapper.toOutput(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<StudentOutput> result = service.getAllStudents();

        assertThat(result).isEmpty();
        verify(repository).findAll();
        verify(mapper).toOutput(Collections.emptyList());
    }

    @Test
    void updateStudent() {
        StudentInput updateInput = new StudentInput("Maria", "Oliveira", "Rua B", "F");
        Student updatedEntity = new Student();
        updatedEntity.setId(anyId);
        updatedEntity.setFirstName("Maria");
        updatedEntity.setLastName("Oliveira");
        updatedEntity.setAddress("Rua B");
        updatedEntity.setGender("F");
        StudentOutput updatedOutput = new StudentOutput(anyId, "Maria", "Oliveira", "Rua B", "F");

        when(repository.findById(anyId)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(updatedEntity);
        when(mapper.toOutput(updatedEntity)).thenReturn(updatedOutput);

        StudentOutput result = service.updateStudent(anyId, updateInput);

        assertThat(result).usingRecursiveComparison().isEqualTo(updatedOutput);
        assertThat(entity.getFirstName()).isEqualTo("Maria");
        assertThat(entity.getLastName()).isEqualTo("Oliveira");
        verify(repository).findById(anyId);
        verify(repository).save(entity);
        verify(mapper).toOutput(updatedEntity);
    }

    @Test
    void deleteStudent() {
        when(repository.findById(anyId)).thenReturn(Optional.of(entity));

        service.deleteStudent(anyId);

        verify(repository).findById(anyId);
        verify(repository).delete(entity);
    }

    @Test
    void deleteStudentNotFound() {
        when(repository.findById(anyId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteStudent(anyId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Aluno não encontrado com id " + anyId);

        verify(repository).findById(anyId);
        verify(repository, never()).delete(any());
    }
}
