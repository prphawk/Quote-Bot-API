package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {

    List<Quote> findAllByInvisibleFalse();
}
