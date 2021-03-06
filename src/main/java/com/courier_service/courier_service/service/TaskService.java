package com.courier_service.courier_service.service;

import com.courier_service.courier_service.exception.ItemAlreadyExistException;
import com.courier_service.courier_service.model.Task;
import com.courier_service.courier_service.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            var result = taskRepository.findTaskByOrderNumber(orderNumber);
            if (result != null) {
                return Stream.of(taskRepository.findTaskByOrderNumber(orderNumber)).collect(Collectors.toList());
            } else {
                return new ArrayList<Task>();
            }
        }
    }

    public void save(Task inputTask) {
        if (taskRepository.findTaskByOrderNumber(inputTask.getNumberOfOrder()) != null) {
            throw new ItemAlreadyExistException("Item already exist");
        }
        taskRepository.save(inputTask);
    }
}

