package com.maybot.quotebot.entity;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "stack")
public class Stack {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "quoteIds", nullable = false)
    private List<Long> quoteIds;

    public Stack() {}

    public List<Long> getQuoteIds() {
        return quoteIds;
    }

    public void setQuoteIds(List<Long> quoteIds) {
        this.quoteIds = quoteIds;
    }
}
