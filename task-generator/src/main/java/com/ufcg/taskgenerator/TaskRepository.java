package com.ufcg.taskgenerator;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TaskRepository {

    private HashMap<String, Task> tasks;

    public TaskRepository() {this.tasks = new HashMap();}

    public void createTask(Task task)
    {
        this.tasks.put(task.id, task);
    }

    public Task updateTask(String id, Task auxTask)
    {
        tasks.forEach((x,y) -> {
           if(x.equals(id)){
               if(!auxTask.getTitle().isEmpty()){y.setTitle(auxTask.getTitle());}
               if(!auxTask.getDescription().isEmpty()){y.setDescription(auxTask.getDescription());}
               if(!auxTask.getExpirationDate().isEmpty()){y.setExpirationDate(auxTask.getExpirationDate());}
               if(auxTask.getPriority() != PRIORITY.NIL){y.setPriority(auxTask.getPriority());}
           }
        });
        return tasks.get(id);
    }

    public Task deleteTask(String id) {
        Task deleted = null;
        if (tasks.containsKey(id)){
            deleted = tasks.get(id);
            tasks.remove(id);
        }

        return deleted;
    }

    public List<Task> getTasks()
    {
        return new ArrayList<>(this.tasks.values());
    }

    public void deleteAll()
    {
        this.tasks = new HashMap<>();
    }

    public Task getTask(String id) {
        return this.tasks.get(id);
    }
}
