package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Schedule;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleModel {

    @NotNull
    private List<Byte> hours;

    public ScheduleModel() {}

    public ScheduleModel(List<Schedule> scheduleList) {
        this.hours = scheduleList.stream().map(Schedule::getHour).collect(Collectors.toList());
    }

    public List<Byte> getHours() {
        return hours;
    }

    public void setHours(List<Byte> hours) {
        this.hours = hours;
    }
}
