package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskController {

    private final DBService dbService;
    private final TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<TaskDto> getTasks() {
        List<Task> tasks = dbService.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        dbService.deleteTask(taskId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = dbService.saveTask(task);
        return taskMapper.mapToTaskDto(savedTask);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(
                dbService.getTaskByID(taskId).orElseThrow(TaskNotFoundException::new)
        );
    }
    @RequestMapping(method = RequestMethod.POST, value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        return taskMapper.mapToTaskDto(dbService.saveTask(task));
    }
}