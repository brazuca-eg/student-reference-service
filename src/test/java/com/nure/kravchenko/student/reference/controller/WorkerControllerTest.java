package com.nure.kravchenko.student.reference.controller;

import com.nure.kravchenko.student.reference.AbstractTest;
import com.nure.kravchenko.student.reference.dto.FacultyDto;
import com.nure.kravchenko.student.reference.dto.WorkerDto;
import com.nure.kravchenko.student.reference.service.RequestService;
import com.nure.kravchenko.student.reference.service.StudentService;
import com.nure.kravchenko.student.reference.service.WorkerService;
import com.nure.kravchenko.student.reference.service.s3.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkerControllerTest extends AbstractTest {

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final String FATHERHOOD = "Fatherhood";

    private static final String EMAIL = "email_em@nure.ua";

    private static final boolean ADMIN = false;

    private static final boolean APPROVED = true;

    private static final String JOB_TITLE = "Job title";

    private static final Character GENDER = 'M';

    private static final String SHORT_NAME = "Short Name";

    private static final Long ID = 11L;

    @Mock
    private WorkerService workerService;

    @Mock
    private StudentService studentService;
    @Mock
    private RequestService requestService;

    @Mock
    private StorageService storageService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        WorkerController workerController = new WorkerController(studentService, requestService, workerService, storageService);
        mockMvc = MockMvcBuilders.standaloneSetup(workerController).build();
    }

    @Test
    void getWorkerById() throws Exception {
        //GIVEN
        WorkerDto workerDto = WorkerDto.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .fatherhood(FATHERHOOD)
                .email(EMAIL)
                .isAdmin(ADMIN)
                .approved(APPROVED)
                .jobTitle(JOB_TITLE)
                .gender(GENDER)
                .build();

        when(workerService.getWorkerDtoById(anyLong())).thenReturn(workerDto);

        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/workers/" + ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        //THEN
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(200, status);
        WorkerDto actual = super.mapFromJson(content, WorkerDto.class);
        assertEquals(workerDto, actual);
        verify(workerService, times(1)).getWorkerDtoById(anyLong());
    }

    @Test
    void getWorkerFaculty() throws Exception {
        //GIVEN
        FacultyDto facultyDto = FacultyDto.builder()
                .id(ID)
                .name(NAME)
                .shortName(SHORT_NAME)
                .build();

        when(workerService.getWorkerFaculty(anyLong())).thenReturn(facultyDto);

        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/workers/" + ID + "/faculty")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        //THEN
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(200, status);
        FacultyDto actual = super.mapFromJson(content, FacultyDto.class);
        assertEquals(facultyDto, actual);
        verify(workerService, times(1)).getWorkerFaculty(anyLong());
    }
}