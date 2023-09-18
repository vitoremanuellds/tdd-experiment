package com.ufcg.taskgenerator.functionalTests;

import com.ufcg.taskgenerator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskGeneratorDecisionTableTests {

    @Autowired
    private TaskService taskServ;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void newRepoHashmap(){this.taskRepository.deleteAll();}

    @Test
    void Should_CreateTask_Case1() throws Exception {
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
    void Should_NotCreateTask_Case2() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "30/02/2024",
                PRIORITY.NIL);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_NotCreateTask_Case3() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                null,
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_NotCreateTask_Case4() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                null,
                null,
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_NotCreateTask_Case5() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                null,
                "test discription",
                null,
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_DeleteTask_Case1() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        Task deleted = taskServ.deleteTask(newTask.getId());
        // Assert
        assertEquals(taskDTO.getTitle(), deleted.getTitle());
    }

    @Test
    void Should_NotDeleteTask_Case2() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        String invalidId = UUID.randomUUID().toString();
        // Assert
        assertThrows(Exception.class, () -> taskServ.deleteTask(invalidId));
    }

    @Test
    void Should_NotDeleteTask_Case3() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.LOW);
        // Test
        String invalidId = UUID.randomUUID().toString();
        // Assert
        assertThrows(Exception.class, () -> taskServ.deleteTask(invalidId));
    }

    @Test
    void Should_UpdateTask_Case1() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.LOW);
        TaskDTO edit = new TaskDTO(
                "edited",
                "test edited",
                "02/01/2024",
                PRIORITY.MEDIUM);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        Task edited = taskServ.updateTask(newTask.getId(), edit);
        // Assert
        assertEquals(edit.getTitle(), edited.getTitle());
    }

    @Test
    void Should_NotUpdateTask_Case2() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.LOW);
        TaskDTO edit = new TaskDTO(
                "edited",
                "test edited",
                "02/01/2024",
                PRIORITY.NIL);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(PRIORITY.LOW, newTask.getPriority());
    }

    @Test
    void Should_NotUpdateTask_Case3() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.LOW);
        TaskDTO edit = new TaskDTO(
                "edited",
                "test edited",
                "invalid",
                PRIORITY.NIL);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertThrows(Exception.class, () -> taskServ.updateTask(newTask.getId(), edit));
    }

    @Test
    void Should_NotUpdateTask_Case4() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.LOW);
        TaskDTO edit = new TaskDTO(
                null,
                null,
                "invalid",
                PRIORITY.NIL);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertThrows(Exception.class, () -> taskServ.updateTask(newTask.getId(), edit));
    }

    @Test
    void Should_NotUpdateTask_Case5() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01/01/2024",
                PRIORITY.LOW);
        TaskDTO edit = new TaskDTO(
                "edited",
                "test edited",
                "02/01/2024",
                PRIORITY.MEDIUM);
        // Test
        String invalidId = UUID.randomUUID().toString();
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertThrows(Exception.class, () -> taskServ.updateTask(invalidId, edit));
    }
}
