package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class FacultyServiceImpl implements FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    private final FacultyRepository facultyRepository;

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("add method was invoked");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        logger.info("get method was invoked");
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        logger.info("update method was invoked");
        return facultyRepository.findById(id).map(facultyFromDb -> {
        facultyFromDb.setName(faculty.getName());
        facultyFromDb.setColor(faculty.getColor());
        return facultyRepository.save(facultyFromDb);
    }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        logger.info("delete method was invoked");
        facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> getByColor(String color) {
        logger.info("getByColor method was invoked");
        return facultyRepository.findAll()
                .stream()
                .filter(it -> it.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public List<Faculty> getByNameOrColorIgnoreCase(String name, String color) {
        logger.info("getByNameOrColorIgnoreCase method was invoked");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public List<Student> getStudents(Long id) {
        logger.info("getStudents method was invoked");
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElse(null);
    }
    @Override
    public String getLongestName() {
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("Нет данных");

    }
}
