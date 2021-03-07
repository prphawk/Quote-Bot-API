package com.maybot.quotebot.repository;

import com.maybot.quotebot.entity.Reply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends CrudRepository<Reply, Long> {}
