package org.example.api_study_web2;

import org.example.api_study_web2.model.Student;
import org.example.api_study_web2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApiStudyWeb2Application {

    @Autowired
    private static StudentRepository studentRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApiStudyWeb2Application.class, args);
        addInitialData(context);
    }

    public static void addInitialData(ConfigurableApplicationContext context) {
        // Obtendo o repositório do Spring context
        studentRepository = context.getBean(StudentRepository.class);

        // Verifica se já existem alunos no banco
        if (studentRepository.count() == 0) {
            // Adiciona alunos iniciais ao banco de dados
            Student student1 = new Student("Mario", "De Luca", "Rua 24 - Italia", "Masculino");
            Student student2 = new Student("Mary", "Jame", "Times Square - Nova York", "Feminino");
            Student student3 = new Student("Miranha", "Piter", "456 Oak Nova Jersey", "Maculino");

            studentRepository.save(student1);
            studentRepository.save(student2);
            studentRepository.save(student3);

            System.out.println("Dados iniciais inseridos com sucesso!");
        } else {
            System.out.println("Dados já existem no banco, não foi necessário inserir.");
        }
    }

}
