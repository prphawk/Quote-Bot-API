package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Quote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {

    Optional<Quote> findFirstByTextStartsWith(String text);

    @Query("SELECT q FROM Quote q JOIN q.tags t WHERE LOWER(t) = LOWER(:tag)")
    List<Quote> findByTag(@Param("tag") String tag);

    @Query("SELECT q FROM Quote q JOIN q.tags t " +
            "WHERE LOWER(t) = LOWER(:text) " +
            "OR LOWER(q.text) LIKE LOWER(concat('%', :text,'%'))")
    List<Quote> find(@Param("text") String text);
}
