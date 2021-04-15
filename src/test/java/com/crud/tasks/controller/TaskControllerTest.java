package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @MockBean
    private TaskController controller;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTasks() throws Exception{
        //Given
        TaskDto task1 = new TaskDto(1L,"New task 1","New test content");
        TaskDto task2 = new TaskDto(2L,"New task 2","New test content");
        List<TaskDto> taskList = List.of(task1,task2);
        when(controller.getTasks()).thenReturn(taskList);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void createTask() throws Exception{
        //Given
        TaskDto task = new TaskDto(1L, "New task 1", "New test content");
        TaskDto createdTask = new TaskDto(1L,"This task was created","New test content");
        when(controller.createTask(any(TaskDto.class))).thenReturn(createdTask);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",Matchers.is("This task was created")));
    }

    @Test
    void deleteTask() throws Exception{
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/11")
                        .characterEncoding("UTF-8"))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateTask() throws Exception{
        //Given
        TaskDto task = new TaskDto(1L, "New task 1", "New test content");
        TaskDto updatedTask = new TaskDto(1L,"This task was updated","New test content");
        when(controller.updateTask(any(TaskDto.class))).thenReturn(updatedTask);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",Matchers.is("This task was updated")));
    }

    @Test
    void getTask() throws Exception {
        //Given
        TaskDto task = new TaskDto(1L,"New task","New test content");
        when(controller.getTask(any(Long.class))).thenReturn(task);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("New task")));
    }

}