package com.maybot.quotebot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "hour", nullable = false)
    private Byte hour;

    @Column(name = "lastPosted")
    private LocalDateTime lastPosted;

    public Schedule() {
    }

    public Schedule(Byte hour) {
        this.hour = hour;
    }

    public Schedule(Byte hour, LocalDateTime lastPosted) {
        this.hour = hour;
        this.lastPosted = lastPosted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getHour() {
        return hour;
    }

    public void setHour(Byte hour) {
        this.hour = hour;
    }

    public LocalDateTime getLastPosted() {
        return lastPosted;
    }

    public void setLastPosted(LocalDateTime lastPosted) {
        this.lastPosted = lastPosted;
    }
}
