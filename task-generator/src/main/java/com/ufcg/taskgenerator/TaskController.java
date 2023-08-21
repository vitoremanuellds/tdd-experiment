package com.ufcg.taskgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
@RestController
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/task-generator", method = RequestMethod.POST)
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO)
    {
        Task newTask;

        try {
            newTask = taskService.createTask(taskDTO);
        } catch (Exception e) {
            return new ResponseEntity("error", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Task>(newTask, HttpStatus.OK);
    }

    @RequestMapping(value = "/task-generator/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateTask(@PathVariable("id") String id,
                                        @RequestBody TaskDTO taskDTO)
    {
        Task modifiedTask;

        try {
            modifiedTask = taskService.updateTask(id, taskDTO);
        } catch (Exception e) {
            return new ResponseEntity("error", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Task>(modifiedTask, HttpStatus.OK);
    }

    @RequestMapping(value = "/task-generator?{id}", method = RequestMethod.DELETE)
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

    @RequestMapping(value = "/task-generator{id}", method = RequestMethod.GET)
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
