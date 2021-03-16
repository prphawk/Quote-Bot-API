package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Deque;
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
public interface DequeRepository extends CrudRepository<Deque, Long>  {

    @Query("SELECT d FROM Deque d ORDER BY d.priority DESC, d.id")
    List<Deque> findAllPriorityFirst();

    @Query("SELECT d FROM Deque d ORDER BY d.priority DESC, d.id")
    Optional<Deque> findPriorityFirst(PageRequest pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM Deque d WHERE d.id = :id")
    void deleteById(@NotNull @Param("id") Long id);
}
