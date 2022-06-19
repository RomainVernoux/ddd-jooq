package fr.vernoux.dddjooq.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Backlog {

    private UUID id;
    private String name;
    private List<UserStory> userStories;

    public void addUserStory(String name, int complexity) {
        UserStory userStory = new UserStory(name, complexity);
        userStories.add(userStory);
    }

    public int totalComplexity() {
        return userStories.stream().mapToInt(UserStory::getComplexity).sum();
    }

    public static Backlog empty(UUID id, String name) {
        Backlog backlog = new Backlog();
        backlog.id = id;
        backlog.name = name;
        backlog.userStories = new ArrayList<>();
        return backlog;
    }
}
