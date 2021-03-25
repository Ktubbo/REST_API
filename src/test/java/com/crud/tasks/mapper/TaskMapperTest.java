package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class TaskMapperTest {

    @Autowired
    TaskMapper taskMapper;

    @Test
    void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L,"test","test content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(task.getId(),1L);
        assertEquals(task.getTitle(),"test");
        assertEquals(task.getContent(),"test content");
    }

    @Test
    void mapToTaskDto() {
        //Given
        Task task = new Task(1L,"test","test content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(taskDto.getId(),1L);
        assertEquals(taskDto.getContent(),"test content");
        assertEquals(taskDto.getTitle(),"test");
    }

    @Test
    void mapToTaskDtoList() {
        //Given
        Task task1 = new Task(1L,"test 1","test content");
        Task task2 = new Task(2L,"test 2","test content");

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(List.of(task1,task2));

        //Then
        assertEquals(taskDtoList.size(),2);

    }

    @Test
    void mapToTaskList() {
        //Given
        TaskDto taskDto1 = new TaskDto(1L,"test 1","test content");
        TaskDto taskDto2 = new TaskDto(2L,"test 2","test content");

        //When
        List<Task> taskList = taskMapper.mapToTaskList(List.of(taskDto1,taskDto2));

        //Then
        assertEquals(taskList.size(),2);
    }
}