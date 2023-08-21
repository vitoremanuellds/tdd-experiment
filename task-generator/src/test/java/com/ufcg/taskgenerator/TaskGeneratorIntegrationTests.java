package com.ufcg.taskgenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.util.diff.DeleteDelta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskGeneratorIntegrationTests
{

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    TaskService taskService;
    @Autowired
    TaskRepository taskRepository;

    @BeforeEach
    void newRepoHashmap(){this.taskRepository.deleteAll();}

    @Test
    void TaskController_Should_CreateTask_When_GivenTaskInfo() throws Exception {
        // Arrange
        TaskDTO task = new TaskDTO(
                "Lavar Roupa",
                "Lavar roupa do cesto vermelho",
                "25/08/2023",
                PRIORITY.MEDIUM);

        // Test
        String strTask = mockMvc.perform(post("/api/task-generator")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Map<String, String> mapTask = objectMapper.readValue(strTask, HashMap.class);
        Task testTask = new Task();

        testTask.setId(mapTask.get("id"));
        testTask.setTitle(mapTask.get("title"));
        testTask.setDescription(mapTask.get("description"));
        testTask.setExpirationDate(mapTask.get("expirationDate"));
        testTask.setPriority(
                Arrays.asList(PRIORITY.values()).stream().filter(p ->
                        p.toString().equals(mapTask.get("priority"))).findFirst().get()
        );

        // Assert
        assertEquals(task.getTitle(), testTask.getTitle());
        assertEquals(task.getDescription(), testTask.getDescription());
        assertEquals("25/08/2023", testTask.getExpirationDate());
        assertEquals(task.getPriority(), testTask.getPriority());
    }

    @Test
    void TaskController_Should_ModifyTask_When_GivenTaskNewInfo() throws Exception
    {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "Lavar Carro",
                "Lavar Carro",
                "24/08/2023",
                PRIORITY.LOW);

        com.ufcg.taskgenerator.Task task =
                new com.ufcg.taskgenerator.Task(
                "id",
                "Lavar Roupa",
                "Lavar roupa do cesto vermelho",
                "25/08/2023",
                PRIORITY.MEDIUM);

        taskService.addTask(task);

        // Test
        String modTask = mockMvc.perform(patch(String.format("/api/task-generator/%s", "id"))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Assert
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getExpirationDate(),"24/08/2023");
        assertEquals(task.getPriority(), taskDTO.getPriority());
    }

    @Test
    void TaskController_Should_DeleteTask_When_GivenTaskId() throws Exception
    {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "Lavar Carro",
                "Lavar Carro",
                "24/08/2023",
                PRIORITY.LOW);

        // Test
        String strTask = mockMvc.perform(delete("/api/task-generator/%s", "id")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Map<String, String> mapTask = objectMapper.readValue(strTask, HashMap.class);
        Task testTask = new Task();

        testTask.setId(mapTask.get("id"));
        testTask.setTitle(mapTask.get("title"));
        testTask.setDescription(mapTask.get("description"));
        testTask.setExpirationDate(mapTask.get("expirationDate"));
        testTask.setPriority(
                Arrays.asList(PRIORITY.values()).stream().filter(p ->
                        p.toString().equals(mapTask.get("priority"))).findFirst().get()
        );

        mockMvc.perform(delete("/api/task-generator?{id}", testTask.getId()))
                        .andExpect(status().isOk());

        // Assert
        assertTrue(taskService.getTasks().contains(testTask));
    }

    @Test
    void TaskController_Should_ReturnAllTasks_When_GivenTaskId() throws Exception
    {
        // Arrange
        List<Task> tasks = taskService.getTasks();

        // Test
        String strTask = mockMvc.perform(get("/api/task-generator"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<com.ufcg.taskgenerator.Task> testTask = (List<com.ufcg.taskgenerator.Task>)objectMapper.readValue(strTask, List.class);

        // Assert
        assertTrue(testTask instanceof List);
        assertEquals(tasks, testTask);

    }
}
