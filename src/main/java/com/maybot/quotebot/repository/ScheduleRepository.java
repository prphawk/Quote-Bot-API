package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Schedule;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    Optional<Schedule> findScheduleByHour(Byte hour);
}
