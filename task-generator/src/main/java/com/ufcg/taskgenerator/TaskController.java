package com.ufcg.taskgenerator;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/task-generator", method = RquestMethod.POST)
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, UriComponentBuilder ucBuilder)
    {
        Task newTask;

        try {
            newTask = taskService.createTask(taskDTO);
        } catch (Exception e) {
            return new ResponseEntity("error", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Task>(newTask, HttpStatus.OK);
    }

    @RequestMapping(value = "/task-generator?{id}", method = RquestMethod.PATCH)
    public ResponseEntity<?> updateTask(@PathVariable("id") String id,
                                        @RequestBody TaskDTO taskDTO,
                                        UriComponentBuilder ucBuilder)
    {
        Task modifiedTask;

        try {
            modifiedTask = taskService.updateTask(id, taskDTO);
        } catch (Exception e) {
            return new ResponseEntity("error", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Task>(modifiedTask, HttpStatus.OK);
    }

    @RequestMapping(value = "/task-generator?{id}", method = RquestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable("id") String id)
    {
        Task deletedTask;

        try {
            deletedTask = taskService.deleteTask(id);
        } catch (Exception e) {
            return new ResponseEntity("error", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Task>(deletedTask, HttpStatus.OK);
    }

    @RequestMapping(value = "/task-generator{id}", method = RquestMethod.POST)
    public ResponseEntity<?> getTasks(@PathVariable("id") String id)
    {
        List<Task> tasks;

        try {
            tasks = taskService.getTasks();
        } catch (Exception e) {
            return new ResponseEntity("error", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }
}
