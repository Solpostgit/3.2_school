package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StudentControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    private void clearDatabase() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateStudent() {
        //given
        Student student = new Student("Ivan", 25);

        //when
//        restTemplate.postForObject(
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/students",
                student,
                Student.class
        );

        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getName(), student.getName());
        org.assertj.core.api.Assertions.assertThat(actualStudent.getAge()).isEqualTo(student.getAge());
    }

    @Test
    void shouldUpdateStudent() {
        //given
        Student student = new Student("Ivan", 25);
        student = studentRepository.save(student);

        Student studentForUpdate = new Student("newName", 27);

        //when
        HttpEntity<Student> entity = new HttpEntity<Student>(studentForUpdate);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/students/" + student.getId(),
                HttpMethod.PUT,
                entity,
                Student.class
        );
        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getId(), student.getId());
        assertEquals(actualStudent.getName(), studentForUpdate.getName());
        assertEquals(actualStudent.getAge(), studentForUpdate.getAge());
    }

    @Test
    void shouldGetStudent() {
        //given
        Student student = new Student("Ivan", 25);
        student = studentRepository.save(student);

        //when
        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/students/" + student.getId(),
                Student.class
        );
        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getId(), student.getId());
        assertEquals(actualStudent.getName(), student.getName());
        assertEquals(actualStudent.getAge(), student.getAge());
    }

    @Test
    void shouldDeleteStudent() {
        //given
        Student student = new Student("Ivan", 25);
        student = studentRepository.save(student);

        //when
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/students/" + student.getId(),
                HttpMethod.DELETE,
                null,
                Student.class
        );

        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        org.assertj.core.api.Assertions.assertThat(studentRepository.findById(student.getId())).isNotPresent();
    }
}
