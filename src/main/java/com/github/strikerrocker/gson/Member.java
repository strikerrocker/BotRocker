package com.github.strikerrocker.gson;

public class Member {
    private int id;

    public Member(int id) {
        this.id = id;
    }

    public Member() {
    }

    public int getId() {
        return id;
    }

    public Member setId(int id) {
        this.id = id;
        return this;
    }
}
