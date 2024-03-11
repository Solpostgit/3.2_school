package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private AvatarService avatarService;

    @Test
    void shouldGetFaculty() throws Exception {
        //given
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Griffindor", "Red");

        when(facultyService.get(facultyId)).thenReturn(faculty);

        //when
        ResultActions perform = mockMvc.perform(get("/faculties/{id}", facultyId));

        //then
        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void shouldCreateFaculty() throws Exception {
        //given
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Griffindor", "Red");
        Faculty savedFaculty = new Faculty("Griffindor", "Red");
        savedFaculty.setId(facultyId);

        when(facultyService.add(faculty)).thenReturn(savedFaculty);

        //when
        ResultActions perform = mockMvc.perform(post("/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));

        //then
        perform
                .andExpect(jsonPath("$.id").value(savedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(savedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(savedFaculty.getColor()))
                .andDo(print());
    }

    @Test
    void shouldUpdateFaculty() throws Exception {
        //given
        Long facultytId = 1L;
        Faculty faculty = new Faculty("Griffindor", "Red");

        when(facultyService.update(facultytId, faculty)).thenReturn(faculty);

        //when
        ResultActions perform = mockMvc.perform(put("/faculties/{id}", facultytId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));

        //then
        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }
}
