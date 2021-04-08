package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {

    Optional<Quote> findByText(String text);
}
