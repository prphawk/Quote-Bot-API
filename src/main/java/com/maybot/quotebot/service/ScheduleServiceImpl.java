package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Schedule;
import com.maybot.quotebot.model.ScheduleModel;
import com.maybot.quotebot.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleModel getSchedule() {
        List<Schedule> scheduleList = (List<Schedule>) scheduleRepository.findAll();
        return new ScheduleModel(scheduleList);
    }

    public ScheduleModel saveSchedule(ScheduleModel model) {
        scheduleRepository.deleteAll();

        List<Schedule> scheduleList = (List<Schedule>)
                scheduleRepository.saveAll(
                    model.getHours()
                            .stream().map(Schedule::new)
                            .collect(Collectors.toList())
        );

        return new ScheduleModel(scheduleList);
    }


    public boolean isItTime() {

        Optional<Schedule> scheduleSearch = scheduleRepository.findScheduleByHour(
                (byte) LocalDateTime.now().getHour()
        );

        if(scheduleSearch.isPresent()) {
            Schedule schedule = scheduleSearch.get();
            schedule.setLastPosted(LocalDateTime.now());
            scheduleRepository.save(schedule);
            return true;
        }

        return false;
    }

}
