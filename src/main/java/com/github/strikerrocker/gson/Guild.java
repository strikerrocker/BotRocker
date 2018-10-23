package com.github.strikerrocker.gson;

import java.util.ArrayList;
import java.util.Collection;

public class Guild {
    Collection<Member> members = new ArrayList();
    private int id;

    public Guild(int name) {
        id = name;
    }

    public Guild() {
    }

    public Guild setId(int id) {
        this.id = id;
        return this;
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public void removeMember(Member member) {
        this.members.remove(member);
    }

    public Collection<Member> getMembers() {
        return members;
    }
}
