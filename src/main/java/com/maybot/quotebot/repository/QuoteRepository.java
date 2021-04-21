package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Quote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {

    Optional<Quote> findFirstByTextStartsWith(String text);

    @Query("SELECT q FROM Quote q JOIN q.tags t WHERE t = :tag")
    List<Quote> findByTag(String tag);
}
