package com.ufcg.taskgenerator.functionalTests;

import com.ufcg.taskgenerator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

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

    @Test
    void Should_CreateTask_When_ValidDateForCommonYear_LowestBoundary() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/02/2025",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(LocalDate.parse(taskDTO.getDate(), formatter), newTask.getExpirationDate());
    }

    @Test
    void Should_CreateTask_When_ValidDateForCommonYear() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "15/02/2025",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(LocalDate.parse(taskDTO.getDate(), formatter), newTask.getExpirationDate());
    }

    @Test
    void Should_CreateTask_When_ValidDateForCommonYear_HighestBoundary_30DayMonth() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "30/04/2025",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(LocalDate.parse(taskDTO.getDate(), formatter), newTask.getExpirationDate());
    }

    @Test
    void Should_ThrowExcepton_When_InvalidDateForCommonYear_HighestBoundary_30DayMonth() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "31/04/2025",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_CreateTask_When_ValidDateForCommonYear_InBoundary_31DayMonth() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "30/01/2025",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(LocalDate.parse(taskDTO.getDate(), formatter), newTask.getExpirationDate());
    }

    @Test
    void Should_CreateTask_When_ValidDateForCommonYear_HighestBoundary_31DayMonth() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "31/01/2025",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(LocalDate.parse(taskDTO.getDate(), formatter), newTask.getExpirationDate());
    }

    @Test
    void Should_CreateTask_When_ValidDateForCommonYear_HighestBoundary_February() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "28/02/2025",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(LocalDate.parse(taskDTO.getDate(), formatter), newTask.getExpirationDate());
    }

    @Test
    void Should_NotCreateTask_When_InvalidDateForCommonYear_February29() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "29/02/2025",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_NotCreateTask_When_InvalidDateForCommonYear_February30() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "30/02/2025",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_CreateTask_When_ValidDateForLeapYear_February28() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "28/02/2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(LocalDate.parse(taskDTO.getDate(), formatter), newTask.getExpirationDate());
    }

    @Test
    void Should_CreateTask_When_ValidDateForLeapYear_HighestBoundary_February() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "29/02/2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(LocalDate.parse(taskDTO.getDate(), formatter), newTask.getExpirationDate());
    }

    @Test
    void Should_NotCreateTask_When_invalidDateForLeapYear_February30() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "30/02/2025",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }
}
