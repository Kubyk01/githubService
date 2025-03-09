package com.example.model;

public class GitHubRepository {
    private String name;
    private Owner owner;
    private boolean fork;

    public GitHubRepository(String name, Owner owner, boolean fork) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }

    public boolean isFork() {
        return fork;
    }
}