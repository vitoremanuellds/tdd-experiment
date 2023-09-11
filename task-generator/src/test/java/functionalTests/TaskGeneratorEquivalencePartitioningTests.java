package functionalTests;

import com.ufcg.taskgenerator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskGeneratorEquivalencePartitioningTests {

    @Autowired
    private TaskService taskServ;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void newRepoHashmap(){this.taskRepository.deleteAll();}

    @Test
    void Should_CreateTask_When_ValidDate_Case1() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-01-2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_CreateTask_When_ValidDate_Case2() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "28-02-2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_CreateTask_When_ValidDate_Case3() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "30-06-2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_CreateTask_When_ValidDate_Case4() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "31-01-2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_NotCreateTask_When_InvalidDate_Case5() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "29-02-2025",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_NotCreateTask_When_InvalidDate_Case6() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "30-02-2024",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_NotCreateTask_When_InvalidDate_Case7() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "31-06-2024",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_CreateTask_When_ValidDate_Case8() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "29-02-2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_NotCreateTask_When_InvalidDate_Case9() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "00-06-2024",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_CreateTask_When_ValidDate_Case10() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-01-2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_CreateTask_When_ValidDate_Case11() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-12-2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_NotCreateTask_When_InvalidDate_Case12() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-00-2024",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_NotCreateTask_When_InvalidDate_Case13() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-13-2024",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_NotCreateTask_When_InvalidDate_Case14() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-13-2022",
                PRIORITY.LOW);
        // Assert
        assertThrows(Exception.class, () -> taskServ.createTask(taskDTO));
    }

    @Test
    void Should_NotCreateTask_When_InvalidDate_Case15() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-13--1",
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
                "01-13--1",
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
                "01-01-2024",
                PRIORITY.LOW);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_CreateTask_When_ValidPriority_Case3() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-01-2024",
                PRIORITY.MEDIUM);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_CreateTask_When_ValidPriority_Case4() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-01-2024",
                PRIORITY.HIGH);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }

    @Test
    void Should_CreateTask_When_ValidPriority_Case3() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(
                "test",
                "test discription",
                "01-01-2024",
                PRIORITY.NIL);
        // Test
        Task newTask = taskServ.createTask(taskDTO);
        // Assert
        assertEquals(taskDTO.getDate(), newTask.getExpirationDate().parse(dateAsString, formatter));
    }
}
