package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Schedule;
import com.maybot.quotebot.model.data.ScheduleDataModel;
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

    public List<ScheduleDataModel> getSchedule() {

        List<Schedule> scheduleList = (List<Schedule>) scheduleRepository.findAll();

        return scheduleList.stream().map(ScheduleDataModel::new).collect(Collectors.toList());
    }

    public List<ScheduleDataModel> saveSchedule(List<Byte> bytes) {

        scheduleRepository.deleteAll();

        return bytes.stream().map(b ->
            new ScheduleDataModel(scheduleRepository.save(new Schedule(b)))
        ).collect(Collectors.toList());
    }

    public boolean isItTime() {

        LocalDateTime now = LocalDateTime.now();

        Optional<Schedule> scheduleSearch = scheduleRepository.findScheduleByHour((byte) now.getHour());

        if(scheduleSearch.isPresent()) {
            Schedule schedule = scheduleSearch.get();

            if(schedule.getLastPosted() == null || schedule.getLastPosted().getDayOfWeek() != now.getDayOfWeek()) {
                schedule.setLastPosted(now);
                scheduleRepository.save(schedule);
                return true;
            }
        }

        return scheduleRepository.findScheduleByHour((byte) -1).isPresent();
    }

}
