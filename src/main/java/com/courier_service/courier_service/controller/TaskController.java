package com.courier_service.courier_service.controller;

import com.courier_service.courier_service.model.Task;
import com.courier_service.courier_service.repo.TaskRepository;
import com.courier_service.courier_service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    public String taskForm(Model model) {

        model.addAttribute("task", new Task());

        return "task";
    }

    @PostMapping("/task")
    public String addingTask(@ModelAttribute Task inputTask, Model model) throws Exception {

        taskService.save(inputTask);

        return "task";
    }

    @GetMapping("/operator")
    public String operator(Model model, @RequestParam(required = false, defaultValue = "") Integer orderNumber) {

        model.addAttribute("tasks", taskService.getTasks(orderNumber));

        return "operator";
    }

    @ExceptionHandler(Exception.class)
    public String conflict() {

        return "errorpage";
    }
}
