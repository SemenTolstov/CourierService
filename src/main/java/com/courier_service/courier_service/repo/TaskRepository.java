package com.courier_service.courier_service.repo;

import com.courier_service.courier_service.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "select * from TASK t where t.number_of_order = :orderNumber", nativeQuery = true)
    Task findTaskByOrderNumber(@Param("orderNumber") Integer orderNumber);
}
