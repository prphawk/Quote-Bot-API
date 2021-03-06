package com.maybot.quotebot.entity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "deque")
public class Deque {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    private Long token;

    public Deque() {}

}
