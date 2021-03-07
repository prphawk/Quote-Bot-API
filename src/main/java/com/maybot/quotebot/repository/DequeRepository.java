package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Deque;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface DequeRepository extends CrudRepository<Deque, Long>  {

    @Query("SELECT d FROM Deque d GROUP BY d.id, d.priority ORDER BY d.priority DESC, d.id")
    List<Deque> findAllPriorityFirst();

    @Query("SELECT d FROM Deque d GROUP BY d.id, d.priority ORDER BY d.priority DESC, d.id")
    Optional<Deque> findPriorityFirst(PageRequest pageable);

}
