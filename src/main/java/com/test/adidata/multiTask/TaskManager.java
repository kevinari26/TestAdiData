package com.test.adidata.multiTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;


@Service
public class TaskManager {
    @Autowired
    ExecutorService executorService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public void addTask(Runnable task) {
        executorService.submit(task);
    }
}
