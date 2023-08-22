package com.ufcg.taskgenerator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TaskService
{
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(TaskDTO taskDTO) throws Exception {
        return new Task(taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getDate(), taskDTO.getPriority());
    }

    public Task updateTask(String id, TaskDTO taskDTO) throws Exception {
        Task auxTask = new Task(taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getDate(), taskDTO.getPriority());
        return taskRepository.updateTask(id, auxTask);
    }

    public Task deleteTask(String id) throws Exception {
        return taskRepository.deleteTask(id);
    }

    public List<Task> getTasks() throws Exception {
        return taskRepository.getTasks();
    }

    public void addTask(Task task) {
        taskRepository.createTask(task);
    }
}
