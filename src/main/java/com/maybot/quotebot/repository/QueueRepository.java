package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Queue;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueRepository extends CrudRepository<Queue, Long>  {

    @Query("SELECT q FROM Queue q ORDER BY q.priority DESC, q.id")
    List<Queue> findAllPriorityFirst();

    @Query("SELECT q FROM Queue q ORDER BY q.priority DESC, q.id")
    Optional<Queue> findPriorityFirst(PageRequest pageable);

    List<Queue> findByPriorityFalse();

    @Transactional
    @Modifying
    @Query("DELETE FROM Queue q WHERE q.id = :id")
    void deleteById(@NotNull @Param("id") Long id);
}
