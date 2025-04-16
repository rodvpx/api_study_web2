package org.example.api_study_web2.data;

import org.example.api_study_web2.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DataMapper {

    DataMapper INSTANCE = Mappers.getMapper(DataMapper.class);

    Student toEntity(StudentInput input);

    StudentOutput toOutput(Student student);

    List<StudentOutput> toOutput(List<Student> students);

}