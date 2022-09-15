package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetEmptyTasksList() throws Exception {
        //Given
        when(dbService.getAllTasks()).thenReturn(List.of());
        when(taskMapper.mapToTaskDtoList(List.of())).thenReturn(List.of());
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",Matchers.hasSize(0)));
    }

    @Test
    void shouldGetTasksList() throws Exception {
        //Given
        Task task = new Task(1L,"title","content");
        List<Task> taskList = List.of(task);

        TaskDto taskDto = new TaskDto(1L,"title","content");
        List<TaskDto> taskDtoList = List.of(taskDto);

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title",Matchers.is("title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content",Matchers.is("content")));
    }

    @Test
    void shouldGetTask() throws Exception {
        //Given
        Task task = new Task(1L,"title","content");
        TaskDto taskDto = new TaskDto(1L,"title","content");

        when(dbService.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",Matchers.is("title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",Matchers.is("content")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(1L,"title","content");
        TaskDto taskDto = new TaskDto(1L,"title","content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(1L,"updatedTitle","updatedContent");
        TaskDto taskDto = new TaskDto(1L,"updatedTitle","updatedContent");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",Matchers.is("updatedTitle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",Matchers.is("updatedContent")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L,"updatedTitle","updatedContent");
        dbService.saveTask(task);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}