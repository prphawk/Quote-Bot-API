package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Deque;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DequeRepository extends CrudRepository<Deque, Long>  {

    Optional<Deque> findFirstByOrderByTokenAsc();

    Optional<Deque> findFirstByOrderByTokenDesc();
}
