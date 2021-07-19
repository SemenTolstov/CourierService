package com.courier_service.courier_service.service;

import com.courier_service.courier_service.exception.ItemAlreadyExistException;
import com.courier_service.courier_service.model.Task;
import com.courier_service.courier_service.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasks(Integer orderNumber) {
        if (orderNumber == null) {
            return taskRepository.findAll();
        } else {
            return Stream.of(taskRepository.findTaskByOrderNumber(orderNumber)).collect(Collectors.toList());
        }
    }

    public void save(Task inputTask) {
        Task newTask = new Task();
        newTask.setDateOfAddingTask(new Date());
        newTask.setNumberOfOrder(inputTask.getNumberOfOrder());
        if (taskRepository.findTaskByOrderNumber(inputTask.getNumberOfOrder()) != null) {
            throw new ItemAlreadyExistException("Item already exist");
        }
        taskRepository.save(newTask);
    }
}

