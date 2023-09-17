package com.ufcg.taskgenerator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TaskService
{
    @Autowired
    private TaskRepository taskRepository;

    DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public Task createTask(TaskDTO taskDTO) throws Exception {

        if(taskDTO.getTitle().isEmpty()){throw new Exception();}

        Task newTask = new Task(
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                LocalDate.parse(taskDTO.getDate(), formatter),
                taskDTO.getPriority());

        taskRepository.createTask(newTask);
        return newTask;
    }

    public Task updateTask(String id, TaskDTO taskDTO) throws Exception {
        LocalDate date = LocalDate.parse(taskDTO.getDate(), formatter);
        Task auxTask = new Task(taskDTO.getTitle(), taskDTO.getDescription(), date, taskDTO.getPriority());
        if(this.taskRepository.getTask(id) == null){throw new Exception();}
        return taskRepository.updateTask(id, auxTask);
    }

    public Task deleteTask(String id) throws Exception {

        Task deleted = taskRepository.deleteTask(id);

        if(deleted == null){throw new Exception();}

        return deleted;
    }

    public List<Task> getTasks() throws Exception {
        return taskRepository.getTasks();
    }

    public void addTask(Task task) {
        taskRepository.createTask(task);
    }
}
