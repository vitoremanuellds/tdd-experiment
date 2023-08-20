package com.ufcg.taskgenerator;

import org.assertj.core.util.diff.DeleteDelta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
import org.springframework.scheduling.config.Task;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskGeneratorIntegrationTests
{

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void TaskController_Should_CreateTask_When_GivenTaskInfo() throws Exception
    {
        // Arrange
        TaskDTO task = new TaskDTO(
                "Lavar Roupa",
                "Lavar roupa do cesto vermelho",
                "25/08/2023",
                PRIORITY.MEDIUM);

        // Test
        Task testTask = mockMvc.perform(post("/api/task-generator")
                .contentType("application/jason")
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk());

        // Assert
        assertEquals(task.getTitle(), testTask.getTitle);
        assertEquals(task.getDescription, testTask.getDescription);
        assertEquals(task.getExpirationDate, testTask.getExpirationDate);
        assertEquals(task.getPriority, testTask.getPriority);
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

        Task task = Task(
                "id",
                "Lavar Roupa",
                "Lavar roupa do cesto vermelho",
                "25/08/2023",
                PRIORITY.MEDIUM);

        // Test
        Task modTask = mockMvc.perform(patch("/api/task-generator?{id}", "id")
                        .contentType("application/jason")
                        .content(objectMapper.writeValueAsString(taskDTO)))
                        .andExpect(status().isOk());

        // Assert
        assertEquals(task.getTitle, "Lavar Carro");
        assertEquals(task.getDescription, "Lavar Carro");
        assertEquals(task.getExpirationDate, "24/08/2023");
        assertEquals(task.getPriority, PRIORITY.LOW);
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
        Task task = mockMvc.perform(post("/api/task-generator")
                        .contentType("application/jason")
                        .content(objectMapper.writeValueAsString(taskDTO)))
                        .andExpect(status().isOk());

        mockMvc.perform(delete("/api/task-generator?{id}", task.getId))
                        .andExpect(status().isOk());

        // Assert
        assertNull(task);
    }

    void TaskController_Should_ReturnAllTasks_When_GivenTaskId() throws Exception
    {
        // Arrange
        String id = "id";

        // Test
        mockMvc.perform(get("/api/task-generator?{id}", id))
                .andExpect(status().isOk());
    }
}
