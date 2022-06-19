package fr.vernoux.dddjooq.domain;

public class UserStory {

    private String name;
    private int complexity;

    public UserStory(String name, int complexity) {
        this.name = name;
        this.complexity = complexity;
    }

    public int getComplexity() {
        return complexity;
    }
}
