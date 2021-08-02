package com.courier_service.courier_service;

import com.courier_service.courier_service.exception.ItemAlreadyExistException;
import com.courier_service.courier_service.model.Task;
import com.courier_service.courier_service.repo.TaskRepository;
import com.courier_service.courier_service.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    final int orderNumberOne = 12;
    final int orderNumberTwo = 34;

    @BeforeTestMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkNullWhenEmptyRepo() {

        Mockito.when(taskRepository.findAll()).thenReturn(new ArrayList<>());

        int actual = taskService.getTasks(null).size();

        int expected = 0;
        assertEquals(expected, actual);
        System.out.println("expected: " + expected + "\n" + "actual: " + actual);
    }

    @Test
    public void checkIntWhenEmptyRepo() {

        Mockito.when(taskRepository.findAll()).thenReturn(new ArrayList<>());

        int actual = taskService.getTasks(12).size();

        int expected = 0;
        assertEquals(expected, actual);
        System.out.println("expected: " + expected + "\n" + "actual: " + actual);
    }

    @Test
    public void checkNullWhenNotEmptyRepo() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task());
        taskList.add(new Task());
        Mockito.when(taskRepository.findAll()).thenReturn(taskList);

        int actual = taskService.getTasks(null).size();

        int expected = 2;
        assertEquals(expected, actual);
        System.out.println("expected: " + expected + "\n" + "actual: " + actual);
    }

    @Test
    public void checkIntWhenNotEmptyRepo() {
        Task taskOne = new Task();
        taskOne.setNumberOfOrder(orderNumberOne);
        Mockito.when(taskRepository.findTaskByOrderNumber(orderNumberOne)).thenReturn(taskOne);

        List<Task> actual = taskService.getTasks(orderNumberOne);

        ArrayList<Task> expected = new ArrayList<>();
        expected.add(taskOne);

        assertEquals(expected, actual);
        System.out.println("expected: " + expected + "\n" + "actual: " + actual);
    }

    @Test
    public void checkSaveWhenDBEmpty() {
        Task taskOne = new Task();
        taskOne.setNumberOfOrder(orderNumberOne);
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(Mockito.any());
        Mockito.when(taskRepository.findTaskByOrderNumber(taskOne.getNumberOfOrder())).thenReturn(null);
        taskService.save(taskOne);

        Mockito.verify(taskRepository, Mockito.times(1)).save(taskOne);

    }

    @Test
    public void checkSaveWhenDBNotEmpty() {

        Assertions.assertThrows(ItemAlreadyExistException.class, () -> {
            Task taskOne = new Task();
            taskOne.setNumberOfOrder(orderNumberOne);
            Mockito.when(taskRepository.save(Mockito.any())).thenReturn(Mockito.any());
            Mockito.when(taskRepository.findTaskByOrderNumber(taskOne.getNumberOfOrder())).thenReturn(taskOne);
            taskService.save(taskOne);
        });

    }
}
