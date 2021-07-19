package com.courier_service.courier_service;

import com.courier_service.courier_service.exception.ItemAlreadyExistException;
import com.courier_service.courier_service.model.Task;
import com.courier_service.courier_service.repo.TaskRepository;
import com.courier_service.courier_service.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CourierServiceApplicationTests {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    final int orderNumberOne = 12;
    final int orderNumberTwo = 34;
    final int orderNumberThree = 56;

    @BeforeEach
    public void setUp() {
        Task taskOne = new Task();
        taskOne.setNumberOfOrder(orderNumberOne);
        taskOne.setDateOfAddingTask(new Date());
        Task taskTwo = new Task();
        taskTwo.setNumberOfOrder(orderNumberTwo);
        taskTwo.setDateOfAddingTask(new Date());
        taskRepository.save(taskOne);
        taskRepository.save(taskTwo);
    }

    @AfterEach
    public void tearDown() {
        taskRepository.deleteAll();
        taskRepository.flush();
    }

    @Test
    void shouldReturnTwoElements() {
        Assert.isTrue(taskService.getTasks(null).size() == 2);
    }

    @Test
    void shouldReturnElementWithParam() {
        Assert.isTrue(taskService.getTasks(orderNumberOne).size() == 1);
        Assert.isTrue(taskService.getTasks(orderNumberOne).get(0).getNumberOfOrder() == orderNumberOne);
    }

    @Test
    void shouldAddTaskWithTaskService() {
        Task taskThree = new Task();
        taskThree.setNumberOfOrder(orderNumberThree);
        taskThree.setDateOfAddingTask(new Date());
        taskService.save(taskThree);

        Assert.isTrue(taskRepository.findTaskByOrderNumber(orderNumberThree).getNumberOfOrder()
                == orderNumberThree);
    }

    @Test
    void shouldCatchExceptionWithRetryOrderNumber() {
        Exception exception = assertThrows(ItemAlreadyExistException.class, () -> {
            Task taskFour = new Task();
            taskFour.setNumberOfOrder(orderNumberOne);
            taskFour.setDateOfAddingTask(new Date());
            taskService.save(taskFour);
        });
        String expectedMessage = "Item already exist";
        String actualMessage = exception.getMessage();
        Assert.isTrue(expectedMessage.equals(actualMessage));
    }

    @Test
    void shouldReturnEmptyListUseSearch() {
        Assert.isTrue(taskService.getTasks(111).isEmpty());
    }
}
