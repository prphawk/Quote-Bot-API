package com.maybot.quotebot.model;

public class PriorityRequestModel {

    private Long id;

    private boolean priority;

    public PriorityRequestModel() {}

    public PriorityRequestModel(Long id, boolean priority) {
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
