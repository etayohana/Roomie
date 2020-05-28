package com.roomy.dbtest;

public class Mission {

    private String name;
    private boolean isCompleted;

    public Mission() {}

    public Mission(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
