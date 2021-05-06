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

    @Query("SELECT q FROM Queue q WHERE q.index IS NOT NULL ORDER BY q.priority DESC, q.index")
    List<Queue> getQueue();

    @Query("SELECT q FROM Queue q WHERE q.index IS NOT NULL ORDER BY q.priority DESC, q.index")
    Optional<Queue> pop(PageRequest pageable);

    @Query("SELECT q FROM Queue q WHERE q.quote.id = :id")
    Optional<Queue> findByQuoteId(@NotNull @Param("id") Long id);

    List<Queue> findByIndexNotNull();

    List<Queue> findByIndexNull();

    @Transactional
    @Modifying
    @Query("DELETE FROM Queue q WHERE q.id = :id")
    void deleteById(@NotNull @Param("id") Long id);
}
