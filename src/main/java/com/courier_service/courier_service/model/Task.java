package com.courier_service.courier_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Data
@AllArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int numberOfOrder;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date dateOfAddingTask;


    public Task() {
        dateOfAddingTask = new Date();
    }
}
