package com.maybot.quotebot.model;

public class PriorityModel {

    private Long id;

    private boolean priority;

    public PriorityModel() {}

    public PriorityModel(Long id, boolean priority) {
        this.id = id;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
