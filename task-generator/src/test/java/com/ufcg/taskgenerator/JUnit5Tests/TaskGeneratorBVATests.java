package com.ufcg.taskgenerator.JUnit5Tests;

import com.ufcg.taskgenerator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskGeneratorBVATests {

    @Autowired
    private TaskService taskServ;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void newRepoHashmap(){this.taskRepository.deleteAll();}

    DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    @ParameterizedTest
    @ValueSource(strings = {
            "01/02/2025", "15/02/2025", "30/04/2025", "30/01/2025",
            "31/01/2025", "28/02/2025", "28/02/2024", "29/02/2024"})
    void Should_CreateTask_When_ValidDate(String date) throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                date,
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(LocalDate.parse(taskDTO.getDate(), formatter), newTask.getExpirationDate());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "31/04/2025", "29/02/2025", "30/02/2025", "30/02/2025"})
    void Should_NotCreateTask_When_InvalidDate(String date) throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                date,
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }
}
