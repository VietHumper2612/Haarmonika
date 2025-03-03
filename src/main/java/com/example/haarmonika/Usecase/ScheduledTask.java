package com.example.haarmonika.Usecase;

import com.example.haarmonika.Database.Repository;

import java.util.Timer;
import java.util.TimerTask;

public class ScheduledTask {

    private Timer timer;
    private Repository repository;

    public ScheduledTask(Repository repository) {
        this.repository = repository;
        this.timer = new Timer(true);
    }

    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                repository.deleteOldBookings();
            }
        };

        timer.scheduleAtFixedRate(task, 0, 86400000);
    }

    public void stop() {
        timer.cancel();
    }
}