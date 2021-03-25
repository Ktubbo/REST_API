package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class TaskControllerTest {

    @Autowired
    TaskController controller;
    @Autowired
    TaskMapper mapper;

    @Test
    void createTaskAndGetTasks() {
        //Given
        Task task1 = new Task("New task 1","New test content");
        Task task2 = new Task("New task 2","New test content");
        //When
        controller.createTask(mapper.mapToTaskDto(task1));
        controller.createTask(mapper.mapToTaskDto(task2));
        //Then
        List<Task> resultList = mapper.mapToTaskList(controller.getTasks());
        assertEquals(2,resultList.size());
    }

    @Test
    void deleteTask() {
        //Given
        Task task1 = new Task("New task 1","New test content");
        //When
        controller.createTask(mapper.mapToTaskDto(task1));
        long taskId = controller.getTasks().get(0).getId();
        controller.deleteTask(taskId);
        //Then
        List<Task> resultList = mapper.mapToTaskList(controller.getTasks());
        assertEquals(0,resultList.size());
    }

    @Test
    void updateTask() {
        //Given
        Task task1 = new Task("New task 1","New test content");
        //When
        controller.createTask(mapper.mapToTaskDto(task1));
        long taskId = controller.getTasks().get(0).getId();
        Task updatedTask = new Task(taskId,"Updated task", "Updated content");
        controller.updateTask(mapper.mapToTaskDto(updatedTask));
        //Then
        List<Task> resultList = mapper.mapToTaskList(controller.getTasks());
        assertEquals("Updated task",resultList.get(0).getTitle());
        assertEquals("Updated content",resultList.get(0).getContent());
    }

    @Test
    void getTask() {
        //Given
        Task task1 = new Task("New task 1","New test content");
        //When
        controller.createTask(mapper.mapToTaskDto(task1));
        long taskId = controller.getTasks().get(0).getId();
        //Then

        try{
            Task resultTask = mapper.mapToTask(controller.getTask(taskId));
            assertEquals("New task 1", resultTask.getTitle());
            assertEquals("New test content", resultTask.getContent());
        } catch (Exception e) {
            fail();
        }
    }

}