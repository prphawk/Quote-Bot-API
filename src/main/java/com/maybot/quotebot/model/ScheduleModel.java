package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Schedule;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleModel {

    private Long id;

    private LocalDateTime lastPosted;

    @NotNull
    private Byte hour;

    public ScheduleModel() {}

    public ScheduleModel(Schedule schedule) {
        this.id = schedule.getId();
        this.lastPosted = schedule.getLastPosted();
        this.hour = schedule.getHour();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLastPosted() {
        return lastPosted;
    }

    public void setLastPosted(LocalDateTime lastPosted) {
        this.lastPosted = lastPosted;
    }

    public Byte getHour() {
        return hour;
    }

    public void setHour(Byte hour) {
        this.hour = hour;
    }
}
