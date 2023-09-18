package com.ufcg.taskgenerator.JUnit5Tests;

import com.ufcg.taskgenerator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
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
public class TaskGeneratorEquivalencePartitioningTests {

    @Autowired
    private TaskService taskServ;

    @Autowired
    private TaskRepository taskRepository;

    DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    @BeforeEach
    void newRepoHashmap(){this.taskRepository.deleteAll();}

    @ParameterizedTest
    @ValueSource(strings = {
            "01/01/2024", "28/02/2024", "30/06/2024", "31/01/2024",
            "29/02/2024", "01/01/2024", "01/12/2024"})
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
            "29/02/2025", "30/02/2024", "31/06/2024", "00/06/2024",
            "01/00/2024", "01/13/2024", "01/13/2022"})
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

    @Test
    void Should_NotCreateTask_When_NullPriority_Case1() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/13/-1",
                null);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_CreateTask_When_ValidPriority_Case2() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getTitle(), newTask.getTitle());
    }

    @Test
    void Should_CreateTask_When_ValidPriority_Case3() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.MEDIUM);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getTitle(), newTask.getTitle());
    }

    @Test
    void Should_CreateTask_When_ValidPriority_Case4() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.HIGH);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getTitle(), newTask.getTitle());
    }

    @Test
    void Should_CreateTask_When_ValidPriority_Case5() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.NIL);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getTitle(), newTask.getTitle());
        assertEquals(PRIORITY.LOW, newTask.getPriority());
    }
}
